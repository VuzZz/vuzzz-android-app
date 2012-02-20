package com.vuzzz.android.address;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class GestureOverlay extends Overlay {
	
	private final GestureDetector gestureDetector;

	public GestureOverlay(Context context, OnGestureListener listener) {
		gestureDetector = new GestureDetector(context, listener);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		return gestureDetector.onTouchEvent(event);
	}

}
