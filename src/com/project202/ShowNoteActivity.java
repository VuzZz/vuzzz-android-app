package com.project202;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.AnimationRes;
import com.project202.actionbar.ActionBarActivity;
import com.project202.adapter.SimplePagerAdapter;
import com.project202.model.Criterion;
import com.project202.model.Rating;
import com.project202.model.Theme;
import com.project202.model.ThemeName;
import com.project202.views.HistoryView_;
import com.project202.views.OnHistoryFocusedListener;
import com.project202.views.RatingDetailsView_;
import com.project202.views.RatingView_;
import com.project202.views.SettingsView;
import com.viewpagerindicator.TitlePageIndicator;

@EActivity(R.layout.show_note)
@OptionsMenu(R.menu.show_note_menu)
public class ShowNoteActivity extends ActionBarActivity {

	@Extra("address")
	String address;
	
	@Extra("rating")
	Rating rating;

	@ViewById
	SettingsView settingsView;

	@AnimationRes
	Animation slideInFromBottom;

	@AnimationRes
	Animation slideOutFromTop;

	@ViewById(R.id.view_pager)
	protected ViewPager viewPager;

	private List<OnHistoryFocusedListener> onHistoryFocusedListeners;

	@ViewById
	TitlePageIndicator titles;

	private SimplePagerAdapter pagerAdapter;

	@AfterViews
	public void afterViews() {
		
		onHistoryFocusedListeners = new ArrayList<OnHistoryFocusedListener>();
		
		setTitle(address);

		// Creating ViewPager Views
		RatingView_ ratingView = new RatingView_(this);
		RatingDetailsView_ ratingDetailsView = new RatingDetailsView_(this);
		final HistoryView_ historyView = new HistoryView_(this);
		// Inflating layouts
		ratingView.onFinishInflate();
		ratingDetailsView.onFinishInflate();
		historyView.onFinishInflate();

		// Injecting some content to test
		ratingView.setValuesFromRating(getTestPrintedRating());

		// Storing views

		List<View> views = Arrays.<View> asList(historyView, ratingView, ratingDetailsView);

		List<String> pageTitles = Arrays.asList(getString(R.string.history_title), getString(R.string.rating_title), getString(R.string.rating_details_title));
		// Initializing PagerAdapter
		pagerAdapter = new SimplePagerAdapter(views, pageTitles);

		// Initializing ViewPager
		viewPager.setAdapter(pagerAdapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (position==0){
					for (OnHistoryFocusedListener listener : onHistoryFocusedListeners){
						listener.onHistoryFocused();
					}
				} else {
					for (OnHistoryFocusedListener listener : onHistoryFocusedListeners){
						listener.onHistoryHidden();
					}
				}
				titles.onPageSelected(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				titles.onPageScrollStateChanged(state);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				titles.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		});

		titles.setViewPager(viewPager);
		
		viewPager.setCurrentItem(1);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Uri data = intent.getData();

		// When the application is started through Intent
		if (data != null) {
			Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
		}
	}

	// Until we implement services
	public static Rating getTestPrintedRating() {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(new Criterion("Test", "hahahahahhahahahhaha", 9.0f));
		criteria.add(new Criterion("Test", "hahahahahhahahahhaha", 4.0f));
		criteria.add(new Criterion("Test", "hahahahahhahahahhaha", 2.0f));

		List<Theme> themes = new ArrayList<Theme>();
		themes.add(new Theme(ThemeName.LEISURE.toString(), "", 1.0f, criteria));
		themes.add(new Theme(ThemeName.CULTURE.toString(), "", 3.0f, criteria));
		themes.add(new Theme(ThemeName.INSTITUTIONS.toString(), "", 6.0f, criteria));
		themes.add(new Theme(ThemeName.NATURE.toString(), "", 8.0f, criteria));
		themes.add(new Theme(ThemeName.SHOPS.toString(), "", 6.0f, criteria));
		themes.add(new Theme(ThemeName.TRANSIT.toString(), "", 6.0f, criteria));

		return new Rating(themes);
	}
	
	public void addOnSettingsFocusedHandler(OnHistoryFocusedListener convertView) {
		onHistoryFocusedListeners.add(convertView);
	}

	@OptionsItem
	void menuSettingsSelected() {
		if (settingsView.getVisibility() == View.VISIBLE) {
			settingsView.startAnimation(slideOutFromTop);
			settingsView.setVisibility(View.GONE);
		} else {
			settingsView.startAnimation(slideInFromBottom);
			settingsView.setVisibility(View.VISIBLE);
		}
	}

}