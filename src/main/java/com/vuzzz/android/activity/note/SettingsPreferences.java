package com.vuzzz.android.activity.note;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultInt;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface SettingsPreferences {

	@DefaultInt(1)
	int cultureWeight();
	@DefaultInt(1)
	int shopsWeight();
	@DefaultInt(1)
	int natureWeight();
	@DefaultInt(1)
	int transitWeight();
	@DefaultInt(1)
	int leisureWeight();
	@DefaultInt(1)
	int securityWeight();

}