package com.vuzzz.android.common.model;

public enum ThemeName {
	GLOBAL("NOTE GLOBALE", 0xFFa3b7b5, 0xFF7fa39f, 0x99a3b7b5, 0x997fa39f), //
	LEISURE("LOISIRS", 0xFFFF4444, 0xFFCC0000, 0x99FF4444, 0x99CC0000), //
	CULTURE("CULTURE", 0xFFFFBB33, 0xFFFF8800, 0x99FFBB33, 0x99FF8800), //
	SHOPS("COMMERCES", 0xFFff8b32, 0xFFd67124, 0x99ff8b32, 0x99d67124), //
	SECURITY("SÉCURITÉ", 0xFFAA66CC, 0xFF9933CC, 0x99AA66CC, 0x999933CC), //
	TRANSIT("TRANSPORT", 0xFF33B5E5, 0xFF0099CC, 0x9933B5E5, 0x990099CC), //
	NATURE("NATURE", 0xFF99CC00, 0xFF669900, 0x9999CC00, 0x99669900);

	private final String name;
	private final int lightColor;
	private final int darkColor;
	private final int pressedLightColor;
	private final int pressedDarkColor;

	private ThemeName(String name, int lightColor, int darkColor, int pressedLightColor, int pressedDarkColor) {
		this.name = name;
		this.lightColor = lightColor;
		this.darkColor = darkColor;
		this.pressedLightColor = pressedLightColor;
		this.pressedDarkColor = pressedDarkColor;
	}
	
	public int getPressedLightColor(){
		return pressedLightColor;
	}
	
	public int getPressedDarkColor(){
		return pressedDarkColor;
	}

	public String getName() {
		return name;
	}

	public int getLightColor() {
		return lightColor;
	}

	public int getDarkColor() {
		return darkColor;
	}

}
