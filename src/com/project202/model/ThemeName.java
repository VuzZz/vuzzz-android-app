package com.project202.model;

public enum ThemeName {
	LEISURE("LOISIRS", 0xFFFF4444, 0xFFCC0000), //
	CULTURE("CULTURE", 0xFFFFBB33, 0xFFFF8800), //
	SHOPS("COMMERCES", 0xFF99CC00, 0xFF669900), //
	SECURITY("SÉCURITÉ", 0xFFAA66CC, 0xFF9933CC), //
	TRANSIT("TRANSPORT", 0xFF33B5E5, 0xFF0099CC), //
	NATURE("NATURE", 0xFFff8b32, 0xFFd67124);

	private final String name;
	private final int lightColor;
	private final int darkColor;

	private ThemeName(String name, int lightColor, int darkColor) {
		this.name = name;
		this.lightColor = lightColor;
		this.darkColor = darkColor;
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
