package com.education.flashmath;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.education.flashmath.fragment.QuestionFragment;
import com.education.flashmath.models.Question;

public class QuestionActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		Question q = new Question();
		q.setCorrectAnswer("1");
		q.setQuestionId(1);
		q.setQuestionText("Find the unknown number x in the following problem @'x' / 5 = 20/100");
		q.setUnknownVariable("x");
		q.setQuiz(null);
		
		QuestionFragment qf = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentForQuestion);
		qf.setQuestion(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

}
