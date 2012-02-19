package com.project202.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.project202.R;

@EViewGroup(R.layout.rating_button)
public class RatingButton extends RelativeLayout {

	@ViewById(R.id.rating_button_rate)
	protected TextView ratingTextView;

	@ViewById(R.id.rating_button_theme)
	protected TextView themeTextView;

	@ViewById(R.id.rating_button_footer)
	protected LinearLayout footerLayout;

	public RatingButton(Context context) {
		super(context);
	}

	public RatingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextView getRatingTextView() {
		return ratingTextView;
	}

	public void setRatingTextView(TextView ratingTextView) {
		this.ratingTextView = ratingTextView;
	}

	public TextView getThemeTextView() {
		return themeTextView;
	}

	public void setThemeTextView(TextView themeTextView) {
		this.themeTextView = themeTextView;
	}

	public LinearLayout getFooterLayout() {
		return footerLayout;
	}

	public void setFooterLayout(LinearLayout footerLayout) {
		this.footerLayout = footerLayout;
	}

}
