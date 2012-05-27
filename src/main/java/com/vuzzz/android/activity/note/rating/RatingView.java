package com.vuzzz.android.activity.note.rating;

import static com.vuzzz.android.activity.note.rating.RatingView.ThemeNameOrAll.all;
import static com.vuzzz.android.activity.note.rating.RatingView.ThemeNameOrAll.themeName;
import static com.vuzzz.android.common.model.ThemeName.CULTURE;
import static com.vuzzz.android.common.model.ThemeName.GLOBAL;
import static com.vuzzz.android.common.model.ThemeName.LEISURE;
import static com.vuzzz.android.common.model.ThemeName.NATURE;
import static com.vuzzz.android.common.model.ThemeName.SECURITY;
import static com.vuzzz.android.common.model.ThemeName.SHOPS;
import static com.vuzzz.android.common.model.ThemeName.TRANSIT;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.vuzzz.android.R;
import com.vuzzz.android.activity.note.OnRatingSwitchedListener;
import com.vuzzz.android.activity.note.OnSettingsUpdatedListener;
import com.vuzzz.android.activity.note.SettingsPreferences_;
import com.vuzzz.android.common.model.Rating;
import com.vuzzz.android.common.model.ThemeName;
import com.vuzzz.android.common.model.Weighted;

@EViewGroup(R.layout.rating)
public class RatingView extends FrameLayout implements OnSettingsUpdatedListener, OnRatingSwitchedListener {

	public interface OnRatingClickListener {
		void onRatingClickListener(ThemeNameOrAll themeNameOrAll);
	}

	public static class ThemeNameOrAll {

		public static ThemeNameOrAll all() {
			return new ThemeNameOrAll(null);
		}

		public static ThemeNameOrAll themeName(ThemeName themeName) {
			if (themeName == null) {
				throw new IllegalArgumentException("themeName should not be null");
			}
			return new ThemeNameOrAll(themeName);
		}

		private final ThemeName themeName;

		private ThemeNameOrAll(ThemeName themeName) {
			this.themeName = themeName;
		}

		public ThemeName asThemeName() {
			return themeName;
		}

		public boolean isAll() {
			return themeName == null;
		}

	}

	private static final Interpolator interpolator = new DecelerateInterpolator();

	@ViewById(R.id.global_rating)
	RatingButton globalRating;

	@ViewById(R.id.culture_rating)
	RatingButton cultureRating;

	private Rating currentRating;

	private long animationStart;

	@Pref
	SettingsPreferences_ preferences;

	@ViewById(R.id.nature_rating)
	RatingButton natureRating;

	@ViewById(R.id.transit_rating)
	RatingButton transitRating;

	@ViewById(R.id.security_rating)
	RatingButton securityRating;

	@ViewById(R.id.shops_rating)
	RatingButton shopsRating;

	@ViewById(R.id.leisure_rating)
	RatingButton leisureRating;

	@DrawableRes(R.drawable.ic_rating_nature)
	Drawable naturePicto;

	@DrawableRes(R.drawable.ic_rating_culture)
	Drawable culturePicto;

	@DrawableRes(R.drawable.ic_rating_transit)
	Drawable transitPicto;

	@DrawableRes(R.drawable.ic_rating_leasure)
	Drawable leisurePicto;

	@DrawableRes(R.drawable.ic_rating_security)
	Drawable securityPicto;

	@DrawableRes(R.drawable.ic_rating_shop)
	Drawable shopsPicto;

	@DrawableRes(R.drawable.ic_rating_global)
	Drawable globalPicto;

	OnRatingClickListener listener;

	public RatingView(Context context) {
		super(context);
	}

	@AfterViews
	void afterViews() {

		globalRating.setTextSize(40);
		globalRating.setPicto(globalPicto);
		globalRating.setTheme(GLOBAL);

		cultureRating.setTheme(CULTURE);
		cultureRating.setPicto(culturePicto);

		natureRating.setTheme(NATURE);
		natureRating.setPicto(naturePicto);

		transitRating.setTheme(TRANSIT);
		transitRating.setPicto(transitPicto);

		leisureRating.setTheme(LEISURE);
		leisureRating.setPicto(leisurePicto);

		securityRating.setTheme(ThemeName.SECURITY);
		securityRating.setPicto(securityPicto);

		shopsRating.setTheme(ThemeName.SHOPS);
		shopsRating.setPicto(shopsPicto);
	}

	public void setOnRatingClickListener(OnRatingClickListener listener) {
		this.listener = listener;
	}

	// Click Events
	@Click
	void globalRatingClicked() {
		listener.onRatingClickListener(all());
	}

	@Click
	public void cultureRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.CULTURE));
	}

	@Click
	public void natureRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.NATURE));
	}

	@Click
	public void transitRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.TRANSIT));
	}

	@Click
	public void securityRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.SECURITY));
	}

	@Click
	public void shopsRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.SHOPS));
	}

	@Click
	public void leisureRatingClicked(View v) {
		listener.onRatingClickListener(themeName(ThemeName.LEISURE));
	}

	public void setValuesFromRating(Rating rating) {
		this.currentRating = rating;

		globalRating.setMark(Weighted.getWeightedMark(rating, preferences));
		
		if (rating.getTheme(CULTURE).isRelevant()) {
			cultureRating.setMark(rating.getThemeMark(CULTURE));
		} else {
			cultureRating.setDisabled();
		}
		if (rating.getTheme(NATURE).isRelevant()) {
			natureRating.setMark(rating.getThemeMark(NATURE));
		} else {
			natureRating.setDisabled();
		}
		if (rating.getTheme(TRANSIT).isRelevant()) {
			transitRating.setMark(rating.getThemeMark(TRANSIT));
		} else {
			transitRating.setDisabled();
		}
		if (rating.getTheme(SECURITY).isRelevant()) {
			securityRating.setMark(rating.getThemeMark(SECURITY));
		} else {
			securityRating.setDisabled();
		}
		if (rating.getTheme(SHOPS).isRelevant()) {
			shopsRating.setMark(rating.getThemeMark(SHOPS));
		} else {
			shopsRating.setDisabled();
		}
		if (rating.getTheme(LEISURE).isRelevant()) {
			leisureRating.setMark(rating.getThemeMark(LEISURE));
		} else {
			leisureRating.setDisabled();
		}
	}

	@Override
	public void onSettingsUpdated() {
		onRatingFocused();
		setValuesFromRating(currentRating);
	}

	public void onRatingHidden() {
		globalRating.updateMarkDisplay(0);
		cultureRating.updateMarkDisplay(0);
		natureRating.updateMarkDisplay(0);
		transitRating.updateMarkDisplay(0);
		securityRating.updateMarkDisplay(0);
		shopsRating.updateMarkDisplay(0);
		leisureRating.updateMarkDisplay(0);
	}

	public void onRatingFocused() {
		animationStart = System.currentTimeMillis();
		updateViews();
	}

	@UiThread(delay = 40)
	void updateDelayed() {
		updateViews();
	}

	void updateViews() {
		final float animationDuration = 1000f;
		long elapsedTime = System.currentTimeMillis() - animationStart;
		float percent;
		if (elapsedTime <= animationDuration) {
			percent = interpolator.getInterpolation(elapsedTime / animationDuration);
			updateDelayed();
		} else {
			percent = 1;
		}
		globalRating.updateMarkDisplay(percent);
		cultureRating.updateMarkDisplay(percent);
		natureRating.updateMarkDisplay(percent);
		transitRating.updateMarkDisplay(percent);
		securityRating.updateMarkDisplay(percent);
		shopsRating.updateMarkDisplay(percent);
		leisureRating.updateMarkDisplay(percent);
	}

	public SettingsPreferences_ getPreferences() {
		return preferences;
	}

	@Override
	public void onRatingSwitched(Rating rating) {
		this.currentRating = rating;
		setValuesFromRating(currentRating);
	}

}
