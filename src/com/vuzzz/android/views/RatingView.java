package com.vuzzz.android.views;

import static com.vuzzz.android.views.RatingView.ThemeNameOrAll.all;
import static com.vuzzz.android.views.RatingView.ThemeNameOrAll.themeName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.Touch;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DimensionRes;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.vuzzz.android.OnRatingSwitchedListener;
import com.vuzzz.android.OnSettingsUpdatedListener;
import com.vuzzz.android.R;
import com.vuzzz.android.address.SettingsPreferences_;
import com.vuzzz.android.component.RatingButton;
import com.vuzzz.android.model.Rating;
import com.vuzzz.android.model.ThemeName;
import com.vuzzz.android.model.Weighted;

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

	@ViewById(R.id.institutions_rating)
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
	
	@DimensionRes(R.dimen.rating_picto)
	Float pictoWidth;

	OnRatingClickListener listener;

	public RatingView(Context context) {
		super(context);
	}

	@AfterViews
	void afterViews() {
		
		int width = pictoWidth.intValue();
		
		globalRating.setBackgroundColor(0xFFa3b7b5);
		globalRating.getFooterLayout().setBackgroundColor(0xFF7fa39f);
		globalRating.setTextSize(40);
		globalPicto.setBounds(0, 0, width, width);
		globalRating.setPicto(globalPicto);
		globalRating.setTheme("NOTE GLOBALE");

		cultureRating.setBackgroundColor(ThemeName.CULTURE.getLightColor());
		cultureRating.getFooterLayout().setBackgroundColor(ThemeName.CULTURE.getDarkColor());
		cultureRating.setTheme(ThemeName.CULTURE.getName());
		culturePicto.setBounds(0, 0, width, width);
		cultureRating.setPicto(culturePicto);

		natureRating.setBackgroundColor(ThemeName.NATURE.getLightColor());
		natureRating.getFooterLayout().setBackgroundColor(ThemeName.NATURE.getDarkColor());
		natureRating.setTheme(ThemeName.NATURE.getName());
		naturePicto.setBounds(0, 0, width, width);
		natureRating.setPicto(naturePicto);

		transitRating.setBackgroundColor(ThemeName.TRANSIT.getLightColor());
		transitRating.getFooterLayout().setBackgroundColor(ThemeName.TRANSIT.getDarkColor());
		transitRating.setTheme(ThemeName.TRANSIT.getName());
		transitPicto.setBounds(0, 0, width, width);
		transitRating.setPicto(transitPicto);

		leisureRating.setBackgroundColor(ThemeName.LEISURE.getLightColor());
		leisureRating.getFooterLayout().setBackgroundColor(ThemeName.LEISURE.getDarkColor());
		leisureRating.setTheme(ThemeName.LEISURE.getName());
		leisurePicto.setBounds(0, 0, width, width);
		leisureRating.setPicto(leisurePicto);

		securityRating.setBackgroundColor(ThemeName.SECURITY.getLightColor());
		securityRating.getFooterLayout().setBackgroundColor(ThemeName.SECURITY.getDarkColor());
		securityRating.setTheme(ThemeName.SECURITY.getName());
		securityPicto.setBounds(0, 0, width, width);
		securityRating.setPicto(securityPicto);

		shopsRating.setBackgroundColor(ThemeName.SHOPS.getLightColor());
		shopsRating.getFooterLayout().setBackgroundColor(ThemeName.SHOPS.getDarkColor());
		shopsRating.setTheme(ThemeName.SHOPS.getName());
		shopsPicto.setBounds(0, 0, width, width);
		shopsRating.setPicto(shopsPicto);
	}

	public void setOnRatingClickListener(OnRatingClickListener listener) {
		this.listener = listener;
	}

	// Click Events	
	@Click(R.id.global_rating)
	void onGlobalRatingClick() {
		listener.onRatingClickListener(all());
		globalRating.setBackgroundColor(0xFFa3b7b5);
		globalRating.getFooterLayout().setBackgroundColor(0xFF7fa39f);
	}
	
	@Click(R.id.culture_rating)
	public void onCultureRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.CULTURE));
		cultureRating.setBackgroundColor(ThemeName.CULTURE.getLightColor());
		cultureRating.getFooterLayout().setBackgroundColor(ThemeName.CULTURE.getDarkColor());
	}
	
	@Click(R.id.nature_rating)
	public void onNatureRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.NATURE));
		natureRating.setBackgroundColor(ThemeName.NATURE.getLightColor());
		natureRating.getFooterLayout().setBackgroundColor(ThemeName.NATURE.getDarkColor());
	}
	
	@Click(R.id.transit_rating)
	public void onTransitRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.TRANSIT));
		transitRating.setBackgroundColor(ThemeName.TRANSIT.getLightColor());
		transitRating.getFooterLayout().setBackgroundColor(ThemeName.TRANSIT.getDarkColor());
	}
	
	@Click(R.id.institutions_rating)
	public void onSecurityRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.SECURITY));
		securityRating.setBackgroundColor(ThemeName.SECURITY.getLightColor());
		securityRating.getFooterLayout().setBackgroundColor(ThemeName.SECURITY.getDarkColor());
	}
	
	@Click(R.id.shops_rating)
	public void onShopsRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.SHOPS));
		shopsRating.setBackgroundColor(ThemeName.SHOPS.getLightColor());
		shopsRating.getFooterLayout().setBackgroundColor(ThemeName.SHOPS.getDarkColor());
	}
	
	@Click(R.id.leisure_rating)
	public void onLeisureRatingClick(View v){
		listener.onRatingClickListener(themeName(ThemeName.LEISURE));
		leisureRating.setBackgroundColor(ThemeName.LEISURE.getLightColor());
		leisureRating.getFooterLayout().setBackgroundColor(ThemeName.LEISURE.getDarkColor());
	}

	// Touch Events
	@Touch(R.id.global_rating)
	public boolean onGlobalRatingTouch(MotionEvent evt){
		globalRating.setBackgroundColor(0x99a3b7b5);
		globalRating.getFooterLayout().setBackgroundColor(0x997fa39f);
		return false;
	}
	
	@Touch(R.id.culture_rating)
	public boolean onCultureRatingTouch(MotionEvent evt){
		cultureRating.setBackgroundColor(ThemeName.CULTURE.getPressedLightColor());
		cultureRating.getFooterLayout().setBackgroundColor(ThemeName.CULTURE.getPressedDarkColor());
		return false;
	}
	
	@Touch(R.id.nature_rating)
	public boolean onNatureRatingTouch(MotionEvent evt){
		natureRating.setBackgroundColor(ThemeName.NATURE.getPressedLightColor());
		natureRating.getFooterLayout().setBackgroundColor(ThemeName.NATURE.getPressedDarkColor());
		return false;
	}
	
	@Touch(R.id.transit_rating)
	public boolean onTransitRatingTouch(MotionEvent evt){
		transitRating.setBackgroundColor(ThemeName.TRANSIT.getPressedLightColor());
		transitRating.getFooterLayout().setBackgroundColor(ThemeName.TRANSIT.getPressedDarkColor());
		return false;
	}
	
	@Touch(R.id.institutions_rating)
	public boolean onSecurityRatingTouch(MotionEvent evt){
		securityRating.setBackgroundColor(ThemeName.SECURITY.getPressedLightColor());
		securityRating.getFooterLayout().setBackgroundColor(ThemeName.SECURITY.getPressedDarkColor());
		return false;
	}
	
	@Touch(R.id.shops_rating)
	public boolean onShopsRatingTouch(MotionEvent evt){
		shopsRating.setBackgroundColor(ThemeName.SHOPS.getPressedLightColor());
		shopsRating.getFooterLayout().setBackgroundColor(ThemeName.SHOPS.getPressedDarkColor());
		return false;
	}
	
	@Touch(R.id.leisure_rating)
	public boolean onLeisureRatingTouch(MotionEvent evt){
		leisureRating.setBackgroundColor(ThemeName.LEISURE.getPressedLightColor());
		leisureRating.getFooterLayout().setBackgroundColor(ThemeName.LEISURE.getPressedDarkColor());
		return false;
	}

	public void setValuesFromRating(Rating rating) {
		this.currentRating = rating;

		globalRating.setMark(Weighted.getWeightedMark(rating, preferences));
		cultureRating.setMark(rating.getThemeMark(ThemeName.CULTURE));
		natureRating.setMark(rating.getThemeMark(ThemeName.NATURE));
		transitRating.setMark(rating.getThemeMark(ThemeName.TRANSIT));
		securityRating.setMark(rating.getThemeMark(ThemeName.SECURITY));
		shopsRating.setMark(rating.getThemeMark(ThemeName.SHOPS));
		leisureRating.setMark(rating.getThemeMark(ThemeName.LEISURE));
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
