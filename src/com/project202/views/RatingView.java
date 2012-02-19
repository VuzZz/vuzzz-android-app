package com.project202.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.controller.GradeController;
import com.project202.controller.RatingController;
import com.project202.model.PrintedRating;
import com.project202.model.Rating;
import com.project202.model.ThemeName;

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
	TextView institutionsRating;
	@ViewById
	TextView shopsRating;
	@ViewById
	TextView leisureRating;
	
	OnRatingClickListener listener;
	
	public RatingView(Context context) {
		super(context);
	}

	public void setOnRatingClickListener(OnRatingClickListener listener){
		this.listener = listener;
	}
	
	public void setValuesFromRating(Rating rating){
		PrintedRating printedRating = RatingController.getPrintedRating(rating);
		globalRating.setText(String.valueOf(printedRating.getGlobalGrade()));
		globalRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getGlobalGrade()));
		
		cultureRating.setText(String.valueOf(printedRating.getCultureGrade()));
		cultureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getCultureGrade()));
		cultureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.CULTURE);
			}
		});
		
		natureRating.setText(String.valueOf(printedRating.getNatureGrade()));
		natureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getNatureGrade()));
		natureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.NATURE);
			}
		});
		
		transitRating.setText(String.valueOf(printedRating.getTransitGrade()));
		transitRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getTransitGrade()));
		transitRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.TRANSIT);
			}
		});
		
		institutionsRating.setText(String.valueOf(printedRating.getInstitutionsGrade()));
		institutionsRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getInstitutionsGrade()));
		institutionsRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.INSTITUTIONS);
			}
		});
		
		shopsRating.setText(String.valueOf(printedRating.getShopsGrade()));
		shopsRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getShopsGrade()));
		shopsRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.SHOPS);
			}
		});
		
		leisureRating.setText(String.valueOf(printedRating.getLeisureGrade()));
		leisureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getLeisureGrade()));
		leisureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.LEISURE);
			}
		});
	}
	
}
