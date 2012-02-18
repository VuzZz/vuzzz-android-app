package com.project202.address;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class AddressOverlay extends Overlay {

	private static final int CIRCLE_SIZE = 30;

	private GeoPoint addressLocation;

	private final Point addressPoint = new Point();
	
	private final Paint testPaint = new Paint();
	private final Paint testPaint2 = new Paint();
	private final Paint testPaint3 = new Paint();

	public AddressOverlay() {
		testPaint.setColor(Color.argb(100, 255, 0, 0));
		testPaint2.setAntiAlias(true);
		testPaint2.setColor(Color.rgb(0, 0, 0));
		testPaint2.setStyle(Style.STROKE);
		testPaint3.setAntiAlias(true);
		testPaint3.setColor(Color.rgb(0, 0, 0));
	}

	public void showAddressPopup(GeoPoint location) {
		this.addressLocation = location;

	}

	public void hideAddressPopup() {
		addressLocation = null;
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {

		if (addressLocation == null) {
			return false;
		}

		if (shadow) {
			return false;
		}
		
		Projection projection = mapView.getProjection();
		projection.toPixels(addressLocation, addressPoint);
		
		canvas.drawCircle(addressPoint.x, addressPoint.y, CIRCLE_SIZE, testPaint);
		canvas.drawCircle(addressPoint.x, addressPoint.y, CIRCLE_SIZE, testPaint2);
		canvas.drawCircle(addressPoint.x, addressPoint.y, 4, testPaint3);
		
		return false;
	}

	/**
	 * Return true if popup was clicked
	 */
	public boolean onSingleTapUp(float x, float y) {
		if (addressLocation == null) {
			return false;
		}
		
		// TODO : no tap if address being loaded
		
		float distance = PointF.length(x - addressPoint.x , y - addressPoint.y);
		
		return distance < CIRCLE_SIZE;
	}

	public GeoPoint getAddressLocation() {
		return addressLocation;
	}
	
}
