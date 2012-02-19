package com.project202.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.ShowNoteActivity;
import com.project202.adapter.DetailsListAdapter;
import com.project202.controller.RatingController;
import com.project202.model.ThemeName;
import com.project202.views.RatingView.OnRatingClickListener;

@EViewGroup(R.layout.rating_details)
public class RatingDetailsView extends LinearLayout implements OnRatingClickListener{

	@Bean
	DetailsListAdapter adapter;
	@ViewById
	ListView detailsList;

	public RatingDetailsView(Context context) {
		super(context);
	}

	@AfterViews
	public void afterViews() {
		adapter.setDetails(RatingController.getDetailsList(ShowNoteActivity.getTestPrintedRating()));
		detailsList.setAdapter(adapter);
	}

	@Override
	public void onRatingClickListener(ThemeName themeName) {
		detailsList.setSelectionFromTop(adapter.getThemeItemPosition(themeName), 0);
		
	}

}
