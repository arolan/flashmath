package com.education.flashmath;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.TextView;

public class FeedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

        if (savedInstanceState == null) {
            FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, new DemoFragment());
            ft.commit();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed, menu);
        TextView tvText = (TextView) findViewById(R.id.tvText);
        EditText etView = (EditText) findViewById(R.id.etView);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        ListView lvTweets = (ListView) findViewById(R.id.lvTweets);


        getFragmentManager();
		return true;
	}

}
