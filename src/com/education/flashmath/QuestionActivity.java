package com.education.flashmath;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.fragment.QuestionFragment;
import com.education.flashmath.models.Question;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class QuestionActivity extends FragmentActivity {

	private static final String NEXT_QUESTION_BTN_TITLE = "Next Question";
	private static final String VERIFY_ANSWER_BTN_TITLE = "Verify Answer";
	public static final String QUESTIONS_ANSWERED_INTENT_KEY = "QUESTIONS_ANSWERED";
	public QuestionFragment qf;
	private int currentQuestionIndex;
	private ArrayList<Question> questionList;
	private TextView tvQuestionProgress;
	private Button btnVerifyAndNextQuestion;
	private String subject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		btnVerifyAndNextQuestion = (Button) findViewById(R.id.btnVerifyAndNextQuestion);
		btnVerifyAndNextQuestion.setVisibility(View.VISIBLE);
		subject = getIntent().getStringExtra("subject");
		setupServerQuestions();
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
					questionList.addAll(Question.fromJSONArray(jsonResults));
					Log.d("DEBUG", questionList.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				qf = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentForQuestion);
				qf.setQuestion(questionList.get(currentQuestionIndex));
				tvQuestionProgress = (TextView) findViewById(R.id.tvQuestionProgress);
				tvQuestionProgress.setText((currentQuestionIndex + 1) + "/" + questionList.size());
				qf.setupQuestionContent();
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
		qf.verifyUserAnswerForQuestion();

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
				qf.setQuestion(this.questionList.get(currentQuestionIndex));
				qf.loadNextQuestion();
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
