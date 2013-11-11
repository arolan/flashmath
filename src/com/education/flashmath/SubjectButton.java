package com.education.flashmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

public class SubjectButton extends Button {

	public SubjectButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SubjectButton() {
		super(null);
	}

	public SubjectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SubjectButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Bitmap ic = null;
		
		if (getTag() == "fractions") {
			ic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dynamic);
		} else if (getTag() == "geometry") {
			ic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);
		}
		
		if (ic != null) {
	        int width = 50;
	        int height = 50;
	        int margin = 15;
	        int x = canvas.getWidth() - width - margin;
	        int y = canvas.getHeight() - height - margin;
	        canvas.drawBitmap(ic, 0, 0, new Paint());
		}
	}
}
