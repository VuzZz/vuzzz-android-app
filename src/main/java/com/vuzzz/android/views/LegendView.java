package com.vuzzz.android.views;

import static android.graphics.Paint.Style.STROKE;
import static android.graphics.PixelFormat.TRANSPARENT;
import static com.vuzzz.android.model.ThemeName.CULTURE;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vuzzz.android.DimenHelper;

public class LegendView extends SurfaceView {
	
	private final Paint paintStroke;
	
	private int color = CULTURE.getLightColor();
	
	public LegendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		setZOrderOnTop(true);
		SurfaceHolder holder = getHolder();
		holder.setFormat(TRANSPARENT);
		paintStroke = new Paint();
		paintStroke.setStyle(STROKE);
		paintStroke.setColor(0x33000000);
		paintStroke.setStrokeWidth(DimenHelper.pixelSize(getContext(), 3f));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(color);
		canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paintStroke);
	}

	public void setColor(int lightColor) {
		color = lightColor;
	}
}
