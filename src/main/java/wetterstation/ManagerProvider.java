package wetterstation;

/**
 * 
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
public class ManagerProvider {
	private static MeasureManager measureManager = null;

	public static synchronized MeasureManager getMeasureManager() {
		if (measureManager == null) {
			measureManager = new MeasureManager();
		}
		return measureManager;
	}
}