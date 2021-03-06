package com.flashmathdev.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.flashmathdev.R;
import com.flashmathdev.util.ConnectivityUtil;
import com.flashmathdev.util.SoundUtil;

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
				  		i.putExtra(ResultActivity.SUBJECT_INTENT_KEY, tag);
				  		startActivity(i);
				        return true;
				 	} else {
				 		Toast.makeText(SubjectActivity.this, "Internet connection is not available", Toast.LENGTH_LONG).show();
				 		return true;
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
		Intent i = new Intent(this, QuestionActivity.class);
		String tag = v.getTag().toString();
		i.putExtra(ResultActivity.SUBJECT_INTENT_KEY, tag);
		i.putExtra(QuestionActivity.IS_MOCK_QUIZ_INTENT_KEY, !(ConnectivityUtil.isInternetConnectionAvailable(this)));
		startActivity(i);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
	        case R.id.action_settings:
	        	showSettingsPage(item);
	        	break;
	        case R.id.action_about:
	        	showAboutSupportPage(item);
	        	break;
	        case R.id.action_rate:
	        	launchRate(item);
	        	break;
		}
		return true;
	}
	
	public void showAboutSupportPage(MenuItem mi) {
		Intent aboutIntent = new Intent(this, AboutSupportActivity.class);
		startActivity(aboutIntent);
	}

	public void showSettingsPage(MenuItem mi) {
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	public void launchRate(MenuItem mi) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));
		} catch (android.content.ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
		}
	}

}
