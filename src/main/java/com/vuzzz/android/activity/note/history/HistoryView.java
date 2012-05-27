package com.vuzzz.android.activity.note.history;

import static com.vuzzz.android.common.model.ThemeName.CULTURE;
import static com.vuzzz.android.common.model.ThemeName.LEISURE;
import static com.vuzzz.android.common.model.ThemeName.NATURE;
import static com.vuzzz.android.common.model.ThemeName.SECURITY;
import static com.vuzzz.android.common.model.ThemeName.SHOPS;
import static com.vuzzz.android.common.model.ThemeName.TRANSIT;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.vuzzz.android.R;
import com.vuzzz.android.activity.note.OnRatingSelectedListener;
import com.vuzzz.android.activity.note.OnSettingsUpdatedListener;
import com.vuzzz.android.common.model.Rating;

@EViewGroup(R.layout.history)
public class HistoryView extends LinearLayout implements OnSettingsUpdatedListener {

	@Bean
	HistoryListAdapter adapter;

	List<Rating> currentRatings = new ArrayList<Rating>();

	@ViewById
	protected ListView historyList;

	@ViewById
	protected LegendItem legendCulture, legendLeisure, legendNature,//
			legendSecurity, legendTransit, legendShops;

	private final OnRatingSelectedListener onRatingSelectedListener;

	public HistoryView(Context context, OnRatingSelectedListener onRatingSelectedListener) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		this.onRatingSelectedListener = onRatingSelectedListener;
	}

	@AfterViews
	public void afterViews() {
		legendCulture.setTheme(CULTURE);
		legendLeisure.setTheme(LEISURE);
		legendNature.setTheme(NATURE);
		legendSecurity.setTheme(SECURITY);
		legendTransit.setTheme(TRANSIT);
		legendShops.setTheme(SHOPS);
		
		historyList.setAdapter(adapter);
		historyList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				onRatingSelectedListener.onRatingSelectedListener((Rating) adapter.getItem(arg2));
			}
		});
	}

	public void setRatings(List<Rating> ratings) {
		this.currentRatings = ratings;
		adapter.setRatings(ratings);
	}

	@Override
	public void onSettingsUpdated() {
		adapter.setRatings(currentRatings);
	}
}
