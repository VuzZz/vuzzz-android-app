package com.project202.model;

public enum ThemeName {
	LEISURE("LOISIRS", 0xFFFF4444), //
	CULTURE("CULTURE", 0xFFFFBB33), //
	SHOPS("COMMERCES", 0xFF99CC00), //
	SECURITY("SÉCURITÉ", 0xFFAA66CC), //
	TRANSIT("TRANSPORT", 0xFF33B5E5), //
	NATURE("NATURE", 0xFFff8b32);
	
	private final String name;
	private final int color;
	
	private ThemeName(String name, int color){
		this.name = name;
		this.color = color;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getColor(){
		return this.color;
	}
}
