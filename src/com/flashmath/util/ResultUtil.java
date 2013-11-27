package com.flashmath.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ResultUtil {

	public static void showAlternativeTextForGraph(String textMessageToShow, Context context, ViewGroup viewWhereToAddMessage) {
		TextView tvNoResult = new TextView(context);
		tvNoResult.setText(textMessageToShow);
		tvNoResult.setLayoutParams(new LayoutParams(
		        LayoutParams.MATCH_PARENT,
		        LayoutParams.WRAP_CONTENT));
		tvNoResult.setTextSize(23);
		tvNoResult.setTextColor(Color.BLACK);
		tvNoResult.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
		viewWhereToAddMessage.addView(tvNoResult);
	}
}
