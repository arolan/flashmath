package com.flashmath.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.flashmath.R;
import com.flashmath.models.Question;

public class QuestionFragment extends Fragment{

	public Question question;
	protected EditText etUserAnswer;
	public TextView tvExplanation;
	protected String backgroundColor;
	
	public Question getQuestion() {
		return question;
	}

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		tvExplanation = (TextView) getActivity().findViewById(R.id.tvExplanation);
		
		if (backgroundColor != null) {
			setupBackground();
		}
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setupBackground() {
		if (backgroundColor != null) {
			Drawable d = getResources().getDrawable(Integer.valueOf(backgroundColor));
			RelativeLayout rlFragmentQuestion = (RelativeLayout) getActivity().findViewById(R.id.rlFragmentQuestion);
			if (rlFragmentQuestion != null) {
				rlFragmentQuestion.setBackground(d);
			}
		}
	}


	public void setQuestion(Question q) {
		this.question = q;
	}

	public void clearAnswerFields() {
		if (etUserAnswer != null) {
			etUserAnswer.setText("");
		}
	}


	public void saveUserAnswer() {
		if (etUserAnswer != null) {
			question.setUserAnswer(etUserAnswer.getText().toString());
			this.etUserAnswer.setEnabled(false);
		}
	}

}

