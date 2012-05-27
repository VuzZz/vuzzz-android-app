package com.vuzzz.android.common.helper;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.maps.GeoPoint;
import com.vuzzz.android.R;

public class MapHelper {

	public static final int MAX_ZOOM = 14;

	private static GeoPoint restrictedAreaCenter;

	private MapHelper() {

	}

	public static GeoPoint getRestrictedAreaCenter(Context context) {
		Resources resources = context.getResources();
		int centerLat = Integer.valueOf(resources.getString(R.string.CENTER_LAT));
		int centerLon = Integer.valueOf(resources.getString(R.string.CENTER_LON));
		
		if (restrictedAreaCenter == null) {
			restrictedAreaCenter = new GeoPoint(centerLat, centerLon);
		}
		return restrictedAreaCenter;
	}
	
	public static GeoPoint findTheNearestRestrictedAreaPoint(Context context, GeoPoint point) {
		Resources resources = context.getResources();
		int minLon = Integer.valueOf(resources.getString(R.string.MIN_LON));
		int minLat = Integer.valueOf(resources.getString(R.string.MIN_LAT));
		int maxLon = Integer.valueOf(resources.getString(R.string.MAX_LON));
		int maxLat = Integer.valueOf(resources.getString(R.string.MAX_LAT));
		
		
		int lat = point.getLatitudeE6();
		int lon = point.getLongitudeE6();

		int newLat = lat;
		int newLon = lon;

		if (lon < minLon) {
			newLon = minLon;
			if (lat < minLat) {
				newLat = minLat;
			}
			if (lat > maxLat) {
				newLat = maxLat;
			}
		} else if (lon > maxLon) {
			newLon = maxLon;
			if (lat < minLat) {
				newLat = minLat;
			}
			if (lat > maxLat) {
				newLat = maxLat;
			}
		} else if (lat < minLat) {
			newLat = minLat;
			if (lon < minLon) {
				newLon = minLon;
			}
			if (lon > maxLon) {
				newLon = maxLon;
			}
		} else if (lat > maxLat) {
			newLat = maxLat;
			if (lon < minLon) {
				newLon = minLon;
			}
			if (lon > maxLon) {
				newLon = maxLon;
			}
		}

		return new GeoPoint(newLat, newLon);
	}

	public static boolean isInRestrictedArea(Context context, GeoPoint point) {
		Resources resources = context.getResources();
		int minLon = Integer.valueOf(resources.getString(R.string.MIN_LON));
		int minLat = Integer.valueOf(resources.getString(R.string.MIN_LAT));
		int maxLon = Integer.valueOf(resources.getString(R.string.MAX_LON));
		int maxLat = Integer.valueOf(resources.getString(R.string.MAX_LAT));
		
		int lat = point.getLatitudeE6();
		int lon = point.getLongitudeE6();

		if (lon >= minLon && lon <= maxLon && lat >= minLat && lat <= maxLat) {
			return true;
		} else {
			return false;
		}
	}

}
