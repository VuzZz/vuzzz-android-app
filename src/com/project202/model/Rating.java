package com.project202.model;

import java.io.Serializable;
import java.util.List;

public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Theme> themes;

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public Rating() {
	}

	public Rating(List<Theme> themes) {
		this.themes = themes;
	}

	public Float getThemeMark(ThemeName themeName) {
		for (Theme t : themes) {
			if (t.getThemeName().equals(themeName))
				return t.getNote();
		}
		return 0f;
	}
}
