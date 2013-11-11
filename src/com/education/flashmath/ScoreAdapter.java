package com.education.flashmath;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreAdapter extends ArrayAdapter<Integer>{
	private ImageView ivScore;
	
	public ScoreAdapter(Context context, ArrayList<Integer> scoreList ) {
		super(context,0,scoreList );
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		Integer score = getItem(position);
		
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.score_list_item,null);
		}
				
		TextView tvBigScore = (TextView) view.findViewById(R.id.tvBigScore);
		TextView tvScoreMsg = (TextView) view.findViewById(R.id.tvScoreMsg);
		ivScore = (ImageView) view.findViewById(R.id.ivScore);
		tvBigScore.setText(" "+score);
		tvBigScore.setTextColor(getScoreColor(score));
		tvScoreMsg.setText(getMsg(score));
		getGradeIcon(score);
		return view;
	}

	private String getMsg(int score) {
		String msg = "";
		if (score == 3) {
			msg = "Good Job!";
		} else if (score == 2) {
			msg = "Try Again";
		} else {
			msg = "Let'a Go Study";
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
	
	private void getGradeIcon(int score){
		if (score == 3) {
			ivScore.setImageResource(R.drawable.ic_a);
		} else if (score == 2) {
			ivScore.setImageResource(R.drawable.ic_b);
		} else {
			ivScore.setImageResource(R.drawable.ic_c);
		}
		
	}

}
