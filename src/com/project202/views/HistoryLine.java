package com.project202.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.project202.model.Rating;
import com.project202.model.Theme;

public class HistoryLine extends View implements OnHistoryFocusedListener {

	private static final int DURATION = 500;

	private static final Interpolator bounceInterpolator = new DecelerateInterpolator();

	private static final float VERTICAL_SPACE = 5f;

	private static final float LEFT_SPACE = 20f;

	private static final float THICKNESS = 15f;

	private Rating rating;

	private float maxRating;

	private boolean pair;

	private static final int[] colors = new int[] { 0xFFFF4444, 0xFFFFBB33, 0xFF99CC00, 0xFFAA66CC, 0xFF33B5E5, 0xFFff8b32 };

	private static final Paint paint = new Paint();

	private static final Path path = new Path();

	private long animationStart;
	
	private boolean hidden = true;
	
	static {
		paint.setTextSize(15);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
	}

	public HistoryLine(Context context) {
		super(context);
		setWillNotDraw(false);
		setMinimumHeight(80);
	}

	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawColor(pair ? 0x11AAAAAA : 0xFFFFFFFF);
		
		if (hidden) {
			return;
		}

		long now = System.currentTimeMillis();
		long deltaTime = now - animationStart;
		int width = getWidth();
		float right_space;
		if (deltaTime >= DURATION) {
			right_space = 20;
		} else {
			float percent = deltaTime / (float) DURATION;
			float interpolation = bounceInterpolator.getInterpolation(percent);
			right_space = width - interpolation * width + 20;
			postInvalidateDelayed(17);
		}

		float screenWith = width - right_space - 20f;
		if (rating != null) {
			float offset = 0f;
			int count = 0;
			int height = getHeight();
			for (Theme theme : rating.getThemes()) {
				float themeWidth = theme.getNote() / maxRating * screenWith;
				paint.setColor(colors[count++]);

				path.reset();
				float left = LEFT_SPACE + offset;
				path.moveTo(left, VERTICAL_SPACE + THICKNESS);
				path.lineTo(left + THICKNESS, VERTICAL_SPACE);
				path.lineTo(left + THICKNESS + themeWidth, VERTICAL_SPACE);
				path.lineTo(left + THICKNESS + themeWidth, height - VERTICAL_SPACE - THICKNESS);
				path.lineTo(left + themeWidth, height - VERTICAL_SPACE);
				path.lineTo(left, height - VERTICAL_SPACE);
				path.close();
				canvas.drawPath(path, paint);
				offset += themeWidth;
			}

			path.reset();
			path.moveTo(LEFT_SPACE + 0, VERTICAL_SPACE + THICKNESS);
			path.lineTo(LEFT_SPACE + THICKNESS, VERTICAL_SPACE);
			path.lineTo(LEFT_SPACE + offset + THICKNESS, VERTICAL_SPACE);
			path.lineTo(LEFT_SPACE + offset, VERTICAL_SPACE + THICKNESS);
			path.lineTo(LEFT_SPACE + 0, VERTICAL_SPACE + THICKNESS);
			path.close();
			paint.setColor(0x33000000);
			canvas.drawPath(path, paint);

			path.reset();
			path.moveTo(LEFT_SPACE + offset + THICKNESS, VERTICAL_SPACE);
			path.lineTo(LEFT_SPACE + offset + THICKNESS, height - VERTICAL_SPACE - THICKNESS);
			path.lineTo(LEFT_SPACE + offset, height - VERTICAL_SPACE);
			path.lineTo(LEFT_SPACE + offset, VERTICAL_SPACE + THICKNESS);
			path.close();
			paint.setColor(0x11000000);
			canvas.drawPath(path, paint);

			paint.setColor(0xFFFFFFFF);
			canvas.drawText("Name", LEFT_SPACE + VERTICAL_SPACE * 2, height / 2f + 10, paint);
		}
	}

	public void setData(Rating rating, float maxRating) {
		this.rating = rating;
		this.maxRating = maxRating;
		invalidate();
	}

	@Override
	public void onHistoryFocused() {
		hidden = false;
		animationStart = System.currentTimeMillis();
		invalidate();
	}

	public void setPair(boolean pair) {
		this.pair = pair;
	}

	@Override
	public void onHistoryHidden() {
		hidden = true;
	}
}
