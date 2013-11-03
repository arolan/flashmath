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
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.fragment.QuestionFragment;
import com.education.flashmath.models.Question;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class QuestionActivity extends FragmentActivity {

	public QuestionFragment qf;
	private int currentQuestionIndex;
	private ArrayList<Question> questionList;
	private TextView tvQuestionProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		//setupSampleQuestions();
		setupServerQuestions();
	}
	
	private void setupServerQuestions() {
		currentQuestionIndex = 0;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://flashmathapi.herokuapp.com/quizzes/fractions/", 
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
	
	public void onSaveQuestionAnswer(View v) {
		qf.saveUserAnswersForQuestion();
	}

	public void onNextQuestion(View v) {
		if (currentQuestionIndex < this.questionList.size()) {
			//if user forgot to press save button and just presses next question
			onSaveQuestionAnswer(v);

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
		onSaveQuestionAnswer(v);
		finalizeQuiz();
	}
	private void finalizeQuiz() {
		Intent i = new Intent(this, ResultActivity.class);
		i.putExtra("QUESTIONS_ANSWERED", this.questionList);
		startActivity(i);
		Toast.makeText(this, "End Of Quiz", Toast.LENGTH_LONG).show();
	}

}
