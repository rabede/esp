package wetterstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Klasse zur Persistierung basierend auf Serialisierung
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
public class PersistWithSerializationStrategy implements IPersistStrategy {
	@Override
	public void persist(final OutputStream os, final List<Measure> measures) throws IOException {
		final ObjectOutputStream outStream = new ObjectOutputStream(os);
		outStream.writeObject(measures);
	}

	@Override
	public List<Measure> readFrom(final InputStream is) throws IOException {
		final ObjectInputStream inStream = new ObjectInputStream(is);
		try {
			return (List<Measure>) inStream.readObject();
		} catch (final ClassNotFoundException e) {
			// kann hier nicht auftreten, weil nur eigene Klassen
		}
		return Collections.emptyList();
	}
}