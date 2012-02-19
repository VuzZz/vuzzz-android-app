package com.project202.model;

import java.io.Serializable;
import java.util.List;

public class Theme implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ThemeName name;
	private String description;
	private float note;
	private List<Criterion> criteria;

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
