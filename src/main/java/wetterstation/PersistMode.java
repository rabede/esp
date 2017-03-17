package wetterstation;

/**
 * Aufzählung zur Definition verschiedener Persistierungsmodi
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden 
 */
public enum PersistMode 
{
	SERIALIZATION 
	{
		IPersistStrategy getStrategy() 
		{
		  return new PersistWithSerializationStrategy();  
		}
	}, 
	XML 
	{ 
		IPersistStrategy getStrategy() 
		{
		  return new PersistWithXmlStrategy();  
		}
	};


	abstract IPersistStrategy getStrategy();
}