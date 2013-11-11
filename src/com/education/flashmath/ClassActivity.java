package com.education.flashmath;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ClassActivity extends Activity {

	public static final String SUBJECT_BACKGROUND_INTENT_KEY = "subjectBackgroundId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
		ActionBar ab = getActionBar();
		ab.setIcon(getResources().getDrawable(R.drawable.ic_einstein));
		ab.setTitle("Mr. Einstein's Class");
		return true;
	}
}