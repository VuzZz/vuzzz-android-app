package com.project202.controller;

import java.util.ArrayList;
import java.util.List;

import com.project202.model.PrintedRating;
import com.project202.model.Rating;
import com.project202.model.Theme;

public class RatingController {
	public static List<PrintedRating> getTestHistoryRatings() {
		List<PrintedRating> ratings = new ArrayList<PrintedRating>();
		ratings.add(new PrintedRating(8f, 3f, 2f, 7f, 8f, 1f, 8f));
		ratings.add(new PrintedRating(2f, 2f, 9f, 2f, 9f, 2f, 2f));
		ratings.add(new PrintedRating(6f, 7f, 3f, 5f, 5f, 7f, 6f));
		ratings.add(new PrintedRating(3f, 5f, 6f, 4f, 2f, 5f, 3f));

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
}
