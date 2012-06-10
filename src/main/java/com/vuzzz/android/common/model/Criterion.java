package com.vuzzz.android.common.model;

import java.io.Serializable;

public class Criterion implements Serializable, RatingDetails {
	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String description;
	public String id;
	public float note;
	public float coefficient;
	
	public Criterion(){};

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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public float getCoefficient() {
		return coefficient;
	}
	
	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}

}