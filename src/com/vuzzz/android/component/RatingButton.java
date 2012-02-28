package com.vuzzz.android.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.vuzzz.android.R;

@EViewGroup(R.layout.rating_button)
public class RatingButton extends RelativeLayout {

	@ViewById(R.id.rating_button_rate)
	protected TextView ratingTextView;

	@ViewById(R.id.rating_button_theme)
	protected TextView themeTextView;
	
	@ViewById(R.id.rating_button_max_mark)
	protected TextView maxMarkTextView;

	@ViewById(R.id.rating_button_footer)
	protected LinearLayout footerLayout;
	
	@DrawableRes(R.drawable.details_row_disabled)
	protected Drawable themeDisabled;

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

	public void setDisabled() {
		maxMarkTextView.setVisibility(View.INVISIBLE);
		ratingTextView.setText("N/C");
	}

	public void setTheme(String theme) {
		themeTextView.setText(theme);
	}

	public void setPicto(Drawable drawable) {
		themeTextView.setCompoundDrawables(drawable, null, null, null);
	}
	
	public void setTextSize(float size) {
		ratingTextView.setTextSize(size);
	}

	public LinearLayout getFooterLayout() {
		return footerLayout;
	}

	public void setFooterLayout(LinearLayout footerLayout) {
		this.footerLayout = footerLayout;
	}

}
