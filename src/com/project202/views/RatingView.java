package com.project202.views;

import android.content.Context;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;

@EViewGroup(R.layout.rating)
public class RatingView extends MyLinearLayout {

	@ViewById
	protected TextView globalRating;
	
	public RatingView(Context context) {
		super(context);
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.rating_title);
	}
	
	public void setGlobalRating(String globalRating, int color){
		this.globalRating.setText(globalRating);
		this.setBackgroundColor(color);
	}

}
