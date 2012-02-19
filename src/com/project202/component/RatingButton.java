package com.project202.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.vuzzz.android.R;

@EViewGroup(R.layout.rating_button)
public class RatingButton extends RelativeLayout {

	@ViewById(R.id.rating_button_rate)
	protected TextView ratingTextView;

	@ViewById(R.id.rating_button_theme)
	protected TextView themeTextView;

	@ViewById(R.id.rating_button_footer)
	protected LinearLayout footerLayout;

	private Float aMark;

	public RatingButton(Context context) {
		super(context);
	}

	public RatingButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setMark(Float aMark) {
		this.aMark = aMark;
	}

	public void updateMarkDisplay(float percent) {
		if (aMark != null) {
			ratingTextView.setText(String.format("%.1f", aMark * percent));
		}
	}

	public void setTheme(String theme) {
		themeTextView.setText(theme);
	}

	public void setPicto(Drawable drawable) {
		themeTextView.setCompoundDrawables(drawable, null, null, null);
	}

	public LinearLayout getFooterLayout() {
		return footerLayout;
	}

	public void setFooterLayout(LinearLayout footerLayout) {
		this.footerLayout = footerLayout;
	}

}
