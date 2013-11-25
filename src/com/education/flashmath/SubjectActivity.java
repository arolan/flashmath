package com.education.flashmath;

import java.io.IOException;

import com.education.flashmath.utils.ConnectivityUtility;
import com.education.flashmath.utils.SoundUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class SubjectActivity extends Activity {

	public static final String SUBJECT_BACKGROUND_INTENT_KEY = "subjectBackgroundId";

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
	
		button1.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        return handleSubjectLongClick(v);
		    }

		});
		
		button2.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		    	return handleSubjectLongClick(v);
		    }

		});
		
		button3.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		    	return handleSubjectLongClick(v);
		    }

		});
		
		button4.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		    	return handleSubjectLongClick(v);
		    }

		});
		
		button5.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		    	return handleSubjectLongClick(v);
		    }

		});
		
		button6.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		    	return handleSubjectLongClick(v);
		    }

		});
		
		
		prepareSounds();
		
		prepareConnectionListener();
	}

	private void prepareConnectionListener() {
		//prepare to send out score when we have internet connection again
		this.registerReceiver( new ConnectivityUtility(),
					      new IntentFilter(
					            ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private void prepareSounds() {
		AssetFileDescriptor badSound = null;
		AssetFileDescriptor averageSound = null;
		AssetFileDescriptor excellentSound = null;
		try {
			badSound = getAssets().openFd("fail.mp3");
			averageSound = getAssets().openFd("average.mp3");
			excellentSound = getAssets().openFd("excellent.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SoundUtility.initSounds(this);
		SoundUtility.loadSounds(badSound,averageSound, excellentSound);
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
		
		i.putExtra("subject", tag);

		String backgroundColor = identifySubjectColor(tag);
		i.putExtra(SUBJECT_BACKGROUND_INTENT_KEY, backgroundColor);
		//Log.d("DEBUG",tag);
		startActivity(i);
	}
	
	public void onClassroomClick(View v){
		Intent i = new Intent(this, ClassActivity.class);
		startActivity(i);
	}

	private String identifySubjectColor(String tag) {
		String color = null;
		if (tag.equalsIgnoreCase("Addition")) {
			color = String.valueOf(R.drawable.btn_blue);
		} else if (tag.equalsIgnoreCase("Subtraction")) {
			color = String.valueOf(R.drawable.btn_purple);
		} else if (tag.equalsIgnoreCase("Multiplication")) {
			color = String.valueOf(R.drawable.btn_green);
		} else if (tag.equalsIgnoreCase("Fractions")) {
			color = String.valueOf(R.drawable.btn_pink);
		} else if (tag.equalsIgnoreCase("Division")) {
			color = String.valueOf(R.drawable.btn_yellow);
		} else if (tag.equalsIgnoreCase("Geometry")) {
			color = String.valueOf(R.drawable.btn_orange);
		}
		return color;
	}

	public boolean handleSubjectLongClick(View v) {
		Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		String tag = v.getTag().toString();
		i.putExtra("subject", tag);
		//Log.d("DEBUG",tag);
		startActivity(i);
		return true;
	}
}
