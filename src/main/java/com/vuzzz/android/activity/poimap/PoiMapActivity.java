package com.vuzzz.android.activity.poimap;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.vuzzz.android.R;
import com.vuzzz.android.common.VuzZzConfig;
import com.vuzzz.android.common.model.POI;
import com.vuzzz.android.common.model.POIList;
import com.vuzzz.android.common.rest.RestClient;

@EActivity(R.layout.poi_map)
public class PoiMapActivity extends MapActivity {

	@Extra("longitude")
	double longitude;

	@Extra("latitude")
	double latitude;

	@Extra("poiType")
	String poiType;

	@Extra("picto")
	int pictoId;
	
	@Extra("color")
	int color;
	
	@Extra("title")
	String title;
	
	@ViewById
	MapView mapView;

	RestClient restClient;

	private POIOverlay searchOverlay;

	@AfterViews
	void afterViews() {
		setTitle(title);
		GeoPoint referencePoint = new GeoPoint( //
				(int) (latitude * 1E6), //
				(int) (longitude * 1E6));
		mapView.getController().animateTo(referencePoint);
		Bitmap picto = BitmapFactory.decodeResource(getResources(), pictoId);
		restClient = VuzZzConfig.configureRestClient();
		List<Overlay> overlays = mapView.getOverlays();
		searchOverlay = new POIOverlay(this, referencePoint, picto, color);
		overlays.add(searchOverlay);
		callServer();
	}

	@Background
	void callServer() {
		POIList poilist = restClient.getPOI(latitude, longitude, poiType);
		displayResults(poilist);
	}

	@UiThread
	void displayResults(POIList poilist) {
		List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		int minLat = MAX_VALUE;
		int maxLat = MIN_VALUE;
		int minLon = MAX_VALUE;
		int maxLon = MIN_VALUE;

		for (POI poi : poilist.list) {
			GeoPoint geoPoint = new GeoPoint((int) (poi.latitude * 1E6),
					(int) (poi.longitude * 1E6));
			geoPoints.add(geoPoint);
			maxLat = max(geoPoint.getLatitudeE6(), maxLat);
			minLat = min(geoPoint.getLatitudeE6(), minLat);
			maxLon = max(geoPoint.getLongitudeE6(), maxLon);
			minLon = min(geoPoint.getLongitudeE6(), minLon);
		}
		
		searchOverlay.setAddresses(geoPoints);
		double fitFactor = 1.5;
		mapView.getController().zoomToSpan(
				(int) (Math.abs(maxLat - minLat) * fitFactor),
				(int) (Math.abs(maxLon - minLon) * fitFactor));

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
