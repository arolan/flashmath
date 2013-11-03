package com.education.flashmath.fragment;

import java.util.StringTokenizer;

import si.solarb.flowlayout.FlowLayout;
import si.solarb.flowlayout.FlowLayout.LayoutParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.R;
import com.education.flashmath.models.Question;

public class QuestionFragment extends Fragment{

	private static final String UKNOWN_VARIABLE_PLACE_HOLDER = "@_@";
	private Question question;
	private FlowLayout flQuestion;
	private TextView tvSectionTitle;
	private TextView tvQuestionTitle;
	private EditText questionField;
	
	public Question getQuestion() {
		return question;
	}

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		tvQuestionTitle = (TextView) getActivity().findViewById(R.id.tvQuestionTitle);
		tvSectionTitle = (TextView) getActivity().findViewById(R.id.tvSectionTitle);
		
		//setupQuestionContent();
	}

	public void setupQuestionContent() {
		flQuestion = (FlowLayout) getActivity().findViewById(R.id.flQuestion);
		
		tvQuestionTitle.setText("Question "+this.question.getQuestionId());
		tvSectionTitle.setText("Section "+this.question.getSectionId());
	
		String questionText = question.getQuestionText();
		StringTokenizer st = new StringTokenizer(questionText);
		LayoutParams params = flQuestion.new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 5, 0);
		
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
					questionField = et;
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
		if (questionField != null) {
			questionField.setText("");
		}
	}

	public void saveUserAnswersForQuestion() {
		if (questionField != null) {
			question.setUserAnswer(questionField.getText().toString());
		}
		
		Toast.makeText(getActivity(), "user answered: "+this.question.getUserAnswer(), Toast.LENGTH_LONG).show();
	}

	public void loadNextQuestion() {
		clearQuestionContent();
		setupQuestionContent();
	}

	private void clearQuestionContent() {
		this.flQuestion.removeAllViews();
	}
}

