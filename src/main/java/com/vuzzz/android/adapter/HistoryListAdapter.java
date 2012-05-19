package com.vuzzz.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.vuzzz.android.ShowNoteActivity;
import com.vuzzz.android.address.SettingsPreferences_;
import com.vuzzz.android.model.Rating;
import com.vuzzz.android.model.Weighted;
import com.vuzzz.android.views.HistoryLine;

@EBean
public class HistoryListAdapter extends BaseAdapter {

	@RootContext
	Activity activity;

	@RootContext
	ShowNoteActivity showNoteActivity;
	
	@Pref
	SettingsPreferences_ preferences;
	
	private List<Rating> ratings = new ArrayList<Rating>();
	private float maxRating;

	public void setRatings(List<Rating> ratings) {
		float maxMark = Float.MIN_VALUE;
		for (Rating rating : ratings) {
			float mark = Weighted.getMarkSum(rating);
			if (mark > maxMark) {
				maxMark = mark;
			}
		}
		this.maxRating = maxMark;
		this.ratings = ratings;
		notifyDataSetChanged();
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
		HistoryLine historyLine;
		if (convertView == null) {
			historyLine = new HistoryLine(activity);
			showNoteActivity.addOnSettingsFocusedHandler(historyLine);
		} else {
			historyLine = (HistoryLine) convertView;
		}
		historyLine.setData(ratings.get(position), maxRating);
		historyLine.setPair(position % 2 == 0);
		return historyLine;
	}
}