package com.education.flashmath.fragment;

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
import com.education.flashmath.models.ArithmeticQuestion;

public class ArithmeticQuestionFragment extends QuestionFragment {
	
	private TextView tvEqualSign;
	private TextView tvOperator;
	private TextView tvOperand1;
	private TextView tvOperand2;

	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_arithmetic_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setupParameters();
	}

	public void setupParameters() {
		tvEqualSign = (TextView) getActivity().findViewById(R.id.tvEqualSign);
		tvOperator = (TextView) getActivity().findViewById(R.id.tvOperator);
		tvOperand1 = (TextView) getActivity().findViewById(R.id.tvOperand1);
		tvOperand2 = (TextView) getActivity().findViewById(R.id.tvOperand2);
		etUserAnswer = (EditText) getActivity().findViewById(R.id.etUserAnswer);
		etUserAnswer.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_VARIATION_NORMAL);
		etUserAnswer.setTextSize(14f);
		
		
		if (backgroundColor != null) {
			setupBackground();
		}
		
		setupArithmeticQuestion();
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
	
	
	public void setupArithmeticQuestion() {
		if(this.question != null) {
			ArithmeticQuestion aq = (ArithmeticQuestion) this.question;
			tvOperand1.setText(""+aq.getOperand1());
			tvOperand2.setText(""+aq.getOperand2());
			tvOperator.setText(""+aq.getOperator());
		}
	}
	
}
