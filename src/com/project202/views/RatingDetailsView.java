package com.project202.views;

import android.content.Context;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.project202.R;

@EViewGroup(R.layout.rating_details)
public class RatingDetailsView extends MyLinearLayout {

	public RatingDetailsView(Context context) {
		super(context);
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.rating_details_title);
	}

}
