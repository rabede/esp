package wetterstation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Beispiel zur Verarbeitung von CVS-Dateien <br>
 * Lese aus einer speziellen csv-Datei alle Werte ein und mache daraus eine
 * Liste von Measure-Objekten. Dabei sollte jede Zeile der Datei den folgenden
 * Aufbau haben: "<code>Name , Punkte , Level , Datum</code>"
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
public final class MeasureCsvImporter {
	private static final Logger log = Logger.getLogger("MeasureCsvImporter");

	private static final int VALUE_COUNT = 6;

	public static List<Measure> readMeasureFromCsv(final String fileName) {
		final List<Measure> measures = new ArrayList<>();

		try {
			// JDK 8: readAllLines(), forEach() & Lambda
			final List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			lines.forEach(line -> {
				// JDK 8: Optional & Lambda
				final Optional<Measure> optionalMeasure = extractMeasureFromLine(line);
				optionalMeasure.ifPresent(measure -> measures.add(measure));
			});
		} catch (final IOException e) {
			log.warning("processing of file '" + fileName + "' failed: " + e);
		}

		return measures;
	}

	private static Optional<Measure> extractMeasureFromLine(final String line) {
		// Spalte die Eingabe mit ';' oder ',' auf
		final String[] values = line.replace('[', ' ').replace(']', ' ').replaceAll("'", "").split(";|,");

		// Behandlung von Leerzeilen und Kommentaren
		if (isEmptyLineOrComment(values)){
			return Optional.empty();
		}
        
		// Ignoriere fehlertoleranterweise unvollständige Einträge      
        if (values.length != VALUE_COUNT)
        {
            log.warning("Wrong number of values: " + values.length + " expected: " + VALUE_COUNT + "! Skipping invalid value '" + line + "'");
            return Optional.empty();
        }

		try {
			// Auslesen der Werte als String + Typprüfung + Konvertierung
			final String dateAsString = values[0].trim();
			final float temperature = Float.parseFloat(values[1].trim());
			final int humidity = (int)Float.parseFloat(values[2].trim());
			final float windspeed = Float.parseFloat(values[3].trim());
			final float downfall = Float.parseFloat(values[4].trim());
			final boolean rain = Boolean.parseBoolean(values[5].trim());

			final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			final LocalDate day = LocalDate.parse(dateAsString, dateTimeFormatter);
			final LocalTime time = LocalTime.parse(dateAsString, dateTimeFormatter);

			return Optional.of(new Measure(day, time, temperature, humidity, windspeed, downfall, rain));
		} catch (final NumberFormatException e) {
			log.warning("Skipping invalid point or level value '" + line + "'");
		} catch (final DateTimeParseException e) {
			log.warning("Skipping invalid date value '" + line + "'");
		}
		return Optional.empty();
	}

	private static boolean isEmptyLineOrComment(final String[] values) {
		return (values.length == 1 && (values[0].trim().length() == 0) ||
		// Ignoriere Kommentare, die auch ',' oder ';' enthalten
				(values.length >= 1 && values[0].trim().startsWith("#")));
	}
}
