package com.project202.model;

public class PrintedDetail {
	private String name;
	private String description;
	private float note;
	private boolean criterion;

	public PrintedDetail(String name, String description, float note, boolean criterion) {
		super();
		this.name = name;
		this.description = description;
		this.note = note;
		this.criterion = criterion;
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
	public boolean isCriterion() {
		return criterion;
	}
	public void setCriterion(boolean criterion) {
		this.criterion = criterion;
	}
	
	
	
}
