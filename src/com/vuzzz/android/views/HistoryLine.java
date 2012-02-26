package com.vuzzz.android.views;

import static com.vuzzz.android.DimenHelper.pixelSize;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.vuzzz.android.model.Rating;
import com.vuzzz.android.model.Theme;

public class HistoryLine extends View implements OnHistoryFocusedListener {

	private static final int DURATION = 500;

	private static final Interpolator bounceInterpolator = new DecelerateInterpolator();

	private Rating rating;

	private float maxRating;

	private String note;

	private boolean pair;

	private static final Paint paint = new Paint();

	private static final Paint scorePaint = new Paint();

	private static final Path path = new Path();

	private long animationStart;

	private boolean hidden = false;

	private final float VERTICAL_SPACE;

	private final float HORIZONTAL_SPACE;

	private final float RIGHT_SPACE;

	private final float THICKNESS;

	private final float ADDRESS_SPACE;

	private final float OVERALL_SCORE_X_OFFSET;

	private final float NOTE_TEXT_SHADOW_OFFSET;

	private final float ADDRESS_TEXT_SHADOW_OFFSET;

	public HistoryLine(Context context) {
		super(context);

		VERTICAL_SPACE = pixelSize(context, 12f);
		THICKNESS = pixelSize(context, 15f);
		ADDRESS_SPACE = pixelSize(context, 15f);
		HORIZONTAL_SPACE = pixelSize(context, 15f);
		RIGHT_SPACE = pixelSize(context, 35f);
		OVERALL_SCORE_X_OFFSET = pixelSize(context, 5f);
		NOTE_TEXT_SHADOW_OFFSET = pixelSize(context, 1f);
		ADDRESS_TEXT_SHADOW_OFFSET = pixelSize(context, 0.5f);

		setMinimumHeight((int) (pixelSize(context, 75) + ADDRESS_SPACE));
		setWillNotDraw(false);
		paint.setFakeBoldText(true);
		paint.setTextSize(pixelSize(context, 15));
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);

		scorePaint.setFakeBoldText(true);
		scorePaint.setTextSize(pixelSize(context, 25));
		scorePaint.setStyle(Style.FILL);
		scorePaint.setAntiAlias(true);

		animationStart = System.currentTimeMillis();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(pair ? 0x11AAAAAA : 0x33FFFFFF);
		if (hidden) {
			return;
		}

		if (rating == null) {
			return;
		}

		// Animation
		long now = System.currentTimeMillis();
		long deltaTime = now - animationStart;
		float interpolation = 0;
		if (deltaTime < DURATION) {
			float percent = deltaTime / (float) DURATION;
			interpolation = bounceInterpolator.getInterpolation(percent);
			postInvalidateDelayed(17);
		}

		// Prepare Drawing
		float left = HORIZONTAL_SPACE;
		float top = VERTICAL_SPACE;
		float width = getWidth() - HORIZONTAL_SPACE * 2 - RIGHT_SPACE - THICKNESS;
		float height = getHeight() - VERTICAL_SPACE * 2 - THICKNESS - ADDRESS_SPACE;
		if (deltaTime < DURATION) {
			width = width * interpolation;
		}

		// Drawing
		float offset = 0f;
		for (Theme theme : rating.getThemes()) {
			float themeWidth = 0f;
			if (theme.getNote() == 0) {
				themeWidth = 1f;
			} else {
				themeWidth = theme.getNote() / maxRating * width;
			}

			path.reset();
			float leftBase = left + offset;
			path.moveTo(leftBase, top + THICKNESS);
			path.lineTo(leftBase + THICKNESS, top);
			path.lineTo(leftBase + THICKNESS + themeWidth, top);
			path.lineTo(leftBase + THICKNESS + themeWidth, top + height);
			path.lineTo(leftBase + themeWidth, top + height + THICKNESS);
			path.lineTo(leftBase, top + height + THICKNESS);
			path.close();
			paint.setColor(theme.getColor());
			canvas.drawPath(path, paint);
			offset += themeWidth;
		}

		path.reset();
		path.moveTo(left, top + THICKNESS);
		path.lineTo(left + THICKNESS, top);
		path.lineTo(left + offset + THICKNESS, top);
		path.lineTo(left + offset, top + THICKNESS);
		path.lineTo(left, top + THICKNESS);
		path.close();
		paint.setColor(0x33000000);
		canvas.drawPath(path, paint);

		path.reset();
		path.moveTo(left + offset, top + THICKNESS);
		path.lineTo(left + offset + THICKNESS, top);
		path.lineTo(left + offset + THICKNESS, top + height);
		path.lineTo(left + offset, top + height + THICKNESS);
		path.close();
		paint.setColor(0x11000000);
		canvas.drawPath(path, paint);

		paint.setColor(0x88000000);
		float leftAddress = left + HORIZONTAL_SPACE;
		float topAddress = top + THICKNESS + height + ADDRESS_SPACE;
		canvas.drawText(rating.address, leftAddress + ADDRESS_TEXT_SHADOW_OFFSET, topAddress + ADDRESS_TEXT_SHADOW_OFFSET, paint);
		paint.setColor(0xffa3b7b5);
		canvas.drawText(rating.address, leftAddress, topAddress, paint);

		scorePaint.setColor(0x88000000);
		float leftNote = left + offset + HORIZONTAL_SPACE + OVERALL_SCORE_X_OFFSET;
		float topNote = top + THICKNESS + height / 2;
		canvas.drawText(note, leftNote + NOTE_TEXT_SHADOW_OFFSET, topNote + NOTE_TEXT_SHADOW_OFFSET, scorePaint);
		scorePaint.setColor(0xFFa3b7b5);
		canvas.drawText(note, leftNote, topNote, scorePaint);

	}

	public void setData(Rating rating, float maxRating) {
		this.rating = rating;
		this.maxRating = maxRating;
		this.note = String.format("%.1f", rating.getGlobalMark());
		invalidate();
	}

	public void playAnimation() {
		onHistoryFocused();
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

	public Rating getRating() {
		return rating;
	}

}
