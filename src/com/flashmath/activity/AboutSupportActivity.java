package com.flashmath.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.education.flashmath.R;
import com.flashmath.util.ColorUtil;

public class AboutSupportActivity extends Activity {

	private TextView tvCreditsSectionTitle;
	private TextView tvSupportSectionTitle;
	private TextView tvAboutSectionTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_support);
		
		tvCreditsSectionTitle = (TextView) findViewById(R.id.tvCreditsSectionTitle);
		tvCreditsSectionTitle.setBackgroundColor(Color.parseColor("#44B4D5"));
		tvCreditsSectionTitle.setTextColor(Color.WHITE);
		tvCreditsSectionTitle.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		
		
		tvSupportSectionTitle =  (TextView) findViewById(R.id.tvSupportTitle);
		tvSupportSectionTitle.setBackgroundColor(Color.parseColor("#66b266"));
		tvSupportSectionTitle.setTextColor(Color.WHITE);
		tvSupportSectionTitle.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		
		tvAboutSectionTitle =  (TextView) findViewById(R.id.tvAboutSection);
		tvAboutSectionTitle.setBackgroundColor(Color.parseColor("#7979FF"));
		tvAboutSectionTitle.setTextColor(Color.WHITE);
		tvAboutSectionTitle.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		
		ActionBar ab = getActionBar();
		ab.setTitle("About Flash Math");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_support, menu);
		return true;
	}

}
