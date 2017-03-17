package wetterstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


/**
 * Dieses Interfaces derfiniert die Methodern zur Persistierung
 *
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden
 */
public interface IPersistStrategy 
{
	void persist(OutputStream os, List<Measure> measures) throws IOException;
	List<Measure> readFrom(InputStream is) throws IOException;
}