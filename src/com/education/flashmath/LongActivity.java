package com.education.flashmath;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LongActivity extends Activity {
	LinearLayout llStats;
	String subject;
	String subjectTitle;
	GraphViewData[] data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long);
		TextView tvSubject = (TextView) findViewById(R.id.tvSubject);
		TextView tvLink = (TextView) findViewById(R.id.tvLink);
	    TextView tvStudy = (TextView) findViewById(R.id.tvStudy);
	    llStats = (LinearLayout) findViewById(R.id.llstats);
		
		subject = getIntent().getStringExtra("subject");
		subjectTitle = Character.toUpperCase(subject.charAt(0))+subject.substring(1);
		
		tvSubject.setText(" Score History for "+ subjectTitle + " ");
		tvSubject.setBackgroundColor(getColor());
		tvSubject.setTextColor(Color.WHITE);
		tvStudy.setText("Study "+subjectTitle);
		
		tvLink.setText(Html.fromHtml("<a href=http://en.wikipedia.org/wiki/"+subject+"_(mathematics)> GO TO WIKI "));
		tvLink.setMovementMethod(LinkMovementMethod.getInstance());
		
		//Graph section
				AsyncHttpClient client = new AsyncHttpClient();
				client.get("http://flashmathapi.herokuapp.com/scores/" + subject + "/",
						new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonScores) {
						data = new GraphViewData[jsonScores.length()];
						int max_score = 0;
						for (int i = 0; i < jsonScores.length(); i++) {
							try {
								int val = jsonScores.getJSONObject(i).getInt("value");
								max_score = val > max_score ? val : max_score;
								data[i] = (new GraphViewData(i + 1, val));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						GraphView graphView = new LineGraphView(LongActivity.this,"");
						GraphViewStyle style = new GraphViewStyle();
						style.setVerticalLabelsColor(Color.BLACK);
						style.setHorizontalLabelsColor(Color.BLACK);
						style.setGridColor(Color.GRAY);
						style.setNumVerticalLabels(max_score + 1);
						GraphViewSeriesStyle lineStyle = new GraphViewSeriesStyle(getColor(), 5);
						GraphViewSeries userData = new GraphViewSeries("Score", lineStyle, data);
						graphView.addSeries(userData);
						graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
						graphView.setGraphViewStyle(style);
						llStats.addView(graphView); 
					}
				});
	}


	public int getColor(){
		int color = 0;
		if(subject.equals("addition")){
			color = Color.parseColor("#2C8EB8");
		} else if(subject.equals("subtraction")){
			color = Color.parseColor("#D79BFA");
		} else if(subject.equals("multiplication")){
			color = Color.parseColor("#32D426");
		} else if(subject.equals("fractions")){
			color = Color.parseColor("#FA96D2");
		} else if(subject.equals("division")){
			color = Color.parseColor("#FFF126");
		}
		return color;
	}
	
	//Close button
	public void onClose(View v){
		finish();
	}
	
	//Clear button
	public void onClear(View v){
	
	}
	
}
