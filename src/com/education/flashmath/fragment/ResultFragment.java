package com.education.flashmath.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.education.flashmath.R;
import com.education.flashmath.ResultActivity;


public class ResultFragment extends android.support.v4.app.Fragment {
	private TextView tvScore;
	private TextView tvRank;
	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ){
		View view = inf.inflate(R.layout.fragment_result, parent, false);	
		tvScore = (TextView) view.findViewById(R.id.tvScore);
		tvRank = (TextView) view.findViewById(R.id.tvRank);
		Button btnAnswer = (Button) view.findViewById(R.id.btnAnswer);
		Button btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		tvScore.setText(((ResultActivity)getActivity()).getScore()+" / "+((ResultActivity)getActivity()).getSize());
		tvRank.setText("1st");
		
	}
}
