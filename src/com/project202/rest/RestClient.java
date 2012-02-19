package com.project202.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.project202.model.Rating;

@Rest("http://www.bluepyth.fr:9000")
public interface RestClient {
	@Get("/rating?latitude={latitude}&longitude={longitude}")
	@Accept(MediaType.APPLICATION_JSON)
	public Rating getRating(double latitude, double longitude);
}
