package com.flashmath.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.flashmath.R;
import com.flashmath.models.ArithmeticQuestion;
import com.flashmath.models.Question;

public class ArithmeticQuestionAnswerFragment extends Fragment {
	
	public Question question;
	private TextView tvOperator;
	private TextView tvOperand1;
	private TextView tvOperand2;
	private TextView tvAnswer;
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question q) {
		this.question = q;
	}
	
	public ArithmeticQuestionAnswerFragment() {
    }

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_arithmetic_question_answer, parent, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		verifyUserAnswerForQuestion();
		super.onResume();
	}
	
	public void verifyUserAnswerForQuestion() {
		tvOperator = (TextView) getActivity().findViewById(R.id.tvOperator);
		tvOperand1 = (TextView) getActivity().findViewById(R.id.tvOperand1);
		tvOperand2 = (TextView) getActivity().findViewById(R.id.tvOperand2);
		tvAnswer = (TextView) getActivity().findViewById(R.id.tvAnswer);
		if(question != null) {
			ArithmeticQuestion aq = (ArithmeticQuestion) question;
			tvOperand1.setText(""+aq.getOperand1());
			tvOperand2.setText(""+aq.getOperand2());
			tvOperator.setText(""+aq.getOperator());
			if(aq.verifyUserAnswerCorrectness()) {
				tvAnswer.setTextColor(Color.parseColor("#66FF66"));
				Drawable d = getResources().getDrawable(R.drawable.btn_correct);
				tvAnswer.setText(""+aq.getUserAnswer());
				RelativeLayout rlFragmentAdditionQuestion = (RelativeLayout) getActivity().findViewById(R.id.rlFragmentAdditionQuestion);
				if (rlFragmentAdditionQuestion != null) {
					rlFragmentAdditionQuestion.setBackground(d);
				}
			} else {
				tvAnswer.setTextColor(Color.parseColor("#FF0033"));
				Drawable d = getResources().getDrawable(R.drawable.btn_incorrect);
				tvAnswer.setText(""+aq.getCorrectAnswer());
				RelativeLayout rlFragmentAdditionQuestion = (RelativeLayout) getActivity().findViewById(R.id.rlFragmentAdditionQuestion);
				if (rlFragmentAdditionQuestion != null) {
					rlFragmentAdditionQuestion.setBackground(d);
				}
			}
		}
	}
}
