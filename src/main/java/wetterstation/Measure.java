package wetterstation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public final class Measure implements Serializable {
	private static final AtomicLong counter = new AtomicLong();

	private long id;
	private LocalDate day;
	private LocalTime time;
	private float temperature;
	private int humidity;
	private float windspeed;
	private float downfall;
	private boolean rain;

	// für XML-Serialisierung
	public Measure() {
	}

	public Measure(final LocalDate day, final LocalTime time, final float temperature, final int humidity,
			final float windspeed, final float downfall, final boolean rain) {
		this.id = counter.getAndIncrement();
		this.day = day;
		this.time = time;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windspeed = windspeed;
		this.downfall = downfall;
		this.rain = rain;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public float getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(float windspeed) {
		this.windspeed = windspeed;
	}

	public float getDownfall() {
		return downfall;
	}

	public void setDownfall(float downfall) {
		this.downfall = downfall;
	}

	public boolean isRain() {
		return rain;
	}

	public void setRain(boolean rain) {
		this.rain = rain;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDay() {
		return day;
	}

	public void setDay(final LocalDate day) {
		this.day = day;
	}

	@Override
	public String toString() {
		String regen = "-";
		if (rain) {
			regen = "Regen";
		}
		return day + "\t" + time + "\t" + temperature + "°C\t" + humidity + "%\t" + windspeed + "km/h\t" + downfall
				+ "ml/m²\t" + regen;
	}
}