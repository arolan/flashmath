package com.flashmath.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.education.flashmath.R;
import com.flashmath.util.ConnectivityUtil;
import com.flashmath.util.SoundUtil;

public class SubjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		Button button5 = (Button) findViewById(R.id.button5);
		Button button6 = (Button) findViewById(R.id.button6);
		
		OnLongClickListener subjectDetailListener = new OnLongClickListener() {
			 @Override
			    public boolean onLongClick(View v) {
				 	if (ConnectivityUtil.isInternetConnectionAvailable(SubjectActivity.this)) {
				        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
				  		String tag = v.getTag().toString();
				  		i.putExtra("subject", tag);
				  		startActivity(i);
				        return true;
				 	} else {
				 		Toast.makeText(SubjectActivity.this, "Internet connection is not available", Toast.LENGTH_LONG).show();
				 		return false;
				 	}
			    }
		};
	
		button1.setOnLongClickListener(subjectDetailListener);
		button2.setOnLongClickListener(subjectDetailListener);
		button3.setOnLongClickListener(subjectDetailListener);
		button4.setOnLongClickListener(subjectDetailListener);
		button5.setOnLongClickListener(subjectDetailListener);
		button6.setOnLongClickListener(subjectDetailListener);
		
		SoundUtil.prepareSounds(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu); 
		getActionBar().setTitle("Flash Math");
		return true;
	}
	
	public void onButtonClick(View v){
	 	if (ConnectivityUtil.isInternetConnectionAvailable(this)) {
			Intent i = new Intent(this, QuestionActivity.class);
			String tag = v.getTag().toString();
			
			i.putExtra("subject", tag);
			startActivity(i);
	 	} else {
	 		// construct a mock quiz and set the quiz flag to be offline mode
	 	}
	}
}
