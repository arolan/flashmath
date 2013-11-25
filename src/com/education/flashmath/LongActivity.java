package com.education.flashmath;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.fragment.LongFragment;
import com.education.flashmath.fragment.LongGraphFragment;
import com.education.flashmath.models.Score;
import com.education.flashmath.network.FlashMathClient;
import com.education.flashmath.utils.ConnectivityUtility;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LongActivity extends Activity {
	private String subject;
	private String subjectTitle;
	private Button btnClear;
	private Button btnSwap;
	private TextView tvAttempts;
	private TextView tvBest;
	private TextView tvWorst;
	private TextView tvAverage;
	private LongFragment lf;
	private LongGraphFragment lg;
	private Boolean listOn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long);
		TextView tvSubject = (TextView) findViewById(R.id.tvSubject);
		TextView tvLink = (TextView) findViewById(R.id.tvLink);
	    TextView tvStudy = (TextView) findViewById(R.id.tvStudy);
	    btnClear = (Button) findViewById(R.id.btnClear);
	    tvAttempts = (TextView) findViewById(R.id.tvAttempts);
	    tvBest = (TextView) findViewById(R.id.tvBest);
	    tvWorst = (TextView) findViewById(R.id.tvWorst);
	    tvAverage = (TextView) findViewById(R.id.tvAverage);
	    btnClear.setBackground(getResources().getDrawable(R.drawable.btn_red));
		
		subject = getIntent().getStringExtra("subject");
		ActionBar ab = getActionBar();
		ab.setIcon(getBarIcon());
	    Button btnClose = (Button) findViewById(R.id.btnClose);
	    btnClose.setBackground(getButton());
	    btnSwap = (Button) findViewById(R.id.btnSwap);
	    btnSwap.setBackground(getButton());
		subjectTitle = Character.toUpperCase(subject.charAt(0))+subject.substring(1);
		ab.setTitle(subjectTitle + " Details");
		
		tvSubject.setText("Score History for "+ subjectTitle);
		tvSubject.setBackgroundColor(getColor());
		tvSubject.setTextColor(Color.WHITE);
		tvStudy.setText(subjectTitle);
		
		tvLink.setText(Html.fromHtml("<a style='text-decoration:none' href=http://en.wikipedia.org/wiki/"+subject+"_(mathematics)>"));
		stripUnderlines(tvLink);
		tvLink.setMovementMethod(LinkMovementMethod.getInstance());
		
		//Graph section
		
		FlashMathClient client = FlashMathClient.getClient(this);
		client.getScores(subject, new JsonHttpResponseHandler() {
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onSuccess(JSONArray jsonScores) {
			    
				tvAttempts.setText(jsonScores.length() + " Attempts");
				GraphViewData[] data = new GraphViewData[jsonScores.length()];
				ArrayList<Score> scoreList = new ArrayList<Score>(jsonScores.length());
				
				int max_score = 0;
				int min_score = -1;
				int total = 0;
				for (int i = jsonScores.length() - 1; i >= 0; i--) {
					try {
						int val = jsonScores.getJSONObject(i).getInt("value");
						max_score = val > max_score ? val : max_score;
						if (min_score == -1) {
							min_score = val;
						} else {
							min_score = val > min_score ? min_score : val;
						}
						total += val;
						data[i] = (new GraphViewData(i + 1, val));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						Date date = dateFormat.parse(jsonScores.getJSONObject(i).getString("created"), new ParsePosition(0));
						scoreList.add(new Score(jsonScores.length() - i, val, date));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (min_score == -1) {
					min_score = 0;
				}
				tvBest.setText(String.valueOf(max_score));
				tvBest.setTextColor(getScoreColor((float) max_score / 3));
				tvWorst.setText(String.valueOf(min_score));
				tvWorst.setTextColor(getScoreColor((float) min_score / 3));
				float average = jsonScores.length() == 0 ? 0f : (float) total / jsonScores.length();
				tvAverage.setText(String.format("%.1f", average));
				tvAverage.setTextColor(getScoreColor(average / 3));
				lg = new LongGraphFragment();
				lg.setScores(data);
				lg.setSubject(subject);
				lf = new LongFragment();
				lf.setScoreList(scoreList);
				lf.setSubject(subject);
				getFragmentManager().beginTransaction().add(R.id.frameStats, lf).commit();
				listOn = true;
				findViewById(R.id.pgLoad).setVisibility(View.GONE);
			}
		});
	}


	public int getColor(){
		int color = 0;
		if(subject.equals("addition")){
			color = Color.parseColor("#7979FF");
		} else if(subject.equals("subtraction")){
			color = Color.parseColor("#D79BFA");
		} else if(subject.equals("multiplication")){
			color = Color.parseColor("#66b266");
		} else if(subject.equals("fractions")){
			color = Color.parseColor("#FA96D2");
		} else if(subject.equals("division")){
			color = Color.parseColor("#44B4D5");
		}
		return color;
	}
	
	private Drawable getBarIcon() {
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.ic_action_plus);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.ic_action_minus);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.ic_action_times);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.ic_action_fraction);
		} else {
			return getResources().getDrawable(R.drawable.ic_action_divide);
		}
	}
	
	public Drawable getButton(){
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.btn_blue2);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.btn_purple2);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.btn_green2);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.btn_pink2);
		} else {
			return getResources().getDrawable(R.drawable.btn_yellow2);
		}
	}
	
	public Drawable getListColor(){
		if(subject.equals("addition")){
			return getResources().getDrawable(R.drawable.btn_blue4);
		} else if(subject.equals("subtraction")){
			return getResources().getDrawable(R.drawable.btn_purple4);
		} else if(subject.equals("multiplication")){
			return getResources().getDrawable(R.drawable.btn_green4);
		} else if(subject.equals("fractions")){
			return getResources().getDrawable(R.drawable.btn_pink4);
		} else {
			return getResources().getDrawable(R.drawable.btn_yellow4);
		}
	}
	
	//Close button
	public void onClose(View v){
		finish();
	}

	//Close button
	public void onSwap(View v){
		if (listOn) {
			getFragmentManager().beginTransaction()
				.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
						 R.animator.card_flip_left_in, R.animator.card_flip_left_out)
				.replace(R.id.frameStats, lg)
				.addToBackStack(null)
				.commit();
			listOn = false;
			btnSwap.setText("List");
			btnSwap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list, 0, 0, 0);
		} else {
			getFragmentManager().beginTransaction()
				.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out,
						 R.animator.card_flip_right_in, R.animator.card_flip_right_out)
				.replace(R.id.frameStats, lf)
				.addToBackStack(null)
				.commit();
			listOn = true;
			btnSwap.setText("Graph");
			btnSwap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_line, 0, 0, 0);
		}
	}
	
	//Clear button
	public void onClear(View v){
		tvAttempts.setText("0 Attempts");
		tvBest.setText("0");
		tvBest.setTextColor(getScoreColor(0));
		tvWorst.setText("0");
		tvWorst.setTextColor(getScoreColor(0));
		tvAverage.setText("0.0");
		tvAverage.setTextColor(getScoreColor(0));
		
		lf.clearScores();
		lg.clearScores();
		
		if (ConnectivityUtility.isInternetConnectionAvailable(this)) {
			FlashMathClient client = FlashMathClient.getClient(this);
			
			client.clearScores(subject, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonScores) {
	 
				}
			});
		} else {
			Toast.makeText(this, ConnectivityUtility.INTERNET_CONNECTION_IS_NOT_AVAILABLE, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public int getScoreColor(float pc) {
		if (pc >= .8) {
			return Color.parseColor("#66FF66");
		} else if (pc >= .5) {
			return Color.parseColor("#E5E500");
		} else {
			return Color.parseColor("#FF0033");
		}
	}

	
	private void stripUnderlines(TextView textView) {
        Spannable s = (Spannable) new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }
	
	private class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}
