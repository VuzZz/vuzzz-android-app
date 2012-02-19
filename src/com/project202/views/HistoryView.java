package com.project202.views;

import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.adapter.HistoryListAdapter;
import com.project202.model.Rating;

@EViewGroup(R.layout.history)
public class HistoryView extends LinearLayout {

	@Bean
	HistoryListAdapter adapter;
	
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
		adapter.setRatings(ratings);
	}
}
