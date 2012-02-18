package com.project202.controller;


import com.project202.R;
import com.project202.model.PrintedRating;
import com.project202.model.Rating;
import com.project202.model.Theme;

public class GradeController {
	public static PrintedRating getPrintedRating(Rating rating){
		
		PrintedRating printedRating = new PrintedRating();
		
		for (Theme theme : rating.getThemes()){
			switch(theme.getName()){
			case LEISURE:
				printedRating.setLeisureGrade(theme.getNote());
				break;
			case SHOPS:
				printedRating.setShopsGrade(theme.getNote());
				break;
			case TRANSIT:
				printedRating.setTransitGrade(theme.getNote());
				break;
			case CULTURE:
				printedRating.setCultureGrade(theme.getNote());
				break;
			case INSTITUTIONS:
				printedRating.setInstitutionsGrade(theme.getNote());
				break;
			case NATURE:
				printedRating.setNatureGrade(theme.getNote());
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
		
		printedRating.setGlobalGrade(getGlobalRating(printedRating));
		
		return printedRating;
		
	}
	
	private static float getGlobalRating(PrintedRating printedRating){
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
