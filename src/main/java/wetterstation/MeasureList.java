package wetterstation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Diese Klasse modelliert eine Liste von Measure-Einträgen
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden
 */
@XmlRootElement (name="Measures")
@XmlAccessorType (XmlAccessType.FIELD)
public class MeasureList
{
    @XmlElement(name = "Measure")
    private List<Measure> measures;
 
    public List<Measure> getMeasures() 
    {
        return measures;
    }
 
    public void setMeasures(List<Measure> measures) 
    {
		this.measures = measures;
    }
}