package com.flashmath.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.flashmath.R;
import com.flashmath.adapter.ScoreAdapter;
import com.flashmath.models.Score;
import com.flashmath.util.ColorUtil;
import com.flashmath.util.Constants;
import com.flashmath.util.ResultUtil;

public class LongFragment extends Fragment{
	
	private ArrayList<Score> scoreList;
	private ScoreAdapter adapter;
	private ListView lvScores;
	private RelativeLayout rlScoreList;
	private String subject;

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.score_list, parent, false);
	}
	
	public void setScoreList(ArrayList<Score> scoreList) {
		this.scoreList = scoreList;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		lvScores = (ListView) getActivity().findViewById(R.id.lvScores);
		rlScoreList = (RelativeLayout) getActivity().findViewById(R.id.rlScoreList);
		
		if (lvScores != null && scoreList != null && scoreList.size() > 0) {
			adapter = new ScoreAdapter(getActivity(), scoreList);
		    lvScores.setDivider(new ColorDrawable(Color.parseColor("#FFFFFF")));
		    lvScores.setDividerHeight(1);
			lvScores.setAdapter(adapter);
			lvScores.setBackgroundDrawable(ColorUtil.getListStyle(subject, this));
			lvScores.setVisibility(View.VISIBLE);
		} else {
			ResultUtil.showAlternativeTextForGraph(Constants.NO_RESULTS_AVAILABLE, getActivity(), rlScoreList);
		}
	}
	
	public void clearScores() {
		if (adapter != null)
			adapter.clear();
	}
}
