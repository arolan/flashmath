package com.flashmath.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.education.flashmath.R;
import com.flashmath.models.Score;

public class ScoreAdapter extends ArrayAdapter<Score>{
	
	public ScoreAdapter(Context context, ArrayList<Score> scoreList ) {
		super(context, 0, scoreList);
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		Integer score = Integer.valueOf(getItem(position).value);
		
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.score_list_item,null);
		}
				
		TextView tvBigScore = (TextView) view.findViewById(R.id.tvBigScore);
		TextView tvScoreMsg = (TextView) view.findViewById(R.id.tvScoreMsg);
		TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
		TextView tvNumber = (TextView) view.findViewById(R.id.tvNumber);
		tvNumber.setText(String.valueOf(getItem(position).att));
		tvBigScore.setText(" "+score);
		tvBigScore.setTextColor(getScoreColor(score));
		tvScoreMsg.setText(getMsg(score));
		tvTime.setText(timeDifference(getItem(position).date));
		return view;
	}

	private String getMsg(int score) { 
		String msg = "";
		if (score == 3) {
			msg = "Good job!";
		} else if (score == 2) {
			msg = "Almost there!";
		} else {
			msg = "Keep trying!";
		}
		return msg;
	}
	
	public int getScoreColor(int score) {
		if (score == 3) {
			return Color.parseColor("#66FF66");
		} else if (score == 2) {
			return Color.parseColor("#E5E500");
		} else {
			return Color.parseColor("#FF0033");
		}
	}
	
	public static String timeDifference(Date date) {
		Date now = new Date();
		TimeZone tz = TimeZone.getTimeZone("US/Pacific");
		long diff = now.getTime() + (5 * 60 * 60 * 1000) + 75000 - date.getTime();
		long val = diff / 1000; 
		if (val < 0) {
			val = 1;
		}
		if (val / 60 == 0) {
			if (val < 0) {
				val = 1;
			}
			if (val == 1)
				return val + " second ago";
			else
				return val + " seconds ago";
		} else {
			val /= 60;
			if (val / 60 == 0) {

				if (val == 1)
					return val + " minute ago";
				else
					return val + " minutes ago";
			} else {
				val /= 60;
				if (val / 24 == 0) {
					if (val == 1)
						return val + " hour ago";
					else
						return val + " hours ago";
				} else {
					val /= 24;

					if (val == 1)
						return val + " day ago";
					else
						return val + " days ago";
				}
			}
		}
	}
}
