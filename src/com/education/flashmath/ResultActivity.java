package com.education.flashmath;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
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
		
		}

}
