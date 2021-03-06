package com.vuzzz.android.common.model;

import java.io.Serializable;
import java.util.List;

import com.vuzzz.android.R;

public class Theme implements Serializable, RatingDetails{

	private static final long serialVersionUID = 1L;
	
	public ThemeName name;
	public String description;
	public float note;
	public List<Criterion> criteria;

	public Theme(){}
	
	public Theme(final String name, String description, float note, List<Criterion> criteria) {
		this.name = ThemeName.valueOf(name);
		this.description = description;
		this.note = note;
		this.criteria = criteria;
	}
	
	public List<Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}

	public ThemeName getThemeName() {
		return name;
	}
	
	public String getName() {
		return name.getName();
	}

	public void setName(String name) {
		this.name = ThemeName.valueOf(name);
	}
	
	public void setThemeName(ThemeName name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	@Override
	public int getColor() {
		return name.getLightColor();
	}

	@Override
	public float getCoefficient() {
		throw new UnsupportedOperationException("Hey what's up dude ?");
	}
	
	public boolean isRelevant() {
		return criteria.size() > 0;
	}

	public static int getPictoId(Theme theme) {
		switch(theme.name){
		case CULTURE: return R.drawable.ic_rating_culture;
		case LEISURE: return R.drawable.ic_rating_leasure;
		case NATURE: return R.drawable.ic_rating_nature;
		case SECURITY: return R.drawable.ic_rating_security;
		case SHOPS: return R.drawable.ic_rating_shop;
		case TRANSIT: return R.drawable.ic_rating_transit;
		default: case GLOBAL: return R.drawable.ic_rating_global;
		}
	}
	
}
