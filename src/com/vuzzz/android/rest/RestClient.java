package com.vuzzz.android.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.vuzzz.android.model.Rating;

@Rest("http://vuzzz.com")
public interface RestClient {
	@Get("/rating?latitude={latitude}&longitude={longitude}")
	@Accept(MediaType.APPLICATION_JSON)
	public Rating getRating(double latitude, double longitude);
}
