package com.education.flashmath;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class SubjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		return true;
	}
	
	public void onButtonClick(View v){
		Intent i = new Intent(this, QuestionActivity.class);
		String tag = v.getTag().toString();
		i.putExtra("subject", tag);
		Log.d("DEBUG",tag);
		startActivity(i);
	}

}
