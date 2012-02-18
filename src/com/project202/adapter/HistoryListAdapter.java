package com.project202.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.project202.ShowNoteActivity;
import com.project202.model.Rating;
import com.project202.views.HistoryLine;

@EBean
public class HistoryListAdapter extends BaseAdapter{

	@RootContext
	Context context;
	
	@RootContext
	ShowNoteActivity showNoteActivity;
	
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
			convertView = new HistoryLine(context);
			showNoteActivity.addOnSettingsFocusedHandler((HistoryLine) convertView);
		}
		((HistoryLine) convertView).setData(ratings.get(position), maxRating);
		((HistoryLine) convertView).setPair(position%2==0);
		return convertView;
	}
		
}