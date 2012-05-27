package com.vuzzz.android.activity.note.history;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.vuzzz.android.R;
import com.vuzzz.android.common.model.ThemeName;

@EViewGroup(R.layout.legend_item)
public class LegendItem extends LinearLayout {

	@ViewById
	protected TextView legendText;
	
	@ViewById
	protected LegendView legendView;
	
	public LegendItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setTheme(ThemeName theme){
		legendText.setText(theme.getName());
		legendView.setColor(theme.getLightColor());
	}

}
