package com.vuzzz.android.views;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.vuzzz.android.OnRatingSwitchedListener;
import com.vuzzz.android.OnSettingsUpdatedListener;
import com.vuzzz.android.R;
import com.vuzzz.android.adapter.DetailsListAdapter;
import com.vuzzz.android.model.Rating;
import com.vuzzz.android.model.ThemeName;
import com.vuzzz.android.views.RatingView.OnRatingClickListener;

@EViewGroup(R.layout.rating_details)
public class RatingDetailsView extends FrameLayout implements OnRatingClickListener, OnSettingsUpdatedListener, OnRatingSwitchedListener {

	@Bean
	DetailsListAdapter adapter;

	@ViewById
	ListView detailsList;

	Rating currentRating;

	public RatingDetailsView(Context context) {
		super(context);
	}

	@AfterViews
	public void afterViews() {
		detailsList.setAdapter(adapter);
	}

	@Override
	public void onRatingClickListener(ThemeName themeName) {
		detailsList.setSelectionFromTop(adapter.getThemeItemPosition(themeName), 0);
	}

	public void setRating(Rating rating) {
		this.currentRating = rating;
		adapter.setRating(rating);
	}

	@Override
	public void onSettingsUpdated() {
		adapter.setRating(currentRating);
	}

	@Override
	public void onRatingSwitched(Rating rating) {
		setRating(rating);
	}

}
