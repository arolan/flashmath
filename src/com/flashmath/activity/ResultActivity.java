package com.flashmath.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.education.flashmath.R;
import com.flashmath.models.Question;
import com.flashmath.network.FlashMathClient;
import com.flashmath.network.TwitterClient;
import com.flashmath.util.ColorUtil;
import com.flashmath.util.SoundUtil;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
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
			evaluate();
		}
		
		
	}

	private void playSounds(float pc) {
		if (pc >= .8) {
			SoundUtil.playSound(this, 3);
		} else if (pc >= .5) {
			SoundUtil.playSound(this, 2);
		} else {
			SoundUtil.playSound(this, 1);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	public void evaluate(){
		//if (resultList != null) {
		ActionBar ab = getActionBar();
		ab.setIcon(ColorUtil.getBarIcon(subject, this));
		for(int i = 0; i < resultList.size(); i++){
			String correctAnswer = resultList.get(i).getCorrectAnswer();
			if (resultList.get(i).getUserAnswer().equals(correctAnswer)){
				score++;
			}
		}
		Button btnTweet = (Button) findViewById(R.id.btnTweet);
		btnTweet.setBackground(ColorUtil.getButtonStyle(subject, this));
		Button btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
		btnTryAgain.setBackground(ColorUtil.getButtonStyle(subject, this));
		Button btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
		btnMainMenu.setBackground(ColorUtil.getButtonStyle(subject, this));
		tvScore.setText(String.valueOf(score));
		tvScore.setTextColor(ColorUtil.getScoreColor((float) score / resultList.size()));
		tvTotal.setText("/ " + String.valueOf(resultList.size()));
		String subjectTitle = Character.toUpperCase(subject.charAt(0))+subject.substring(1);
		ab.setTitle(subjectTitle + " Results");
		
		tvSubject.setText(" Score History for " + subjectTitle + " ");
		tvSubject.setBackgroundColor(ColorUtil.subjectColorInt(subject));
		tvSubject.setTextColor(Color.WHITE);
		FlashMathClient client = FlashMathClient.getClient(this);
		client.putScore(subject, String.valueOf(score), new JsonHttpResponseHandler() {
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
				GraphViewSeriesStyle lineStyle = new GraphViewSeriesStyle(ColorUtil.subjectColorInt(subject), 5);
				graphView.addSeries(new GraphViewSeries("Scores", lineStyle, data));
				graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(1, 0) }));
				graphView.addSeries(new GraphViewSeries(new GraphViewData[] { new GraphViewData(2, 3) }));
				graphView.setGraphViewStyle(style);
				llStats.addView(graphView);
			}
		});
		
		playSounds((float) score / resultList.size());
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
		getClient().sendTweetWithImage(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject object) {
				Toast.makeText(ResultActivity.this, "Sent tweet \"" + getTweet((float) score / resultList.size()) + "\"", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				super.onFailure(e, errorResponse);
				e.printStackTrace();
				Toast.makeText(ResultActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
			}
		}, getTweet((float) score / resultList.size()), getScreenShotFile()
		);
	}
	
	
	private InputStream getScreenShotFile() {
		// create bitmap screen capture
		Bitmap bitmap;
		View resultView = findViewById(R.id.rlResult).getRootView();
		resultView.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(resultView.getDrawingCache());
		resultView.setDrawingCacheEnabled(false);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, stream);
		InputStream is = new ByteArrayInputStream(stream.toByteArray());
        
		return is;
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
		startActivity(i);
	}
	
	public void onMainMenuSelected(View v) {
		Intent i = new Intent(this, SubjectActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	
}
