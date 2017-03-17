package wetterstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

/**
 * Klasse zur Persistierung basierend auf XML
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden 
 */
public class PersistWithXmlStrategy implements IPersistStrategy 
{	
	@Override
	public void persist(final OutputStream os, 
			            final List<Measure> measures)
	                    throws IOException 
	{		
		final MeasureList measureList = new MeasureList();
		measureList.setMeasures(measures);
		
		try
		{
			final Marshaller marshaller = createMarshaller();
			marshaller.marshal(measureList, os);
		}
		catch (final JAXBException ex)
		{
			throw new IOException(ex);
		}
	}

	@Override
	public List<Measure> readFrom(final InputStream is) throws IOException
	{
		try
		{
			final Unmarshaller unmarshaller = createUnmarshaller();

			final MeasureList measureList = (MeasureList) unmarshaller.unmarshal(is);
			return measureList.getMeasures();
		}
		catch (final JAXBException ex)
		{
			throw new IOException(ex);
		}
	}
	
	private Marshaller createMarshaller() throws JAXBException, PropertyException 
	{
		final JAXBContext context = JAXBContext.newInstance(MeasureList.class);
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		return marshaller;
	}
	
	private Unmarshaller createUnmarshaller() throws JAXBException 
	{
		final JAXBContext context = JAXBContext.newInstance(MeasureList.class);
		return context.createUnmarshaller();
	}
}