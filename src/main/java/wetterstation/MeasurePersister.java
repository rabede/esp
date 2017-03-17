package wetterstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Beispielklasse zur Persistierung von Measures-Daten 
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden
 */
public class MeasurePersister 
{	
	public void persist(final PersistMode mode,
						final OutputStream os, 
			            final List<Measure> measures)
	                    throws IOException 
	{
		final IPersistStrategy strategy = mode.getStrategy();
		strategy.persist(os, measures);
	}

	public List<Measure> readFrom(final PersistMode mode, 
			                        final InputStream is) 
			                        throws IOException
	{
		final IPersistStrategy strategy = mode.getStrategy();
		return strategy.readFrom(is);
	}
}