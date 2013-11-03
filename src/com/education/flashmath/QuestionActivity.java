package com.education.flashmath;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.fragment.QuestionFragment;
import com.education.flashmath.models.Question;

public class QuestionActivity extends FragmentActivity {

	public QuestionFragment qf;
	private int currentQuestionIndex;
	private ArrayList<Question> questionList;
	private TextView tvQuestionProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		setupSampleQuestions();
		
		qf = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentForQuestion);
		qf.setQuestion(questionList.get(currentQuestionIndex));
		tvQuestionProgress = (TextView) findViewById(R.id.tvQuestionProgress);
		tvQuestionProgress.setText((currentQuestionIndex+1)+"/"+this.questionList.size());
	}

	private void setupSampleQuestions() {
		
		currentQuestionIndex = 0;
		questionList = new ArrayList<Question>();
		
		Question q = new Question();
		
		q.setCorrectAnswer("1");
		q.setQuestionId(1);
		q.setSectionId(3);
		q.setQuestionText("Find the unknown number in the following problem @_@ / 5 = 20/100");
		
		q.setQuiz(null);
		
		questionList.add(q);
		
		q = new Question();
		
		q.setCorrectAnswer("5");
		q.setQuestionId(2);
		q.setSectionId(3);
		q.setQuestionText("Find the unknown number in the following problem @_@ / 25 = 100/500");
		q.setQuiz(null);
		
		questionList.add(q);
		
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
