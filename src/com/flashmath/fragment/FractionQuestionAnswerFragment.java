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

import com.flashmath.R;
import com.flashmath.models.FractionQuestion;
import com.flashmath.models.Question;

public class FractionQuestionAnswerFragment extends Fragment {
	
	public Question question;
	private TextView tvOperand1;
	private TextView tvOperand2;
	private TextView tvOperand3;
	private TextView tvAnswer;
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question q) {
		this.question = q;
	}
	
	public FractionQuestionAnswerFragment() {
    }

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_fraction_question_answer, parent, false);	
	}
	
	@Override
	public void onResume() {
		verifyUserAnswerForQuestion();
		super.onResume();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void verifyUserAnswerForQuestion() {
		tvOperand1 = (TextView) getActivity().findViewById(R.id.tvFractionOperand1);
		tvOperand2 = (TextView) getActivity().findViewById(R.id.tvFractionOperand2);
		tvOperand3 = (TextView) getActivity().findViewById(R.id.tvFractionOperand3);
		tvAnswer = (TextView) getActivity().findViewById(R.id.tvAnswer);
		
		if(question != null) {
			FractionQuestion aq = (FractionQuestion) question;
			tvOperand1.setText(""+aq.getOperand1());
			tvOperand2.setText(""+aq.getOperand2());
			tvOperand3.setText(""+aq.getOperand3());
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
