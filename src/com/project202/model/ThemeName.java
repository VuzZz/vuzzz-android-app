package com.project202.model;

public enum ThemeName {
	LEISURE("LEISURE"), CULTURE("CULTURE"), SHOPS("SHOPS"), INSTITUTIONS("INSTITUTIONS"), TRANSIT("TRANSIT"), NATURE("NATURE");
	
	private String name;
	
	private ThemeName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
