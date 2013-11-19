package com.education.flashmath.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.education.flashmath.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

public class LongGraphFragment extends Fragment{
	
	private LinearLayout llStats;
	private String subject;
	private GraphViewData[] data;

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.score_graph, parent, false);
	}
	
	public void setScores(GraphViewData[] data) {
		this.data = data;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		llStats = (LinearLayout) getActivity().findViewById(R.id.llStats);
		
		GraphView graphView = new LineGraphView(getActivity(),"");
		GraphViewStyle style = new GraphViewStyle();
		
		style.setVerticalLabelsColor(Color.BLACK);
		style.setHorizontalLabelsColor(Color.BLACK);
		style.setGridColor(Color.GRAY);
		GraphViewSeriesStyle lineStyle = new GraphViewSeriesStyle(getColor(), 5);
		GraphViewSeries userData = new GraphViewSeries("Score", lineStyle, data);
		graphView.addSeries(userData);
		graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
		graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
		graphView.setGraphViewStyle(style);
		llStats.addView(graphView); 
		llStats.setVisibility(View.VISIBLE);
	}
	
	public void clearScores() {
		if (llStats != null) {
			llStats.setVisibility(View.INVISIBLE);
			GraphView graphView = new LineGraphView(getActivity(),"");
			GraphViewStyle style = new GraphViewStyle();
			style.setVerticalLabelsColor(Color.BLACK);
			style.setHorizontalLabelsColor(Color.BLACK);
			style.setGridColor(Color.GRAY);
			style.setNumVerticalLabels(4);
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
			graphView.setGraphViewStyle(style);
			llStats.removeAllViews();
			llStats.addView(graphView);
			llStats.setVisibility(View.VISIBLE);
		}
	}

	public int getColor() {
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
		}
		return color;
	}
	
	public Drawable getListColor(){
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.btn_blue4);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.btn_purple4);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.btn_green4);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.btn_pink4);
		} else {
			return getResources().getDrawable(R.drawable.btn_yellow4);
		}
	}
}
