package com.flashmath.util;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.education.flashmath.R;

public class ColorUtil {
	public static String identifySubjectColor(String subject) {
		String color = null;
		if (subject.equalsIgnoreCase("Addition")) {
			color = String.valueOf(R.drawable.subject_blue);
		} else if (subject.equalsIgnoreCase("Subtraction")) {
			color = String.valueOf(R.drawable.subject_purple);
		} else if (subject.equalsIgnoreCase("Multiplication")) {
			color = String.valueOf(R.drawable.subject_green);
		} else if (subject.equalsIgnoreCase("Fractions")) {
			color = String.valueOf(R.drawable.subject_pink);
		} else if (subject.equalsIgnoreCase("Division")) {
			color = String.valueOf(R.drawable.subject_yellow);
		} else if (subject.equalsIgnoreCase("Geometry")) {
			color = String.valueOf(R.drawable.subject_orange);
		}
		return color;
	}

	public static int subjectColorInt(String subject){
		int color = 0;
		if(subject.equals("addition")){
			color = Color.parseColor("#7979FF");
		} else if(subject.equals("subtraction")){
			color = Color.parseColor("#D79BFA");
		} else if(subject.equals("multiplication")){
			color = Color.parseColor("#66b266");
		} else if(subject.equals("fractions")){
			color = Color.parseColor("#FA96D2");
		} else if(subject.equals("division")){
			color = Color.parseColor("#44B4D5");
		} else if(subject.equals("geometry")){
			color = Color.parseColor("#FABB4E");
		}
		return color;
	}

	public static int getScoreColor(float pc) {
		if (pc >= .8) {
			return Color.parseColor("#66FF66");
		} else if (pc >= .5) {
			return Color.parseColor("#E5E500");
		} else {
			return Color.parseColor("#FF0033");
		}
	}

	public static Drawable getBarIcon(String subject, Context context) {
		if(subject.equals("addition")){
			return context.getResources().getDrawable(R.drawable.ic_action_plus);
		} else if(subject.equals("subtraction")){
			return context.getResources().getDrawable(R.drawable.ic_action_minus);
		} else if(subject.equals("multiplication")){
			return context.getResources().getDrawable(R.drawable.ic_action_times);
		} else if(subject.equals("fractions")){
			return context.getResources().getDrawable(R.drawable.ic_action_fraction);
		} else if(subject.equals("division")){
			return context.getResources().getDrawable(R.drawable.ic_action_divide);
		} else {
			return context.getResources().getDrawable(R.drawable.ic_action_geometry);
		}
	}

	public static Drawable getButtonStyle(String subject, Context context){
		if(subject.equals("addition")){
			return context.getResources().getDrawable(R.drawable.btn_blue);
		} else if(subject.equals("subtraction")){
			return context.getResources().getDrawable(R.drawable.btn_purple);
		} else if(subject.equals("multiplication")){
			return context.getResources().getDrawable(R.drawable.btn_green);
		} else if(subject.equals("fractions")){
			return context.getResources().getDrawable(R.drawable.btn_pink);
		} else if(subject.equals("division")){
			return context.getResources().getDrawable(R.drawable.btn_yellow);
		} else {
			return context.getResources().getDrawable(R.drawable.btn_orange);
		}
	}

	public static Drawable getListStyle(String subject, Fragment context){
		if(subject.equals("addition")){
			return context.getResources().getDrawable(R.drawable.list_blue);
		} else if(subject.equals("subtraction")){
			return context.getResources().getDrawable(R.drawable.list_purple);
		} else if(subject.equals("multiplication")){
			return context.getResources().getDrawable(R.drawable.list_green);
		} else if(subject.equals("fractions")){
			return context.getResources().getDrawable(R.drawable.list_pink);
		} else if(subject.equals("division")){
			return context.getResources().getDrawable(R.drawable.list_yellow);
		} else {
			return context.getResources().getDrawable(R.drawable.list_orange);
		}
	}
}
