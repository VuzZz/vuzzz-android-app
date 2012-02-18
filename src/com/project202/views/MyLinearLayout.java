package com.project202.views;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class MyLinearLayout extends LinearLayout {
	public MyLinearLayout(Context context) {
		super(context);
	}

	public abstract String getTitle(Context context);
}
