package com.project202.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.project202.OnSettingsUpdatedListener;
import com.project202.R;
import com.project202.address.SettingsPreferences_;
import com.project202.component.RatingButton;
import com.project202.model.Rating;
import com.project202.model.ThemeName;
import com.project202.model.Weighted;

@EViewGroup(R.layout.rating)
public class RatingView extends LinearLayout implements OnSettingsUpdatedListener {
	
	public interface OnRatingClickListener{
		void onRatingClickListener(ThemeName themeName);
	}

	private static final int PICTO_WIDTH = 25;

	@ViewById(R.id.global_rating)
	RatingButton globalRating;
	
	@ViewById(R.id.culture_rating)
	RatingButton cultureRating;
	
	private Rating currentRating;
	
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
	
	OnRatingClickListener listener;
	
	public RatingView(Context context) {
		super(context);
	}
	
	@AfterViews
	void afterViews() {
		globalRating.setBackgroundColor(ThemeName.CULTURE.getLightColor());
		globalRating.getFooterLayout().setBackgroundColor(ThemeName.CULTURE.getDarkColor());
		globalRating.getThemeTextView().setText("NOTE TOTAL");
		
		cultureRating.setBackgroundColor(ThemeName.CULTURE.getLightColor());
		cultureRating.getFooterLayout().setBackgroundColor(ThemeName.CULTURE.getDarkColor());
		cultureRating.getThemeTextView().setText(ThemeName.CULTURE.getName());
		culturePicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		cultureRating.getThemeTextView().setCompoundDrawables(culturePicto.getCurrent(), null, null, null);

		natureRating.setBackgroundColor(ThemeName.NATURE.getLightColor());
		natureRating.getFooterLayout().setBackgroundColor(ThemeName.NATURE.getDarkColor());
		natureRating.getThemeTextView().setText(ThemeName.NATURE.getName());
		naturePicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		natureRating.getThemeTextView().setCompoundDrawables(naturePicto, null, null, null);
		
		transitRating.setBackgroundColor(ThemeName.TRANSIT.getLightColor());
		transitRating.getFooterLayout().setBackgroundColor(ThemeName.TRANSIT.getDarkColor());
		transitRating.getThemeTextView().setText(ThemeName.TRANSIT.getName());
		transitPicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		transitRating.getThemeTextView().setCompoundDrawables(transitPicto, null, null, null);

		leisureRating.setBackgroundColor(ThemeName.LEISURE.getLightColor());
		leisureRating.getFooterLayout().setBackgroundColor(ThemeName.LEISURE.getDarkColor());
		leisureRating.getThemeTextView().setText(ThemeName.LEISURE.getName());
		leisurePicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		leisureRating.getThemeTextView().setCompoundDrawables(leisurePicto, null, null, null);

		securityRating.setBackgroundColor(ThemeName.SECURITY.getLightColor());
		securityRating.getFooterLayout().setBackgroundColor(ThemeName.SECURITY.getDarkColor());
		securityRating.getThemeTextView().setText(ThemeName.SECURITY.getName());
		securityPicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		securityRating.getThemeTextView().setCompoundDrawables(securityPicto, null, null, null);
		
		shopsRating.setBackgroundColor(ThemeName.SHOPS.getLightColor());
		shopsRating.getFooterLayout().setBackgroundColor(ThemeName.SHOPS.getDarkColor());
		shopsRating.getThemeTextView().setText(ThemeName.SHOPS.getName());
		shopsPicto.setBounds(0, 0, PICTO_WIDTH, PICTO_WIDTH);
		shopsRating.getThemeTextView().setCompoundDrawables(shopsPicto, null, null, null);
	}

	public void setOnRatingClickListener(OnRatingClickListener listener){
		this.listener = listener;
	}
	
	public void setValuesFromRating(Rating rating){
		this.currentRating = rating;

		globalRating.getRatingTextView().setText(Weighted.getWeightedMark(rating, preferences).toString());
		
		cultureRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.CULTURE).toString());
		cultureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.CULTURE);
			}
		});
		
		natureRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.NATURE).toString());
		natureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.NATURE);
			}
		});
		
		transitRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.TRANSIT).toString());
		transitRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.TRANSIT);
			}
		});
		
		securityRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.SECURITY).toString());
		securityRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.SECURITY);
			}
		});
		
		shopsRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.SHOPS).toString());
		shopsRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.SHOPS);
			}
		});
		
		leisureRating.getRatingTextView().setText(rating.getThemeMark(ThemeName.LEISURE).toString());
		leisureRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRatingClickListener(ThemeName.LEISURE);
			}
		});
	}

	@Override
	public void onSettingsUpdated() {
		setValuesFromRating(currentRating);
	}
	
}
