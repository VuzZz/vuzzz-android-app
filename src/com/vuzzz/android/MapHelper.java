package com.vuzzz.android;

import com.google.android.maps.GeoPoint;

public class MapHelper {

	// Paris
	// public static final int MAX_LAT = 48900850;
	// public static final int MIN_LAT = 48813671;
	// public static final int MAX_LON = 2420921;
	// public static final int MIN_LON = 2244110;
	//
	// public static final int CENTER_LAT = 48862481;
	// public static final int CENTER_LON = 2351723;

	// Nantes
	public static final int MAX_LAT = 47276460;
	public static final int MIN_LAT = 47153796;
	public static final int MAX_LON = -1473579;
	public static final int MIN_LON = -1635971;
	
	public static final int CENTER_LAT = 47220060;
	public static final int CENTER_LON = -1552200;

	public static final int MAX_ZOOM = 14;

	private static GeoPoint restrictedAreaCenter;

	private MapHelper() {

	}

	public static GeoPoint getRestrictedAreaCenter() {
		if (restrictedAreaCenter == null) {
			restrictedAreaCenter = new GeoPoint(CENTER_LAT, CENTER_LON);
		}
		return restrictedAreaCenter;
	}

	public static GeoPoint findTheNearestRestrictedAreaPoint(GeoPoint point) {
		int lat = point.getLatitudeE6();
		int lon = point.getLongitudeE6();

		int newLat = lat;
		int newLon = lon;

		if (lon < MIN_LON) {
			newLon = MIN_LON;
			if (lat < MIN_LAT)
				newLat = MIN_LAT;
			if (lat > MAX_LAT)
				newLat = MAX_LAT;
		} else if (lon > MAX_LON) {
			newLon = MAX_LON;
			if (lat < MIN_LAT)
				newLat = MIN_LAT;
			if (lat > MAX_LAT)
				newLat = MAX_LAT;
		} else if (lat < MIN_LAT) {
			newLat = MIN_LAT;
			if (lon < MIN_LON)
				newLon = MIN_LON;
			if (lon > MAX_LON)
				newLon = MAX_LON;
		} else if (lat > MAX_LAT) {
			newLat = MAX_LAT;
			if (lon < MIN_LON)
				newLon = MIN_LON;
			if (lon > MAX_LON)
				newLon = MAX_LON;
		}

		return new GeoPoint(newLat, newLon);
	}

	public static boolean isInRestrictedArea(GeoPoint point) {
		int lat = point.getLatitudeE6();
		int lon = point.getLongitudeE6();

		if (lon >= MIN_LON && lon <= MAX_LON && lat >= MIN_LAT && lat <= MAX_LAT) {
			return true;
		} else {
			return false;
		}
	}

}
