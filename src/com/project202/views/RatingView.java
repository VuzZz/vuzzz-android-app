package com.project202.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.project202.R;
import com.project202.address.SettingsPreferences_;
import com.project202.model.Rating;
import com.project202.model.ThemeName;
import com.project202.model.Weighted;

@EViewGroup(R.layout.rating)
public class RatingView extends LinearLayout {
	
	public interface OnRatingClickListener{
		void onRatingClickListener(ThemeName themeName);
	}
	
	@ViewById
	TextView globalRating;
	@ViewById
	TextView cultureRating;
	@ViewById
	TextView natureRating;
	@ViewById
	TextView transitRating;
	@ViewById
	TextView securityRating;
	@ViewById
	TextView shopsRating;
	@ViewById
	TextView leisureRating;
	
	@Pref
	SettingsPreferences_ preferences;
	
	OnRatingClickListener listener;
	
	public RatingView(Context context) {
		super(context);
	}

	public void setOnRatingClickListener(OnRatingClickListener listener){
		this.listener = listener;
	}
	
	public void setValuesFromRating(Rating rating){
		globalRating.setText(Weighted.getWeightedMark(rating, preferences).toString());
		
		cultureRating.setText(rating.getThemeMark(ThemeName.CULTURE).toString());
		cultureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.CULTURE);
			}
		});
		
		natureRating.setText(rating.getThemeMark(ThemeName.NATURE).toString());
		natureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.NATURE);
			}
		});
		
		transitRating.setText(rating.getThemeMark(ThemeName.TRANSIT).toString());
		transitRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.TRANSIT);
			}
		});
		
		securityRating.setText(rating.getThemeMark(ThemeName.SECURITY).toString());
		securityRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.SECURITY);
			}
		});
		
		shopsRating.setText(rating.getThemeMark(ThemeName.SHOPS).toString());
		shopsRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.SHOPS);
			}
		});
		
		leisureRating.setText(rating.getThemeMark(ThemeName.LEISURE).toString());
		leisureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.LEISURE);
			}
		});
	}
	
}
