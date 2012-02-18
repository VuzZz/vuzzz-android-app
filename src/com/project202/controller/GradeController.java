package com.project202.controller;

import com.project202.R;
import com.project202.model.PrintedRating;

public class GradeController {
	
	public static float getGlobalGrade(PrintedRating printedRating){
		float sum = 0f;
		sum += printedRating.getCultureGrade();
		sum += printedRating.getInstitutionsGrade();
		sum += printedRating.getLeisureGrade();
		sum += printedRating.getNatureGrade();
		sum += printedRating.getShopsGrade();
		sum += printedRating.getTransitGrade();
		return sum / 6f;
	}

	public static int getColorForGrade(float grade){
		if (grade >= 0 && grade < 2.5) return R.drawable.red;
		if (grade >= 2.5 && grade < 5) return R.drawable.orange;
		if (grade >= 5 && grade < 7.5) return R.drawable.yellow;
		else return R.drawable.green;
	}
}
