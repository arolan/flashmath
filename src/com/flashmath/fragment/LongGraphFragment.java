package com.flashmath.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.education.flashmath.R;
import com.flashmath.util.ColorUtil;
import com.flashmath.util.ResultUtil;
import com.jjoe64.graphview.CustomLabelFormatter;
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
	
	public GraphViewData[] getScores() {
		return this.data;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		llStats = (LinearLayout) getActivity().findViewById(R.id.llStats);

		if (data.length > 1) {
			GraphView graphView = new LineGraphView(getActivity(),"");
			graphView.setCustomLabelFormatter(new CustomLabelFormatter.IntegerOnly());
			GraphViewStyle style = new GraphViewStyle();
			style.setVerticalLabelsColor(Color.BLACK);
			style.setHorizontalLabelsColor(Color.BLACK);
			int datapoints = data.length;
			while (datapoints >= 10) {
				datapoints /= 2;
			}
			style.setNumHorizontalLabels(Math.max(2, datapoints));
			style.setGridColor(Color.GRAY);
			GraphViewSeriesStyle lineStyle = new GraphViewSeriesStyle(ColorUtil.subjectColorInt(subject), 5);
			GraphViewSeries userData = new GraphViewSeries("Score", lineStyle, data);
			graphView.addSeries(userData);
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
			graphView.setGraphViewStyle(style);
			llStats.addView(graphView);
		} else {
			ResultUtil.showAlternativeTextForGraph("You need to take more tests before we can make you a graph!", getActivity(), llStats);
		}
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
			style.setNumHorizontalLabels(2);
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
			graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
			graphView.setGraphViewStyle(style);
			llStats.removeAllViews();
			llStats.addView(graphView);
			llStats.setVisibility(View.VISIBLE);
		}
	}
	
	
}
