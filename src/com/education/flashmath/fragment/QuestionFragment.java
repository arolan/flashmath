package com.education.flashmath.fragment;

import java.util.StringTokenizer;

import si.solarb.flowlayout.FlowLayout;
import si.solarb.flowlayout.FlowLayout.LayoutParams;
import android.app.Fragment;
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
import com.education.flashmath.models.Question;

public class QuestionFragment extends Fragment{

	private static final String UKNOWN_VARIABLE_PLACE_HOLDER = "@_@";
	public Question question;
	private FlowLayout flQuestion;
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
		flQuestion = (FlowLayout) getActivity().findViewById(R.id.flQuestion);
		
		if (backgroundColor != null) {
			setupBackground();
		}
		
//		if (this.question != null) {
//			setupQuestionContent();	
//		}
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


	public void setupQuestionContent() {
		
		if (flQuestion == null) {
			return;
		}
		
//		tvQuestionTitle.setText("Question "+this.question.getQuestionId());
//		tvSectionTitle.setText("Section "+this.question.getSectionId());
		
		String questionText = question.getQuestionText();
		StringTokenizer st = new StringTokenizer(questionText);
		LayoutParams params = flQuestion.new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(20, 0, 20, 0);
		
		while(st.hasMoreTokens()) {
			
			String token = st.nextToken();
			token.trim();
			
			
			if (!token.isEmpty()) {

				if (token.equals(UKNOWN_VARIABLE_PLACE_HOLDER)) {
					EditText et = new EditText(getActivity());
					et.setLayoutParams(params);
					et.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_VARIATION_NORMAL);
					et.setTextSize(14f);
					et.setPadding(et.getPaddingLeft(), -5, et.getPaddingRight(), et.getPaddingBottom());
					etUserAnswer = et;
					flQuestion.addView(et);
				}
				else {
					TextView tvQuestionPart = new TextView(getActivity());
					tvQuestionPart.setLayoutParams(params);
					tvQuestionPart.setText(token);
					flQuestion.addView(tvQuestionPart);
				}
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

//	public void loadNextQuestion() {
//		clearQuestionContent();
//		setupQuestionContent();
//	}
//
//	private void clearQuestionContent() {
//		this.flQuestion.removeAllViews();
//	}
}

