package com.project202.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;
import com.project202.controller.GradeController;
import com.project202.controller.RatingController;
import com.project202.model.PrintedRating;
import com.project202.model.Rating;

@EViewGroup(R.layout.rating)
public class RatingView extends LinearLayout {

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
	
	public RatingView(Context context) {
		super(context);
	}
	
	public void setValuesFromRating(Rating rating){
		PrintedRating printedRating = RatingController.getPrintedRating(rating);
		this.globalRating.setText(String.valueOf(printedRating.getGlobalGrade()));
		this.globalRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getGlobalGrade()));
		
		this.cultureRating.setText(String.valueOf(printedRating.getCultureGrade()));
		this.cultureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getCultureGrade()));
		
		this.natureRating.setText(String.valueOf(printedRating.getNatureGrade()));
		this.natureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getNatureGrade()));
		
		this.transitRating.setText(String.valueOf(printedRating.getTransitGrade()));
		this.transitRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getTransitGrade()));
		
		this.institutionsRating.setText(String.valueOf(printedRating.getInstitutionsGrade()));
		this.institutionsRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getInstitutionsGrade()));
		
		this.shopsRating.setText(String.valueOf(printedRating.getShopsGrade()));
		this.shopsRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getShopsGrade()));
		
		this.leisureRating.setText(String.valueOf(printedRating.getLeisureGrade()));
		this.leisureRating.setBackgroundColor(GradeController.getColorForGrade(printedRating.getLeisureGrade()));
	}
	
}
