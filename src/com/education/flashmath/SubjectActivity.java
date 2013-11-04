package com.education.flashmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

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
	
		button1.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
		
		button2.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
		
		button3.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
		
		button4.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
		
		button5.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
		
		button6.setOnLongClickListener(new OnLongClickListener() {

		    @Override
		    public boolean onLongClick(View v) {
		        Intent i = new Intent(SubjectActivity.this, LongActivity.class);
		  		String tag = v.getTag().toString();
		  		i.putExtra("subject", tag);
		  		//Log.d("DEBUG",tag);
		  		startActivity(i);
		        return true;
		    }

		});
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
		//Log.d("DEBUG",tag);
		startActivity(i);
	}

}
