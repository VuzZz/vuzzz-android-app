package com.project202.model;

import java.util.List;

public class Rating {
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
}
