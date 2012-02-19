package com.project202.address;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Address;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class SearchOverlay extends Overlay {

	private static final float CUBE_SIZE = 50f;
	private static final float HALF_CUBE_SIZE = CUBE_SIZE / 2;
	private static final float THICKNESS = 10f;

	private final Paint paint = new Paint();
	private final int color = 0xFF33B5E5;

	private static final Point point = new Point();
	private final MapView mapView;

	public SearchOverlay(MapView mapView) {
		this.mapView = mapView;
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
	}

	private List<Address> addresses;
	private List<GeoPoint> geopoints;

	private List<RectF> hitRects;

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		if (addresses == null) {
			return;
		}

		if (shadow) {
			return;
		}

		Projection projection = mapView.getProjection();

		int i = 0;
		for (GeoPoint geopoint : geopoints) {

			RectF rect = hitRects.get(i);

			projection.toPixels(geopoint, point);

			Path path = new Path();

			float top = point.y - HALF_CUBE_SIZE - THICKNESS;
			float left = point.x - HALF_CUBE_SIZE;

			rect.left = left;
			rect.top = top;
			rect.right = left + CUBE_SIZE + THICKNESS;
			rect.bottom = top + CUBE_SIZE;

			path.reset();
			path.moveTo(left, top + THICKNESS);
			path.lineTo(left + THICKNESS, top);
			path.lineTo(left + THICKNESS + CUBE_SIZE, top);
			path.lineTo(left + THICKNESS + CUBE_SIZE, top + CUBE_SIZE);
			path.lineTo(left + CUBE_SIZE, top + CUBE_SIZE + THICKNESS);
			path.lineTo(left, top + CUBE_SIZE + THICKNESS);
			path.close();
			paint.setColor(color);
			canvas.drawPath(path, paint);

			path.reset();
			path.moveTo(left, top + THICKNESS);
			path.lineTo(left + THICKNESS, top);
			path.lineTo(left + THICKNESS + CUBE_SIZE, top);
			path.lineTo(left + CUBE_SIZE, top + THICKNESS);
			path.close();
			paint.setColor(0x33000000);
			canvas.drawPath(path, paint);

			path.reset();
			path.moveTo(left + THICKNESS + CUBE_SIZE, top);
			path.lineTo(left + THICKNESS + CUBE_SIZE, top + CUBE_SIZE);
			path.lineTo(left + CUBE_SIZE, top + CUBE_SIZE + THICKNESS);
			path.lineTo(left + CUBE_SIZE, top + THICKNESS);
			path.close();
			paint.setColor(0x11000000);
			canvas.drawPath(path, paint);

			i++;
		}

	}

	public void setAddresses(List<Address> addresses, List<GeoPoint> geopoints) {
		this.addresses = addresses;
		this.geopoints = geopoints;
		hitRects = new ArrayList<RectF>(addresses.size());
		for (GeoPoint geopoint : geopoints) {
			hitRects.add(new RectF());
		}
	}

	public Address onSingleTapUp(float x, float y) {

		int i = 0;
		for (RectF hitRect : hitRects) {

			if (hitRect.contains(x, y)) {
				return addresses.get(i);
			}

			i++;
		}

		return null;
	}

}
