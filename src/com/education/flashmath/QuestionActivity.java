package com.education.flashmath;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.fragment.ArithmeticQuestionFragment;
import com.education.flashmath.fragment.FractionQuestionFragment;
import com.education.flashmath.fragment.QuestionAnswerFragment;
import com.education.flashmath.fragment.QuestionFragment;
import com.education.flashmath.models.ArithmeticQuestion;
import com.education.flashmath.models.FractionQuestion;
import com.education.flashmath.models.Question;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class QuestionActivity extends Activity {

	private static final String NEXT_QUESTION_BTN_TITLE = "Next Question";
	private static final String VERIFY_ANSWER_BTN_TITLE = "Verify Answer";
	public static final String QUESTIONS_ANSWERED_INTENT_KEY = "QUESTIONS_ANSWERED";
	public QuestionFragment qf;
	public QuestionAnswerFragment qaf;
	private int currentQuestionIndex;
	private ArrayList<Question> questionList;
	private TextView tvQuestionProgress;
	private Button btnVerifyAndNextQuestion;
	private String subject;
	private String backgroundColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		btnVerifyAndNextQuestion = (Button) findViewById(R.id.btnVerifyAndNextQuestion);
		btnVerifyAndNextQuestion.setVisibility(View.VISIBLE);
		subject = getIntent().getStringExtra("subject");
		backgroundColor = getIntent().getStringExtra(SubjectActivity.SUBJECT_BACKGROUND_INTENT_KEY);
		
		if (subject.equalsIgnoreCase("Fractions")) {
			qf = new FractionQuestionFragment();
		} else {
			qf = new ArithmeticQuestionFragment();
		}
		setupServerQuestions();
		qf.setBackgroundColor(backgroundColor);
		
		
		getFragmentManager().beginTransaction().add(R.id.fragmentForQuestion, qf).commit();
	}
	
	private void setupServerQuestions() {
		currentQuestionIndex = 0;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://flashmathapi.herokuapp.com/quizzes/" + subject + "/", 
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray jsonResults = null;
				try {
					jsonResults = response.getJSONArray("questions");
					
					questionList = new ArrayList<Question>();
					
					if (subject.equalsIgnoreCase("Fractions")) {
						questionList.addAll(FractionQuestion.fromJSONArray(jsonResults, subject));
					} else {
						questionList.addAll(ArithmeticQuestion.fromJSONArray(jsonResults, subject));
					}
					
					Log.d("DEBUG", questionList.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				qf.setQuestion(questionList.get(currentQuestionIndex));
				
				if (subject.equalsIgnoreCase("Fractions")) {
					((FractionQuestionFragment) qf).setupFractionQuestion();
				} else {
					((ArithmeticQuestionFragment) qf).setupArithmeticQuestion();
				}
				
				tvQuestionProgress = (TextView) findViewById(R.id.tvQuestionProgress);
				tvQuestionProgress.setText((currentQuestionIndex + 1) + "/" + questionList.size());

				
				qaf = new QuestionAnswerFragment();
				qaf.setQuestion(questionList.get(currentQuestionIndex));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

	public void onClearQuestionAnswer(View v) {
		qf.clearAnswerFields();
	}
	
	public void onVerifyAndNextQuestionPressed(View v) {
		if (btnVerifyAndNextQuestion.getText().toString().equals(VERIFY_ANSWER_BTN_TITLE)) {
			onVerifyQuestionAnswer(v);
		} else {
			onNextQuestion(v);
		}
	}
	
	public void onVerifyQuestionAnswer(View v) {
		qf.saveUserAnswer();
		
		qaf.setQuestion(questionList.get(currentQuestionIndex));
		
		
		// Create and commit a new fragment transaction that adds the fragment for the back of
		// the card, uses custom animations, and is part of the fragment manager's back stack.
		getFragmentManager().beginTransaction()
		// Replace the default fragment animations with animator resources representing
		// rotations when switching to the back of the card, as well as animator
		// resources representing rotations when flipping back to the front (e.g. when
		// the system Back button is pressed).
		.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
							 R.animator.card_flip_left_in, R.animator.card_flip_left_out)
		// Replace any fragments currently in the container view with a fragment
		// representing the next page (indicated by the just-incremented currentPage
		// variable).
		.replace(R.id.fragmentForQuestion,  qaf)
		// Add this transaction to the back stack, allowing users to press Back
		// to get to the front of the card.
		.addToBackStack(null)
		// Commit the transaction.
		.commit();
		
		//we reached end of questions, remove the Next Question button and 
		//let user use End Quiz button
		if(currentQuestionIndex+1 >= this.questionList.size()) {
			btnVerifyAndNextQuestion.setVisibility(View.GONE);
		} else {
			btnVerifyAndNextQuestion.setText(NEXT_QUESTION_BTN_TITLE);
		}
	}

	public void onNextQuestion(View v) {
		if (currentQuestionIndex < this.questionList.size()) {
			btnVerifyAndNextQuestion.setText(VERIFY_ANSWER_BTN_TITLE);
			//if user forgot to press save button and just presses next question
			qf.saveUserAnswer();

			Question q = qf.getQuestion();
			this.questionList.remove(currentQuestionIndex);
			this.questionList.add(currentQuestionIndex, q);
			currentQuestionIndex++;

			if(currentQuestionIndex < this.questionList.size()) {
	            getFragmentManager().popBackStack();
	            getFragmentManager().beginTransaction().remove(qf).commit();
	            if (this.subject.equalsIgnoreCase("Fractions")) {
	            	qf = new FractionQuestionFragment();
	            } else {
	            	qf = new ArithmeticQuestionFragment();
				}
	            
	            qf.setBackgroundColor(backgroundColor);
	            qf.setQuestion(this.questionList.get(currentQuestionIndex));
	            getFragmentManager().beginTransaction().add(R.id.fragmentForQuestion, qf).commit();
	            tvQuestionProgress.setText((currentQuestionIndex+1)+"/"+this.questionList.size());
			} else {
				finalizeQuiz();
			}
		}
		else {
			finalizeQuiz();
		}
	}

	public void onEndQuiz(View v) {
		qf.saveUserAnswer();
		finalizeQuiz();
	}
	
	private void finalizeQuiz() {
		Intent i = new Intent(this, ResultActivity.class);
		i.putExtra(QUESTIONS_ANSWERED_INTENT_KEY, this.questionList);
		i.putExtra("subject", subject);
		startActivity(i);
		Toast.makeText(this, "End Of Quiz", Toast.LENGTH_LONG).show();
	}

}
