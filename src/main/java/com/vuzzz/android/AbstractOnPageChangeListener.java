package com.vuzzz.android;

import android.support.v4.view.ViewPager.OnPageChangeListener;

public abstract class AbstractOnPageChangeListener implements OnPageChangeListener {

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public abstract void onPageSelected(int arg0);

}
