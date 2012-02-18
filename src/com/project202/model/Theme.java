package com.project202.model;

public class Theme{

	private ThemeName name;
	private String description;
	private float note;

	public Theme(String name, String description, float note) {
		this.name = ThemeName.valueOf(name);
		this.description = description;
		this.note = note;
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
