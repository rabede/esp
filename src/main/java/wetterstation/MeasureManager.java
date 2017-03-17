package wetterstation;

import static java.util.Comparator.comparing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Beispielklasse zur Demonstration der Verwaltung von Measure-Daten
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
public class MeasureManager {
	final Comparator<Measure> byTemp = comparing(Measure::getTemperature);

	final Comparator<Measure> byDay = comparing(Measure::getDay);

	final Comparator<Measure> byHumidity = comparing(Measure::getHumidity);

	final Comparator<Measure> byTempHumidityAndDay = byTemp.reversed().thenComparing(byHumidity.reversed())
			.thenComparing(byDay);

	private final List<Measure> measures = new ArrayList<>();

	public List<Measure> getMeasures() {
		// Kopie erzeugen: keine internen Daten herausgeben
		final List<Measure> sortedResults = new ArrayList<>(measures);
		sortedResults.sort(byTempHumidityAndDay);
		return sortedResults;
	}

	public void add(final Measure measure) {
		measures.add(measure);
		measures.sort(byTempHumidityAndDay);

		final List<Measure> first50 = truncateToMax(measures, 50);
		populateFrom(first50);
	}

	private List<Measure> truncateToMax(final List<Measure> list, final int n) {
		final int max = Math.min(n, list.size());

		return new ArrayList<>(list.subList(0, max));
	}

	private void populateFrom(final List<Measure> first50) {
		measures.clear();
		measures.addAll(first50);
	}

	public List<Measure> getMeasuresTopN(final int n) {
		final List<Measure> allMeasures = getMeasures();
		return truncateToMax(allMeasures, n);
	}

	public void delete(final Measure measure) {
		measures.remove(measure);
	}

	public Optional<Measure> findBy(final long id) {
		final Predicate<Measure> sameId = measure -> measure.getId() == id;
		return measures.stream().filter(sameId).findFirst();
	}

	public void populateFromCsv(final String filePath) {
		final List<Measure> readMeasures = MeasureCsvImporter.readMeasureFromCsv(filePath);
		populateFrom(readMeasures);
	}

	public void saveTo(final PersistMode persistMode, final OutputStream os) throws IOException {
		final MeasurePersister persister = new MeasurePersister();
		persister.persist(persistMode, os, measures);
	}

	public void loadFrom(final PersistMode persistMode, final InputStream is) throws IOException {
		final MeasurePersister persister = new MeasurePersister();
		final List<Measure> readMeasures = persister.readFrom(persistMode, is);

		populateFrom(readMeasures);
	}
}