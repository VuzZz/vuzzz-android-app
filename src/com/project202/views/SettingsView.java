package com.project202.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.project202.OnSettingsUpdatedListener;
import com.project202.address.AbstractSeekBarChangeListener;
import com.project202.address.SettingsPreferences_;
import com.vuzzz.android.R;

@EViewGroup(R.layout.settings)
public class SettingsView extends LinearLayout {
	
	Context context;
	
	@ViewById
	SeekBar cultureWeight;
	@ViewById
	SeekBar securityWeight;
	@ViewById
	SeekBar leisureWeight;
	@ViewById
	SeekBar natureWeight;
	@ViewById
	SeekBar shopsWeight;
	@ViewById
	SeekBar transitWeight;
	
	OnSettingsUpdatedListener listener;
	
	@Pref
	SettingsPreferences_ settingsPreferences;
	
	public SettingsView(Context context) {
		super(context);
		this.context = context;
	}
	
	public SettingsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public SettingsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	@AfterViews
	public void afterViews(){
		// Sets default values
		cultureWeight.setProgress(settingsPreferences.cultureWeight().get());
		securityWeight.setProgress(settingsPreferences.securityWeight().get());
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
				listener.onSettingsUpdated();
			}
		});
		securityWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.securityWeight().put(progress);
				listener.onSettingsUpdated();
			}
		});
		leisureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.leisureWeight().put(progress);
				listener.onSettingsUpdated();
			}
		});
		natureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.natureWeight().put(progress);
				listener.onSettingsUpdated();
			}
		});
		shopsWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.shopsWeight().put(progress);
				listener.onSettingsUpdated();
			}
		});
		transitWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				settingsPreferences.transitWeight().put(progress);
				listener.onSettingsUpdated();
			}
		});
	}
	
	public void setOnSettingsUpdatedListener(OnSettingsUpdatedListener listener){
		this.listener = listener;
	}
}
