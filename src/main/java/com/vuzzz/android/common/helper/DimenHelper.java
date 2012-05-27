package com.vuzzz.android.common.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DimenHelper {

	public static float pixelSize(Context context, float dp) {
		Resources resources = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return px;
	}

}
