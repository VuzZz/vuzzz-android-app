package com.project202.address;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class AddressOverlay extends Overlay {

	private static final int FONT_SIZE = 30;
	private static final int INNER_SPACE = 40;
	private static final int RADIUS = 7;
	private static final int ICON_SIZE = 0;

	private GeoPoint addressLocation;

	private final Point addressPoint = new Point();

	private final Paint textPaint = new Paint();
	private final Paint panelPaint = new Paint();
	private final Paint strokePaint = new Paint();
	private final Paint shadowPaint = new Paint();
	private final Path path = new Path();

	private final RectF rectF = new RectF();
	private final RectF panel = new RectF();

	private Address address = null;

	public AddressOverlay() {
		textPaint.setAntiAlias(true);
		textPaint.setColor(0xFF666666);
		textPaint.setTextSize(FONT_SIZE);
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

		Projection projection = mapView.getProjection();
		projection.toPixels(addressLocation, addressPoint);

		String firstString = "Veuillez patienter...";
		String secondString = "Téléchargement de la position.";

		if (address != null) {
			firstString = address.address;
			secondString = address.postalCodeAndCity;
		}

		float firstStringSize = textPaint.measureText(firstString);
		float secondStringSize = textPaint.measureText(secondString);
		float baseX = addressPoint.x;
		float baseY = addressPoint.y;
		float width = Math.max(firstStringSize, secondStringSize) + INNER_SPACE * 2;

		float height = FONT_SIZE + INNER_SPACE * 2 - 5;
		float left = baseX - width / 2;
		float top = baseY - height - INNER_SPACE;

		if (shadow) {

			// Draw Background
			float shadowOffset = 4;

			rectF.left = left + shadowOffset;
			rectF.top = top + shadowOffset;
			rectF.right = rectF.left + width;
			rectF.bottom = rectF.top + height;
			canvas.drawRoundRect(rectF, RADIUS, RADIUS, shadowPaint);

			path.reset();
			int halfInnerSpace = 0; // INNER_SPACE / 4;
			path.moveTo(baseX + shadowOffset, (baseY - halfInnerSpace) + shadowOffset);
			path.lineTo((baseX - INNER_SPACE / 2) + shadowOffset, (baseY - INNER_SPACE - 2) + shadowOffset);
			path.lineTo((baseX + INNER_SPACE / 2) + shadowOffset, (baseY - INNER_SPACE - 2) + shadowOffset);
			path.close();
			canvas.drawPath(path, shadowPaint);

		} else {

			if (address != null) {
				width += ICON_SIZE + INNER_SPACE;
				panelPaint.setColor(0xFFFFFFFF);
			} else {
				panelPaint.setColor(0xFFEEEEEE);
			}

			rectF.left = left;
			rectF.top = top;
			rectF.right = rectF.left + width;
			rectF.bottom = rectF.top + height;

			canvas.drawRoundRect(rectF, RADIUS, RADIUS, panelPaint);
			canvas.drawRoundRect(rectF, RADIUS, RADIUS, strokePaint);

			panel.set(rectF);

			// Draw \/
			path.reset();
			int halfInnerSpace = 0; // INNER_SPACE / 4;
			path.moveTo(baseX, baseY - halfInnerSpace);
			path.lineTo(baseX - INNER_SPACE / 2, baseY - INNER_SPACE - 2);
			path.lineTo(baseX + INNER_SPACE / 2, baseY - INNER_SPACE - 2);
			path.close();
			canvas.drawPath(path, panelPaint);
			canvas.drawLine(baseX, baseY - halfInnerSpace, baseX - INNER_SPACE / 2, baseY - INNER_SPACE, strokePaint);
			canvas.drawLine(baseX, baseY - halfInnerSpace, baseX + INNER_SPACE / 2, baseY - INNER_SPACE, strokePaint);

			// Draw text
			textPaint.setFakeBoldText(true);
			canvas.drawText(firstString, left + INNER_SPACE, top + INNER_SPACE, textPaint);
			textPaint.setFakeBoldText(false);
			canvas.drawText(secondString, left + INNER_SPACE, top + INNER_SPACE + FONT_SIZE + 10, textPaint);
		}

		return false;
	}

	/**
	 * Return true if popup was clicked
	 */
	public boolean onSingleTapUp(float x, float y) {
		if (addressLocation == null) {
			return false;
		}
		// return address != null && panel.contains(x, y);
		return panel.contains(x, y);
	}

	public GeoPoint getAddressLocation() {
		return addressLocation;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
