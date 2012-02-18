package com.project202.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.widget.FrameLayout;

import com.project202.model.Rating;
import com.project202.model.Theme;

public class HistoryLine extends FrameLayout {

	private static final String TAG = HistoryLine.class.getSimpleName();

	private static final float RIGHT_SPACE = 20f;
	private static final float VERTICAL_SPACE = 5f;
	private static final float THICKNESS = 15f;
	
	private Rating rating;

	private float maxRating;

	private static final int[] colors = new int[] {0xFFFF4444, 0xFFFFBB33, 0xFF99CC00, 0xFFAA66CC, 0xFF33B5E5, 0xFF9932ff};

	static final Paint paint = new Paint();
	static {
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
		super.draw(canvas);
		canvas.drawColor(0xFFFFFFFF);
		float screenWith = getWidth()-RIGHT_SPACE;
		if (rating!=null){
			float offset = 0f;
			int count = 0;
			for (Theme theme : rating.getThemes()){
				float width = theme.getNote() / maxRating * screenWith;
				Log.d(TAG, "width "+width+" "+maxRating+" "+screenWith);
				paint.setColor(colors[count++]);
				
				Path path = new Path();
				path.moveTo(offset+0, VERTICAL_SPACE+THICKNESS);
				path.lineTo(offset+THICKNESS, VERTICAL_SPACE);
				path.lineTo(offset+THICKNESS+width, VERTICAL_SPACE);
				path.lineTo(offset+THICKNESS+width, getHeight()-VERTICAL_SPACE-THICKNESS);
				path.lineTo(offset+width, getHeight()-VERTICAL_SPACE);
				path.lineTo(offset, getHeight()-VERTICAL_SPACE);
				path.close();
				canvas.drawPath(path, paint);
				offset += width;
			}
		}
	}

	public void setData(Rating rating, float maxRating) {
		this.rating = rating;
		this.maxRating = maxRating;
		invalidate();
	}
}
