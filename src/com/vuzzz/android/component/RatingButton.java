package com.vuzzz.android.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DimensionRes;
import com.vuzzz.android.R;
import com.vuzzz.android.model.ThemeName;

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

	@DimensionRes(R.dimen.rating_picto)
	Float pictoWidthDim;
	
	private Float aMark;
	
	private ThemeName theme;

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

	public void setTheme(ThemeName aTheme) {
		this.theme = aTheme;
		themeTextView.setText(theme.getName());
		turnInDefaultState();
	}

	public void setPicto(Drawable drawable) {
		int pictoWidth = pictoWidthDim.intValue();
		drawable.setBounds(0, 0, pictoWidth, pictoWidth);
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			turnInPresseState();
			break;
		case MotionEvent.ACTION_UP:
			turnInDefaultState();
			break;
		case MotionEvent.ACTION_CANCEL:
			turnInDefaultState();
			break;
		
		}
		return super.onTouchEvent(event);
	}
	
	private void turnInPresseState() {
		setBackgroundColor(theme.getPressedDarkColor());
		getFooterLayout().setBackgroundColor(theme.getPressedLightColor());
	}
	
	private void turnInDefaultState() {
		setBackgroundColor(theme.getDarkColor());
		getFooterLayout().setBackgroundColor(theme.getLightColor());
	}

}
