package com.education.flashmath.fragment;

import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.flashmath.R;
import com.education.flashmath.models.Question;

public class QuestionFragment extends Fragment{

	private Question question;
	private LinearLayout llQuestion;

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		llQuestion = (LinearLayout) getActivity().findViewById(R.id.lvQuestion);
		
		String questionText = question.getQuestionText();
		
		StringTokenizer st = new StringTokenizer(questionText);
		
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			String variable = question.getUnknownVariable();
			TextView tvQuestionPart = new TextView(getActivity());
			if (token != "@'x'") {
				tvQuestionPart.setText(token);
				llQuestion.addView(tvQuestionPart);
			} else {
				EditText et = new EditText(getActivity());
				et.setHint(variable);
				llQuestion.addView(tvQuestionPart);
			}
			
			
		}
	}

	public void setQuestion(Question q) {
		this.question = q;
	}
	

}

