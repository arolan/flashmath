package com.education.flashmath;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.education.flashmath.models.Question;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ResultActivity extends FragmentActivity {

	private ArrayList<Question> resultList;
	private int score = 0;
	private String subject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		resultList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS_ANSWERED");
		subject = getIntent().getStringExtra("subject");
		evaluate(resultList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	
	public void evaluate(ArrayList<Question> resultList){
		for(int i = 0; i < resultList.size(); i++){
			String correctAnswer = resultList.get(i).getCorrectAnswer();
			if (resultList.get(i).getUserAnswer().equals(correctAnswer)){
				score++;
			}
		}
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://flashmathapi.herokuapp.com/scores/" + subject + "/" + String.valueOf(score) + "/",
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonScores) {
				// Use the results to populate a graphview!
				Toast.makeText(ResultActivity.this, String.valueOf(jsonScores.length()), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public int getScore(){
		return score;
	}
	
	public int getSize(){
		return resultList.size();
	}
	
	//action bar menu icon
	public void onMenu(MenuItem mi){

	}

	//view Answers
	public void onViewAnswers(View v){
		//AnswersActivity
	}

	//goes back to the first questions
	public void onTryAgain(View v){
		Intent i = new Intent(this, QuestionActivity.class);
		startActivity(i);
	}
}
