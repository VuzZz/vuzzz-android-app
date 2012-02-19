package com.project202.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.OnSettingsUpdatedListener;
import com.project202.adapter.HistoryListAdapter;
import com.project202.model.Rating;
import com.vuzzz.android.R;

@EViewGroup(R.layout.history)
public class HistoryView extends LinearLayout implements OnSettingsUpdatedListener {

	@Bean
	HistoryListAdapter adapter;
	
	List<Rating> currentRatings = new ArrayList<Rating>();
	
	@ViewById
	protected ListView historyList;
	
	public HistoryView(Context context) {
		super(context);
	}

	@AfterViews
	public void afterViews(){
		historyList.setAdapter(adapter);
	}
	
	public void setRatings(List<Rating> ratings){
		Log.i("TAG", "SET RATINGS "+ratings.size());
		this.currentRatings = ratings;
		adapter.setRatings(ratings);
	}

	@Override
	public void onSettingsUpdated() {
		adapter.setRatings(currentRatings);
	}
}
