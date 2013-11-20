package com.flashmath.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.flashmath.R;
import com.flashmath.models.ArithmeticQuestion;
import com.flashmath.models.FractionQuestion;

public class FractionQuestionFragment extends QuestionFragment {
	
	private TextView tvOperand1;
	private TextView tvOperand2;
	private TextView tvOperand3;
	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_fraction_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setupParameters();
	}

	public void setupParameters() {
		tvOperand1 = (TextView) getActivity().findViewById(R.id.tvFractionOperand1);
		tvOperand2 = (TextView) getActivity().findViewById(R.id.tvFractionOperand2);
		tvOperand3 = (TextView) getActivity().findViewById(R.id.tvFractionOperand3);
		etUserAnswer = (EditText) getActivity().findViewById(R.id.etFractionAnswer);
		etUserAnswer.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_VARIATION_NORMAL);
		
		
		if (backgroundColor != null) {
			setupBackground();
		}
		
		setupFractionQuestion();
	}
	
	public void setupBackground() {
		if (backgroundColor != null) {
			Drawable d = getResources().getDrawable(Integer.valueOf(backgroundColor));
			RelativeLayout rlFragmentAdditionQuestion = (RelativeLayout) getActivity().findViewById(R.id.rlFragmentAdditionQuestion);
			if (rlFragmentAdditionQuestion != null) {
				rlFragmentAdditionQuestion.setBackground(d);
			}
		}
	}
	
	public void setupFractionQuestion() {
		if(this.question != null) {
			FractionQuestion fq = (FractionQuestion) this.question;
			tvOperand1.setText(""+fq.getOperand1());
			tvOperand2.setText(""+fq.getOperand2());
			tvOperand3.setText(""+fq.getOperand3());
		}
	}
	
}