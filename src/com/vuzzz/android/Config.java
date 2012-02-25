package com.vuzzz.android;

public abstract class Config {
	
	public static final boolean RELEASE = true;
	
	public static final boolean LOG_TO_CONSOLE = true && !RELEASE;
	
	public static final boolean MUI = true;
	
	private Config() {
	}
	
}
