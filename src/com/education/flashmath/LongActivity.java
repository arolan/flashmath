package com.education.flashmath;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long);
		TextView tvSubject = (TextView) findViewById(R.id.tvSubject);
		TextView tvLink = (TextView) findViewById(R.id.tvLink);
	    TextView tvStudy = (TextView) findViewById(R.id.tvStudy);
		
		String subject = getIntent().getStringExtra("subject");
		tvSubject.setText("Your Progress of "+subject);
		tvStudy.setText("Study "+subject);
		tvLink.setText(Html.fromHtml("<a href=http://en.wikipedia.org/wiki/"+subject+"_(mathematics)> GO TO WIKI "));
		tvLink.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.long_menu, menu);
        return true;
    }
	
	//Close button
	public void onClose(View v){
		finish();
	}
	
	//Clear button
	public void onClear(View v){
		
	}
	
}
