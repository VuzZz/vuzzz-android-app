package com.vuzzz.android.activity.download;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;

import com.vuzzz.android.common.VuzZzConfig;
import com.vuzzz.android.common.model.Rating;
import com.vuzzz.android.common.rest.RestClient_;

public class RatingDownloadTask<T extends Activity & TaskResultListener<Rating>> extends LoadingTask<T, Rating> {

	private final long latitudeE6;
	private final long longitudeE6;
	private RestClient_ restClient;
	private Context context;
	private ObjectMapper objectMapper;

	private final String address;

	public RatingDownloadTask(T activity, String address, int latitudeE6, int longitudeE6) {
		super(activity);
		this.context = activity;
		this.address = address;
		this.latitudeE6 = latitudeE6;
		this.longitudeE6 = longitudeE6;
		objectMapper = VuzZzConfig.configureObjectMapper();
		restClient = VuzZzConfig.configureRestClient();
	}

	@Override
	protected Rating load() throws Exception {
		double latitude = latitudeE6 / (double) 1E6;
		double longitude = longitudeE6 / (double) 1E6;

		Rating rating = restClient.getRating(latitude, longitude);
		rating.address = address;
		rating.latitude = latitude;
		rating.longitude = longitude;
		
		objectMapper.writeValue(Rating.newRatingFile(context), rating);
		
		Rating.checkMaxHistory(context);

		return rating;
	}

}
