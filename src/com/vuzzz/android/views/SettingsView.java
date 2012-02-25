package com.vuzzz.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.vuzzz.android.OnSettingsUpdatedListener;
import com.vuzzz.android.R;
import com.vuzzz.android.address.AbstractSeekBarChangeListener;
import com.vuzzz.android.address.SettingsPreferences_;

@EViewGroup(R.layout.settings)
public class SettingsView extends FrameLayout {

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
	public void afterViews() {
		// Sets default values
		cultureWeight.setProgress(settingsPreferences.cultureWeight().get());
		securityWeight.setProgress(settingsPreferences.securityWeight().get());
		leisureWeight.setProgress(settingsPreferences.leisureWeight().get());
		natureWeight.setProgress(settingsPreferences.natureWeight().get());
		shopsWeight.setProgress(settingsPreferences.shopsWeight().get());
		transitWeight.setProgress(settingsPreferences.transitWeight().get());
		setProgressChangeListeners();
	}

	private void setProgressChangeListeners() {
		cultureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateCulture(progress);
			}
		});
		securityWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateSecurity(progress);
			}
		});
		leisureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateLeisure(progress);
			}
		});
		natureWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateNature(progress);
			}
		});
		shopsWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateShops(progress);
			}
		});
		transitWeight.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateTransit(progress);
			}
		});
	}

	@Background
	void updateCulture(int progress) {
		settingsPreferences.cultureWeight().put(progress);
		settingsUpdated();
	}
	
	@Background
	void updateSecurity(int progress) {
		settingsPreferences.securityWeight().put(progress);
		settingsUpdated();
	}
	
	@Background
	void updateLeisure(int progress) {
		settingsPreferences.leisureWeight().put(progress);
		settingsUpdated();
	}
	
	@Background
	void updateNature(int progress) {
		settingsPreferences.natureWeight().put(progress);
		settingsUpdated();
	}
	
	@Background
	void updateShops(int progress) {
		settingsPreferences.shopsWeight().put(progress);
		settingsUpdated();
	}
	
	@Background
	void updateTransit(int progress) {
		settingsPreferences.transitWeight().put(progress);
		settingsUpdated();
	}

	@UiThread
	void settingsUpdated() {
		listener.onSettingsUpdated();
	}

	public void setOnSettingsUpdatedListener(OnSettingsUpdatedListener listener) {
		this.listener = listener;
	}
}
