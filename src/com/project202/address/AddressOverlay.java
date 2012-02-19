package com.project202.address;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class AddressOverlay extends Overlay {

	private static final int INNER_SPACE = 30;
	private static final int RADIUS = 7;
	private static final int ICON_SIZE = 0;

	private GeoPoint addressLocation;

	private final Point addressPoint = new Point();
	
	private float fontSize = 16;
	private final Paint textPaint = new Paint();
	private final Paint panelPaint = new Paint();
	private final Paint strokePaint = new Paint();
	private final Paint shadowPaint = new Paint();
	private Path path = new Path();
	
	private Address address = null;
	private RectF panel;

	public AddressOverlay() {
		textPaint.setAntiAlias(true);
		textPaint.setColor(0xFF666666);
		textPaint.setTextSize(fontSize);
		panelPaint.setAntiAlias(true);
		panelPaint.setColor(0xFFFFFFFF);
		panelPaint.setStyle(Style.FILL);
		shadowPaint.setStyle(Style.FILL);
		shadowPaint.setAntiAlias(true);
		shadowPaint.setColor(0x33000000);
		strokePaint.setAntiAlias(true);
		strokePaint.setStrokeWidth(1);
		strokePaint.setColor(0xFF444444);
		strokePaint.setStyle(Style.STROKE);
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

		String firstString = "Veuillez patienter...";
		String secondString = "Téléchargement de la position.";
		
		if (address != null){
			firstString = address.address;
			secondString = address.postalCodeAndCity;
		}
		
		float firstStringSize = textPaint.measureText(firstString);
		float secondStringSize = textPaint.measureText(secondString);
		float baseX = addressPoint.x;
		float baseY = addressPoint.y;
		float width = Math.max(firstStringSize, secondStringSize)+INNER_SPACE*2;
		
		if (address != null){
			width += ICON_SIZE+INNER_SPACE;
			panelPaint.setAlpha(255);
		} else {
			panelPaint.setAlpha(160);
		}
		
		float height = fontSize+INNER_SPACE*2-5;
		float left = baseX - width/2;
		float top = baseY - height - INNER_SPACE;

		// Draw Background
		float shadowOffset = 2;
		RectF shadowPanel = new RectF(left+shadowOffset, top+shadowOffset,//
				left+width+shadowOffset+shadowOffset-1, top+height+shadowOffset+shadowOffset);
		canvas.drawRoundRect(shadowPanel, RADIUS, RADIUS, shadowPaint);
		panel = new RectF(left, top, left+width, top+height);
		canvas.drawRoundRect(panel, RADIUS, RADIUS, panelPaint);
		canvas.drawRoundRect(panel, RADIUS, RADIUS, strokePaint);
		
		// Draw \/
		path.reset();
		path.moveTo(baseX, baseY-INNER_SPACE/2);
		path.lineTo(baseX-INNER_SPACE/2, baseY-INNER_SPACE-2);
		path.lineTo(baseX+INNER_SPACE/2, baseY-INNER_SPACE-2);
		path.close();
		canvas.drawPath(path, panelPaint);
		canvas.drawLine(baseX, baseY-INNER_SPACE/2,baseX-INNER_SPACE/2, baseY-INNER_SPACE, strokePaint);
		canvas.drawLine(baseX, baseY-INNER_SPACE/2,baseX+INNER_SPACE/2, baseY-INNER_SPACE, strokePaint);
		panelPaint.setAlpha(255);
		
		// Draw text
		textPaint.setFakeBoldText(true);
		canvas.drawText(firstString, left+INNER_SPACE, top+INNER_SPACE, textPaint);
		textPaint.setFakeBoldText(false);
		canvas.drawText(secondString, left+INNER_SPACE, top+INNER_SPACE+fontSize+5, textPaint);
		
		return false;
	}

	/**
	 * Return true if popup was clicked
	 */
	public boolean onSingleTapUp(float x, float y) {
		if (addressLocation == null) {
			return false;
		}
//		return address != null && panel.contains(x, y);
		return panel.contains(x, y);
	}

	public GeoPoint getAddressLocation() {
		return addressLocation;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
