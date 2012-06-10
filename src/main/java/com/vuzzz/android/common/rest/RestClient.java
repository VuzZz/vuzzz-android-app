package com.vuzzz.android.common.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.vuzzz.android.common.model.POIList;
import com.vuzzz.android.common.model.Rating;

@Rest("http://vuzzz.com")
public interface RestClient {
	
	@Get("/rating?latitude={latitude}&longitude={longitude}")
	@Accept(MediaType.APPLICATION_JSON)
	public Rating getRating(double latitude, double longitude);
	
	@Get("/locate?latitude={latitude}&longitude={longitude}&poiType={id}")
	@Accept(MediaType.APPLICATION_JSON)
	public POIList getPOI(double latitude, double longitude, String id);
	
}
