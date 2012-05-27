package com.vuzzz.android.activity.note;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.TitleProvider;

/**
 * Simple {@link PagerAdapter} implementation that doesn't handle recycle of
 * views nor updating / saving state. To be used when you have a limited number
 * of views that can be pre instantiated.
 */
public class SimplePagerAdapter extends PagerAdapter implements TitleProvider {

	private final List<View> views;
	
	private final List<String> pageTitles;

	public SimplePagerAdapter(List<View> views, List<String> pageTitles) {
		this.views = views;
		this.pageTitles = pageTitles;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public void finishUpdate(View view) {
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		View view = views.get(position);
		((ViewPager) collection).addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View view) {
	}

	@Override
	public String getTitle(int position) {
		return pageTitles.get(position);
	}

}