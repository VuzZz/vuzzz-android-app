package com.project202.model;

import java.io.Serializable;
import java.util.List;

public class Rating implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Theme> themes;

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public Rating(List<Theme> themes) {
		this.themes = themes;
	}

	public float getMark() {
		float sum = 0f;
		for (Theme theme : themes){
			sum += theme.getNote();
		}
		return sum;
	}
}
