package com.project202.model;

public class Criterion {
	private String name;
	private String description;
	private float note;
	
	public Criterion(String name, String description, float note) {
		super();
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
}
