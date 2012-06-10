package com.vuzzz.android.activity.poimap;

import static com.vuzzz.android.common.helper.DimenHelper.pixelSize;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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

public class POIOverlay extends Overlay {

	private static final String LOGO_LETTER = "V";
	private final float CUBE_SIZE;
	private final float HALF_CUBE_SIZE;
	private final float THICKNESS;
	private final float FONT_SIZE;
	private final float TOP_PADDING;
	private final float CIRCLE_SIZE;

	private final Paint paint = new Paint();
	private final Paint textPaint = new Paint();
	private final int color = 0xFF33B5E5;

	private static final Point point = new Point();

	private List<GeoPoint> geopoints = new ArrayList<GeoPoint>();

	private float logoLetterHalfWidth;
	private float halfTextSize;
	private final GeoPoint referencePoint;
	private final Bitmap picto;
	private final int poiColor;

	public POIOverlay(Context context, GeoPoint referencePoint, Bitmap picto, int color) {

		this.referencePoint = referencePoint;
		this.picto = picto;
		poiColor = color;
		CUBE_SIZE = pixelSize(context, 25f);
		HALF_CUBE_SIZE = CUBE_SIZE / 2;
		THICKNESS = pixelSize(context, 5f);
		FONT_SIZE = pixelSize(context, 15f);
		TOP_PADDING = pixelSize(context, 2f);
		CIRCLE_SIZE = pixelSize(context, 12f);

		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		textPaint.setAntiAlias(true);
		textPaint.setColor(0xFFFFFFFF);
		textPaint.setTextSize(FONT_SIZE);
		textPaint.setFakeBoldText(true);
		logoLetterHalfWidth = textPaint.measureText(LOGO_LETTER) / 2;
		halfTextSize = FONT_SIZE / 2;

	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		if (shadow) {
			return;
		}

		Projection projection = mapView.getProjection();

		paint.setColor(poiColor);
		for (GeoPoint geopoint : geopoints) {
			projection.toPixels(geopoint, point);
			canvas.drawOval(new RectF(point.x - CIRCLE_SIZE, point.y - CIRCLE_SIZE, //
					point.x + CIRCLE_SIZE, point.y + CIRCLE_SIZE), paint);
			canvas.drawBitmap(picto, null, new RectF( //
					point.x-CIRCLE_SIZE/2, point.y-CIRCLE_SIZE/2, //
					point.x+CIRCLE_SIZE/2, point.y+CIRCLE_SIZE/2), paint);
		}

		projection.toPixels(referencePoint, point);

		Path path = new Path();

		float top = point.y - THICKNESS - TOP_PADDING;
		float left = point.x - HALF_CUBE_SIZE;

		// Whole thing
		path.reset();
		path.moveTo(left, top);
		path.lineTo(left + CUBE_SIZE, top);
		path.lineTo(left + CUBE_SIZE + THICKNESS, top + THICKNESS);
		path.lineTo(left + CUBE_SIZE + THICKNESS, top + CUBE_SIZE + THICKNESS);
		path.lineTo(left + THICKNESS, top + CUBE_SIZE + THICKNESS);
		path.lineTo(left, top + CUBE_SIZE);
		path.close();
		paint.setColor(color);
		canvas.drawPath(path, paint);

		//
		path.reset();
		path.moveTo(left, top + CUBE_SIZE);
		path.lineTo(left + CUBE_SIZE, top + CUBE_SIZE);
		path.lineTo(left + CUBE_SIZE + THICKNESS, top + CUBE_SIZE + THICKNESS);
		path.lineTo(left + THICKNESS, top + CUBE_SIZE + THICKNESS);
		path.close();
		paint.setColor(0x33000000);
		canvas.drawPath(path, paint);

		path.reset();
		path.moveTo(left + CUBE_SIZE, top);
		path.lineTo(left + CUBE_SIZE + THICKNESS, top + THICKNESS);
		path.lineTo(left + CUBE_SIZE + THICKNESS, top + CUBE_SIZE + THICKNESS);
		path.lineTo(left + CUBE_SIZE, top + CUBE_SIZE);
		path.close();
		paint.setColor(0x11000000);
		canvas.drawPath(path, paint);

		float middleX = left + HALF_CUBE_SIZE - logoLetterHalfWidth;
		float middleY = top + CUBE_SIZE - halfTextSize;
		canvas.drawText(LOGO_LETTER, middleX, middleY, textPaint);

	}

	public void setAddresses(List<GeoPoint> geopoints) {
		this.geopoints.addAll(geopoints);
	}

}
