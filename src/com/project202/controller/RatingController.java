package com.project202.controller;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.project202.model.Criterion;
import com.project202.model.PrintedDetail;
import com.project202.model.PrintedRating;
import com.project202.model.Rating;
import com.project202.model.Theme;

public class RatingController {
	public static List<Rating> getTestHistoryRatings() {
		List<Rating> ratings = new ArrayList<Rating>();
		
		for (int i = 0; i < 5; i++){
			List<Theme> themes = new ArrayList<Theme>();
			for (int j = 0; j < 6; j++){
				Theme theme = new Theme("CULTURE", "Description", (float)(Math.random()*10), null);
				themes.add(theme);
			}
			Rating r = new Rating(themes);
			ratings.add(r);
		}
		return ratings;
	}

	public static PrintedRating getPrintedRating(Rating rating) {

		PrintedRating printedRating = new PrintedRating();

		for (Theme theme : rating.getThemes()) {
			switch (theme.getName()) {
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

		printedRating.setGlobalGrade(GradeController.getGlobalGrade(printedRating));

		return printedRating;

	}
	public static List<PrintedDetail> getDetailsList(Rating rating){
		List<PrintedDetail> details = new ArrayList<PrintedDetail>();
		
		for(Theme t : rating.getThemes()){
			details.add(new PrintedDetail(t.getName().toString(), t.getDescription(), t.getNote(), false));
			for(Criterion c : t.getCriteria()){
				details.add(new PrintedDetail(c.getName(), c.getDescription(), c.getNote(), true));
			}
		}
		
		Log.d("LIST", details.toString());
		
		return details;
	}
}
