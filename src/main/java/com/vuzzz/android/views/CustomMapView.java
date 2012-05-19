package com.vuzzz.android.views;

import static com.vuzzz.android.MapHelper.MAX_ZOOM;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.vuzzz.android.MapHelper;

public class CustomMapView extends MapView {

	public CustomMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getZoomLevel() < MAX_ZOOM) {
				getController().setZoom(MAX_ZOOM);
				getController().setCenter(MapHelper.getRestrictedAreaCenter(getContext()));
			}

			if (!MapHelper.isInRestrictedArea(getContext(), getMapCenter())) {
				final GeoPoint nearestPoint = MapHelper.findTheNearestRestrictedAreaPoint(getContext(), getMapCenter());
				getController().setCenter(nearestPoint);
			}
		}
		return true;
	}

}
