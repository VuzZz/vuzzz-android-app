package com.project202;

public abstract class Config {
	
	public static final boolean RELEASE = false;
	
	public static final boolean LOG_TO_CONSOLE = true && !RELEASE;
	
	private Config() {
	}
	
}