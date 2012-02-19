package com.project202.loading;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;

import com.project202.model.Rating;
import com.project202.rest.RestClient_;

public class RatingDownloadTask<T extends Activity & TaskResultListener<Rating>> extends LoadingTask<T, Rating> {

	public final static String HISTO_FILE_PREFIX = "histo_file_";
	
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

		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
		objectMapper.configure(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS, false);
		objectMapper.configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, false);

		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> httpMessageConverter : messageConverters) {
			if (httpMessageConverter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jacksonConverter = (MappingJacksonHttpMessageConverter) httpMessageConverter;
				jacksonConverter.setObjectMapper(objectMapper);
			}
		}

		restClient = new RestClient_(restTemplate);
	}

	@Override
	protected Rating load() throws Exception {
		double latitude = latitudeE6 / (double) 1E6;
		double longitude = longitudeE6 / (double) 1E6;

		Rating rating = restClient.getRating(latitude, longitude);
		rating.address = address;
		rating.latitude = latitude;
		rating.longitude = longitude;
		
		objectMapper.writeValue(context.openFileOutput(HISTO_FILE_PREFIX + new Date(), 0), rating);

		return rating;
	}

}
