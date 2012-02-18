package com.project202.views;

import android.content.Context;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.adapter.HistoryListAdapter;
import com.project202.controller.RatingController;

@EViewGroup(R.layout.history)
public class HistoryView extends MyLinearLayout {

	@Bean
	HistoryListAdapter adapter;
	
	@ViewById
	protected ListView historyList;
	
	public HistoryView(Context context) {
		super(context);
	}

	@AfterViews
	public void afterViews(){
		adapter.setRatings(RatingController.getTestHistoryRatings());
		historyList.setAdapter(adapter);
	}
	
	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.history_title);
	}
}