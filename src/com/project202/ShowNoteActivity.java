package com.project202;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.actionbar.ActionBarActivity;
import com.project202.model.Criterion;
import com.project202.model.Rating;
import com.project202.model.Theme;
import com.project202.model.ThemeName;
import com.project202.views.HistoryView_;
import com.project202.views.MyLinearLayout;
import com.project202.views.RatingDetailsView_;
import com.project202.views.RatingView_;
import com.project202.views.SettingsView_;

@EActivity(R.layout.show_note)
public class ShowNoteActivity extends ActionBarActivity {
	
	@Extra("address")
	String address;
	
	private final int NUM_VIEWS = 4;
	private final Map<Integer,MyLinearLayout> views = new HashMap<Integer,MyLinearLayout>();
	
	@ViewById(R.id.view_pager)
	protected ViewPager viewPager;
	
	private CustomPagerAdapter pagerAdapter;
	
	private class CustomPagerAdapter extends PagerAdapter{
		
		@Override
		public CharSequence getPageTitle(int position) {
			return views.get(position).getTitle(ShowNoteActivity.this);
		}
		
		@Override
		public int getCount() {
			return NUM_VIEWS;
		}
		
		@Override
		public Object instantiateItem(ViewGroup viewPager, int position) {
			View v = views.get(position);
			viewPager.addView(v);
			return v;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (MyLinearLayout) object;
		}
		
		@Override
		public void destroyItem(ViewGroup viewPager, int position, Object object) {
			viewPager.removeView((MyLinearLayout) object);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
	
	@AfterViews
	public void afterViews(){
		
		setTitle(address);
		
		// Creating ViewPager Views
		RatingView_ ratingView = new RatingView_(this);
		RatingDetailsView_ ratingDetailsView = new RatingDetailsView_(this);
		HistoryView_ historyView = new HistoryView_(this);
		SettingsView_ settingsView = new SettingsView_(this);
		
		// Inflating layouts
		ratingView.onFinishInflate();
		ratingDetailsView.onFinishInflate();
		historyView.onFinishInflate();
		settingsView.onFinishInflate();
		
		// Injecting some content to test
		ratingView.setValuesFromRating(getTestPrintedRating());
		
		// Storing views
		views.put(0, historyView);
		views.put(1, ratingView);
		views.put(2, ratingDetailsView);
		views.put(3, settingsView);
		
		// Initializing PagerAdapter
		pagerAdapter = new CustomPagerAdapter();
		
		// Initializing ViewPager
		viewPager.setAdapter(pagerAdapter);
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
	public static Rating getTestPrintedRating(){
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
}