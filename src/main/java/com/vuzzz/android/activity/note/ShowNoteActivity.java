package com.vuzzz.android.activity.note;

import static android.view.View.GONE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.AnimationRes;
import com.viewpagerindicator.TitlePageIndicator;
import com.vuzzz.android.R;
import com.vuzzz.android.activity.note.details.RatingDetailsView_;
import com.vuzzz.android.activity.note.history.HistoryView_;
import com.vuzzz.android.activity.note.history.OnHistoryFocusedListener;
import com.vuzzz.android.activity.note.rating.RatingView.OnRatingClickListener;
import com.vuzzz.android.activity.note.rating.RatingView.ThemeNameOrAll;
import com.vuzzz.android.activity.note.rating.RatingView_;
import com.vuzzz.android.common.VuzZzConfig;
import com.vuzzz.android.common.helper.HomeHelper;
import com.vuzzz.android.common.model.Rating;
import com.vuzzz.android.common.model.Weighted;
import com.vuzzz.android.common.util.AbstractAnimationListener;

@NoTitle
@EActivity
public class ShowNoteActivity extends Activity implements OnRatingClickListener, OnSettingsUpdatedListener, OnRatingSelectedListener {

	@Extra("rating")
	Rating rating;

	@ViewById
	SettingsView settingsView;

	@ViewById
	View shareButton;

	@ViewById
	HelpView helpView;

	@AnimationRes
	Animation slideInFromBottom;

	@AnimationRes
	Animation slideOutFromTop;

	@ViewById
	View warningText;

	@ViewById(R.id.view_pager)
	protected ViewPager viewPager;

	private List<OnHistoryFocusedListener> onHistoryFocusedListeners;

	private RatingDetailsView_ ratingDetailsView;

	@ViewById
	TitlePageIndicator titles;

	@ViewById
	TextView titleView;

	@AnimationRes
	Animation slideOutToTop;

	@Bean
	ShareManager shareManager;

	@ViewById
	View actionBar;

	private SimplePagerAdapter pagerAdapter;

	private List<OnRatingSwitchedListener> onRatingSwitchedListener = new ArrayList<OnRatingSwitchedListener>();

	private List<OnSettingsUpdatedListener> onSettingsUpdatedListeners = new ArrayList<OnSettingsUpdatedListener>();

	private RatingView_ ratingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (VuzZzConfig.MUI) {
			setContentView(R.layout.show_note_mui);
		} else {
			setContentView(R.layout.show_note);
		}

	}

	@AfterViews
	public void afterViews() {

		if (VuzZzConfig.MUI) {
			shareButton.setVisibility(View.GONE);
		}

		onHistoryFocusedListeners = new ArrayList<OnHistoryFocusedListener>();

		ratingView = new RatingView_(this);
		ratingDetailsView = new RatingDetailsView_(this);
		final HistoryView_ historyView = new HistoryView_(this, this);

		// Inflating layouts
		ratingView.onFinishInflate();
		ratingDetailsView.onFinishInflate();
		historyView.onFinishInflate();

		// Registering listeners
		onSettingsUpdatedListeners.add(ratingView);
		onSettingsUpdatedListeners.add(historyView);
		onSettingsUpdatedListeners.add(ratingDetailsView);
		onRatingSwitchedListener.add(ratingView);
		onRatingSwitchedListener.add(ratingDetailsView);

		// Injecting content
		ratingView.setOnRatingClickListener(this);
		settingsView.setOnSettingsUpdatedListener(this);

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
				if (position == 0) {
					for (OnHistoryFocusedListener listener : onHistoryFocusedListeners) {
						listener.onHistoryFocused();
					}
				} else {
					for (OnHistoryFocusedListener listener : onHistoryFocusedListeners) {
						listener.onHistoryHidden();
					}
				}

				if (position == 1) {
					ratingView.onRatingFocused();
				} else {
					ratingView.onRatingHidden();
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

		List<Rating> ratings = loadRatingsFromFiles();
		historyView.setRatings(ratings);

		boolean ratingNull = rating == null;

		if (ratingNull && !ratings.isEmpty())
			rating = ratings.get(0);

		ratingView.setValuesFromRating(rating);
		ratingDetailsView.setRating(rating);

		titleView.setText(rating.address);

		if (ratingNull) {

			viewPager.setCurrentItem(0);
		} else {
			viewPager.setCurrentItem(1);

		}

		hideWarningAfterDelay();
	}

	@UiThread(delay = 5000)
	public void hideWarningAfterDelay() {
		warningText.startAnimation(slideOutToTop);
		slideOutToTop.setAnimationListener(new AbstractAnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				warningText.setVisibility(GONE);
			}
		});
	}

	private List<Rating> loadRatingsFromFiles() {
		File[] historyFiles = Rating.listHistoryFilesSortedByCreationDesc(this);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
		objectMapper.configure(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS, false);
		objectMapper.configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, false);

		List<Rating> ratings = new ArrayList<Rating>();

		
		for (File histFile : historyFiles) {
			try {
				ratings.add(objectMapper.readValue(histFile, Rating.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ratings;
	}

	public void addOnSettingsFocusedHandler(OnHistoryFocusedListener convertView) {
		onHistoryFocusedListeners.add(convertView);
	}

	@Click
	void settingsButtonClicked() {
		if (settingsView.getVisibility() == View.VISIBLE) {
			hideSettings();
		} else {
			showSettings();
			if (helpView.getVisibility() == View.VISIBLE) {
				hideHelp();
			}
		}
	}

	private void hideSettings() {
		settingsView.startAnimation(slideOutFromTop);
		settingsView.setVisibility(View.GONE);
	}

	private void showSettings() {
		settingsView.startAnimation(slideInFromBottom);
		settingsView.setVisibility(View.VISIBLE);
	}

	@Click
	void helpButtonClicked() {
		if (helpView.getVisibility() == View.VISIBLE) {
			hideHelp();
		} else {
			showHelp();
			if (settingsView.getVisibility() == View.VISIBLE) {
				hideSettings();
			}
		}
	}

	private void hideHelp() {
		helpView.startAnimation(slideOutFromTop);
		helpView.setVisibility(View.GONE);
	}

	private void showHelp() {
		helpView.startAnimation(slideInFromBottom);
		helpView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onRatingClickListener(ThemeNameOrAll themeNameOrAll) {
		ratingDetailsView.onRatingClickListener(themeNameOrAll);
		viewPager.setCurrentItem(2);
	}

	@Override
	public void onBackPressed() {
		if (settingsView.getVisibility() == View.VISIBLE) {
			settingsButtonClicked();
		} else if (helpView.getVisibility() == View.VISIBLE) {
			helpButtonClicked();
		} else {
			super.onBackPressed();
		}
	}

	@Click
	void homeClicked() {
		HomeHelper.goToHome(this);
	}

	@Override
	public void onSettingsUpdated() {
		for (OnSettingsUpdatedListener l : onSettingsUpdatedListeners)
			l.onSettingsUpdated();
	}

	@Click
	void shareButtonClicked() {
		final String format = "Une adresse où il fait bon vivre : [%s] a obtenu %s/10 http://vuzzz.com/geo?q=%s,%s";
		shareManager.share(String.format(format, rating.address, String.format("%.1f", Weighted.getWeightedMark(rating, ratingView.getPreferences())), Double.toString(rating.latitude), Double.toString(rating.longitude)));

	}

	@Override
	public void onRatingSelectedListener(Rating rating) {
		for (OnRatingSwitchedListener l : onRatingSwitchedListener) {
			l.onRatingSwitched(rating);
		}
		viewPager.setCurrentItem(1, true);
	}

}