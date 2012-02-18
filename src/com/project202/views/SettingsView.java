package com.project202.views;

import android.content.Context;
import android.widget.SeekBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.project202.AbstractSeekBarChangeListener;
import com.project202.R;
import com.project202.SettingsPreferences_;

@EViewGroup(R.layout.settings)
public class SettingsView extends MyLinearLayout {
	
	Context context;
	
	@ViewById
	SeekBar cultureWeight;
	@ViewById
	SeekBar institutionsWeight;
	@ViewById
	SeekBar leisureWeight;
	@ViewById
	SeekBar natureWeight;
	@ViewById
	SeekBar shopsWeight;
	@ViewById
	SeekBar transitWeight;
	
	@Pref
	SettingsPreferences_ settingsPreferences;
	
	public SettingsView(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public String getTitle(Context context) {
		return "Settings";
	}	
	
	@AfterViews
	public void afterViews(){
		// Sets default values
		cultureWeight.setProgress(settingsPreferences.cultureWeight().get());
		institutionsWeight.setProgress(settingsPreferences.institutionsWeight().get());
		leisureWeight.setProgress(settingsPreferences.leisureWeight().get());
		natureWeight.setProgress(settingsPreferences.natureWeight().get());
		shopsWeight.setProgress(settingsPreferences.shopsWeight().get());
		transitWeight.setProgress(settingsPreferences.transitWeight().get());
		
		setProgressChangeListeners();
	}
	
	private void setProgressChangeListeners(){
		cultureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.cultureWeight().put(progress);
			}
		});
		institutionsWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.institutionsWeight().put(progress);
			}
		});
		leisureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.leisureWeight().put(progress);
			}
		});
		natureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.natureWeight().put(progress);
			}
		});
		shopsWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.shopsWeight().put(progress);
			}
		});
		transitWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.transitWeight().put(progress);
			}
		});
	}
}
