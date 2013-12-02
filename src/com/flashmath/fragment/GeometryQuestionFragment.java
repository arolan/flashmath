package com.flashmath.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flashmath.R;
import com.flashmath.models.GeometryQuestion;

public class GeometryQuestionFragment extends QuestionFragment {
	private RelativeLayout rl;
	private TextView tvType;
	private TextView tvShape;
	private TextView tvOperand1;
	private TextView tvOperand2;
	private TextView tvOperand3;
	private TextView tvRectOperand1;
	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_geometry_question, parent, false);	
	}
	
	@Override
	public void onResume() {
		setupParameters();
		super.onResume();
	}

	public void onActivityCreated(Bundle savedInstanceState){ 
		super.onActivityCreated(savedInstanceState);
	}

	public void setupParameters() {
		tvType = (TextView) getActivity().findViewById(R.id.tvType);
		tvShape = (TextView) getActivity().findViewById(R.id.tvShape);
		tvOperand1 = (TextView) getActivity().findViewById(R.id.tvOperand1);
		tvOperand2 = (TextView) getActivity().findViewById(R.id.tvOperand2);
		tvOperand3 = (TextView) getActivity().findViewById(R.id.tvOperand3);
		tvRectOperand1 = (TextView) getActivity().findViewById(R.id.tvRectOperand1);
		etUserAnswer = (EditText) getActivity().findViewById(R.id.etUserAnswer);
		etUserAnswer.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_VARIATION_NORMAL);
		
		if (backgroundColor != null) {
			setupBackground();
		}
		
		setupGeometryQuestion();
	}
	
	public void setupBackground() {
		if (backgroundColor != null) {
			Drawable d = getResources().getDrawable(Integer.valueOf(backgroundColor));
			rl = (RelativeLayout) getActivity().findViewById(R.id.rlFragmentAdditionQuestion);
			if (rl != null) {
				rl.setBackground(d);
			}
		}
	}
	
	
	public void setupGeometryQuestion() {
		if(this.question != null) {
			GeometryQuestion gq = (GeometryQuestion) this.question;
			String shape = gq.getShape();
			String type = gq.getType();
			tvType.setText(type);
			tvShape.setText(shape);
			final Paint paint = new Paint();
			final Path hypotheneuse = new Path();
			final Path topCorner = new Path();
			final Path rightCorner = new Path();
			paint.setColor(Color.WHITE);
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setStrokeWidth(10f);
			paint.setStyle(Style.FILL);
			if (shape.equals("Square")) {
				tvOperand1.setText(String.valueOf(gq.getOperand1()));
				tvOperand2.setText(String.valueOf(gq.getOperand1()));
				View vSquare = new View(getActivity()) {
					@Override
					protected void onDraw(Canvas canvas) {
						int w = canvas.getWidth();
						canvas.drawLine(100, 85, w - 100, 85, paint);
						canvas.drawLine(100, w - 125, w - 100, w - 125, paint);
						canvas.drawLine(105, 80, 105, w - 120, paint);
						canvas.drawLine(w - 105, 80, w - 105, w - 120, paint);
					}
				};
				rl.addView(vSquare);
			} else if (shape.equals("Rectangle")) {
				tvRectOperand1.setText(String.valueOf(gq.getOperand1()));
				tvOperand2.setText(String.valueOf(gq.getOperand2()));
				View vSquare = new View(getActivity()) {
					@Override
					protected void onDraw(Canvas canvas) {
						int w = canvas.getWidth();
						canvas.drawLine(100, 85, w - 100, 85, paint);
						canvas.drawLine(100, w - 75, w - 100, w - 75, paint);
						canvas.drawLine(105, 80, 105, w - 80, paint);
						canvas.drawLine(w - 105, 80, w - 105, w - 80, paint);
					}
				};
				rl.addView(vSquare);
			} else if (shape.equals("Triangle")) {
				tvRectOperand1.setText(String.valueOf(gq.getOperand1()));
				tvOperand2.setText(String.valueOf(gq.getOperand2()));
				if (type.equals("Perimeter")) {
					tvOperand3.setText(String.valueOf(gq.getOperand3()));
				}
				View vSquare = new View(getActivity()) {
					@Override
					protected void onDraw(Canvas canvas) {
						int w = canvas.getWidth();
						canvas.drawLine(100, w - 75, w - 100, w - 75, paint);
						canvas.drawLine(105, 80, 105, w - 80, paint);
						paint.setStrokeWidth(1f);
						hypotheneuse.moveTo(110, 80);
						hypotheneuse.lineTo(w - 100, w - 80);
						hypotheneuse.lineTo(w - 115, w - 80);
						hypotheneuse.lineTo(110, 95);
						hypotheneuse.lineTo(110, 80);
						topCorner.moveTo(110, 80);
						topCorner.lineTo(100, 80);
						topCorner.lineTo(100, 67);
						topCorner.lineTo(110, 80);
						rightCorner.moveTo(w - 100, w - 80);
						rightCorner.lineTo(w - 100, w - 70);
						rightCorner.lineTo(w - 92, w - 70);
						rightCorner.lineTo(w - 100, w - 80);
						canvas.drawPath(hypotheneuse, paint);
						canvas.drawPath(topCorner, paint);
						canvas.drawPath(rightCorner, paint);
					}
				};
				rl.addView(vSquare);
			}
		}
	}
	
}
