package com.project202.model;

public enum ThemeName {
	LEISURE("LEISURE"), CULTURE("CULTURE"), SHOPS("SHOPS"), SECURITY("SECURITY"), TRANSIT("TRANSIT"), NATURE("NATURE");
	
	private final String name;
	
	private ThemeName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
