package com.education.flashmath;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.education.flashmath.models.Question;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ResultActivity extends OAuthLoginActivity<TwitterClient> {

	public static final String SUBJECT_INTENT_KEY = "subject";
	private ArrayList<Question> resultList;
	private int score = 0;
	private String subject;
	private LinearLayout llStats;
	private TextView tvTotal;
	private TextView tvScore;
	private TextView tvSubject;
	private String backgroundColor;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		llStats = (LinearLayout) findViewById(R.id.llStats);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvSubject = (TextView) findViewById(R.id.tvSubject);
		if (savedInstanceState == null) {
			resultList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS_ANSWERED");
			subject = getIntent().getStringExtra(SUBJECT_INTENT_KEY);
			backgroundColor = getIntent().getStringExtra(SubjectActivity.SUBJECT_BACKGROUND_INTENT_KEY);
			evaluate();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	public Drawable getButton(){
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.btn_blue2);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.btn_purple2);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.btn_green2);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.btn_pink2);
		} else {
			return getResources().getDrawable(R.drawable.btn_yellow2);
		}
	}
	
	private Drawable getBarIcon() {
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.ic_action_plus);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.ic_action_minus);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.ic_action_times);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.ic_action_fraction);
		} else {
			return getResources().getDrawable(R.drawable.ic_action_divide);
		}
	}
	
	public void evaluate(){
		//if (resultList != null) {
		ActionBar ab = getActionBar();
		ab.setIcon(getBarIcon());
		for(int i = 0; i < resultList.size(); i++){
			String correctAnswer = resultList.get(i).getCorrectAnswer();
			if (resultList.get(i).getUserAnswer().equals(correctAnswer)){
				score++;
			}
		}
		Button btnTweet = (Button) findViewById(R.id.btnTweet);
		btnTweet.setBackground(getButton());
		Button btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
		btnTryAgain.setBackground(getButton());
		Button btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
		btnMainMenu.setBackground(getButton());
		tvScore.setText(String.valueOf(score));
		tvScore.setTextColor(getScoreColor((float) score / resultList.size()));
		tvTotal.setText("/ " + String.valueOf(resultList.size()));
		String subjectTitle = Character.toUpperCase(subject.charAt(0))+subject.substring(1);
		ab.setTitle(subjectTitle + " Results");
		
		tvSubject.setText(" Score History for " + subjectTitle + " ");
		tvSubject.setBackgroundColor(getColor());
		tvSubject.setTextColor(Color.WHITE);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://flashmathapi.herokuapp.com/scores/" + subject + "/" + String.valueOf(score) + "/",
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonScores) {
				GraphViewData[] data = new GraphViewData[jsonScores.length()];
				int max_score = 1;
				for (int i = 0; i < jsonScores.length(); i++) {
					try {
						int val = jsonScores.getJSONObject(i).getInt("value");
						max_score = val > max_score ? val : max_score;
						data[i] = (new GraphViewData(i + 1, val));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				GraphView graphView = new LineGraphView(ResultActivity.this, "");
				GraphViewStyle style = new GraphViewStyle();
				style.setVerticalLabelsColor(Color.BLACK);
				style.setHorizontalLabelsColor(Color.BLACK);
				style.setGridColor(Color.GRAY);
				style.setNumVerticalLabels(4);
				style.setNumHorizontalLabels(2);
				GraphViewSeriesStyle lineStyle = new GraphViewSeriesStyle(getColor(), 5);
				graphView.addSeries(new GraphViewSeries("Scores", lineStyle, data));
				graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
				graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
				graphView.setGraphViewStyle(style);
				llStats.addView(graphView);
			}
		});
	}
	
	public void tweetScore(View v) {
		if (!getClient().isAuthenticated()) {
			getClient().connect();
		} else {
			tweet();
		}
	}

	//goes back to the first questions
	public void onTryAgain(View v){
		Intent i = new Intent(this, QuestionActivity.class);
		startActivity(i);
	}
	
	private void tweet() {
		getClient().sendTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject object) {
				Toast.makeText(ResultActivity.this, "Sent tweet \"" + getTweet((float) score / resultList.size()) + "\"", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(e, errorResponse);
				e.printStackTrace();
				Toast.makeText(ResultActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
			}
		}, getTweet((float) score / resultList.size()));
	}
	
	public String getTweet(float pc) {
		if (pc >= .8) {
			return "Look Ma! I passed " + subject + " with a " + String.format("%.0f", pc * 100) + "%.";
		} else if (pc >= .5) {
			return "Alas, I am mortal! I barely passed " + subject + " with a " + String.format("%.0f", pc * 100) + "%.";
		} else {
			return "I have brought shame to my family. I failed " + subject + " with a " + String.format("%.0f", pc * 100) + "%.";
		}
	}
	
	public int getColor(){
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
	
	public int getScoreColor(float pc) {
		if (pc >= .8) {
			return Color.parseColor("#66FF66");
		} else if (pc >= .5) {
			return Color.parseColor("#E5E500");
		} else {
			return Color.parseColor("#FF0033");
		}
	}

	@Override
	public void onLoginSuccess() {
	}

	@Override
	public void onLoginFailure(Exception e) {
		Toast.makeText(ResultActivity.this, "Error logging into Twitter!", Toast.LENGTH_SHORT).show();
	}
	
	public void onTakeQuizAgain(View v) {
		Intent i = new Intent(this, QuestionActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(SUBJECT_INTENT_KEY, subject);
		i.putExtra(SubjectActivity.SUBJECT_BACKGROUND_INTENT_KEY, backgroundColor);
		startActivity(i);
	}
	
	public void onMainMenuSelected(View v) {
		Intent i = new Intent(this, SubjectActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	
}
