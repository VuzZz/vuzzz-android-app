package com.project202.views;

import android.content.Context;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.project202.R;

@EViewGroup(R.layout.history)
public class HistoryView extends MyLinearLayout {

	public HistoryView(Context context) {
		super(context);
	}

	@Override
	public String getTitle(Context context) {
		return context.getString(R.string.history_title);
	}
}
