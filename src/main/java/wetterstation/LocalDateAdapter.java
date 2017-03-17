package wetterstation;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Hilfsklasse zur Unterstützung der neuen Datentypen auf dem Date And Time API für XML (bei JAX-RS auch für JSON)
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden 
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>
{
    @Override
    public LocalDate unmarshal(final String s) throws Exception
    {
        return LocalDate.parse(s);
    }

    @Override
    public String marshal(final LocalDate localdate) throws Exception
    {
        return localdate.toString();
    }
}