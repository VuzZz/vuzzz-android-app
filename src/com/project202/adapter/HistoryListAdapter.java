package com.project202.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.project202.model.Rating;
import com.project202.views.HistoryLine;

@EBean
public class HistoryListAdapter extends BaseAdapter{

	@RootContext
	Activity activity;
	
	private List<Rating> ratings;
	private float maxRating;
	
	public void setRatings(List<Rating> ratings){
		float maxMark = Float.MIN_VALUE;
		for (Rating rating : ratings){
			float mark = rating.getMark();
			if (mark > maxMark){
				maxMark = mark;
			}
		}
		this.maxRating = maxMark;
		this.ratings = ratings;
	}
	
	@Override
	public int getCount() {
		return ratings.size();
	}

	@Override
	public Object getItem(int position) {
		return ratings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new HistoryLine(activity); 
		}
		
		((HistoryLine) convertView).setData(ratings.get(position), maxRating);

		return convertView;
	}
	
}