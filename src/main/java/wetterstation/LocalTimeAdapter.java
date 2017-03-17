package wetterstation;

import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Hilfsklasse zur Unterstützung der neuen Datentypen auf dem Date And Time API
 * für XML (bei JAX-RS auch für JSON)
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
	@Override
	public LocalTime unmarshal(final String s) throws Exception {
		return LocalTime.parse(s);
	}

	@Override
	public String marshal(final LocalTime localtime) throws Exception {
		return localtime.toString();
	}
}