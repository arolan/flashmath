package com.education.flashmath.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.flashmath.R;
import com.education.flashmath.models.Question;

public class QuestionAnswerFragment extends Fragment {
	
	public Question question;
	public TextView tvExplanation;
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question q) {
		this.question = q;
	}
	
	public QuestionAnswerFragment() {
    }

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_question_answer, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		verifyUserAnswerForQuestion();
	}
	
	public void verifyUserAnswerForQuestion() {
		tvExplanation = (TextView) getActivity().findViewById(R.id.tvExplanation);
		tvExplanation.setText("");
		String explanationText = null;
		if(question.verifyUserAnswerCorrectness()) {
			explanationText = "<font color=\"green\"><b>Correct! </b></font>";
		} else {
			explanationText = "<font color=\"red\"><b>Wrong! </b></font>";	
		}
		this.tvExplanation.setText(Html.fromHtml(explanationText + question.getExplanation()));
		
	}
}
