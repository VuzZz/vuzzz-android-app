package com.project202.model;

import java.io.Serializable;

public class Criterion implements Serializable, RatingDetails {
	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String description;
	public float note;
	
	public Criterion(){};
	
	public Criterion(String name, String description, float note) {
		this.name = name;
		this.description = description;
		this.note = note;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
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
		return 0;
	}
}
