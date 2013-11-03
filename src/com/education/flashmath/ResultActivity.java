package com.education.flashmath;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.education.flashmath.models.Question;

public class ResultActivity extends FragmentActivity {

	private ArrayList<Question> resultList;
	private int score = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		resultList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS_ANSWERED");
		
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
