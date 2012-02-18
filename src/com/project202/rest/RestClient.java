package com.project202.rest;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.project202.model.Rating;

@Rest("http://192.168.1.129:9000/")
public interface RestClient {
	@Get("/rating?latitude={latitude}&longitude={longitude}")
	public Rating getRating(float latitude, float longitude);
}
