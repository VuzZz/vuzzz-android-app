package com.project202.model;

import java.util.List;

public class Theme{

	private ThemeName name;
	private String description;
	private float note;
	private List<Criterion> criteria;

	public Theme(String name, String description, float note, List<Criterion> criteria) {
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

	public void setName(ThemeName name) {
		this.name = name;
	}

	public ThemeName getName() {
		return name;
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
	
	

}
