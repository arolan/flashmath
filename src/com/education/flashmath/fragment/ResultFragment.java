package com.education.flashmath.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.education.flashmath.R;

public class ResultFragment extends Fragment{

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ){
		return inf.inflate(R.layout.fragment_result, parent, false);	
		
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		TextView tvScore = (TextView)getActivity().findViewById(R.id.tvScore);
		TextView tvRank = (TextView)getActivity().findViewById(R.id.tvRank);
		Button btnAnswer = (Button)getActivity().findViewById(R.id.btnAnswer);
		Button btnTryAgain = (Button)getActivity().findViewById(R.id.btnTryAgain);
		
	}
	

}

