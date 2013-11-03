package com.education.flashmath.fragment;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.education.flashmath.R;
import com.education.flashmath.models.Question;

public class QuestionFragment extends Fragment{

	private static final String UKNOWN_VARIABLE_PLACE_HOLDER = "@_@";
	private static final int NUMBER_OF_ELEMENTS_PER_ROW = 4;
	private Question question;
	private TableLayout tlQuestion;
	private TextView tvSectionTitle;
	private TextView tvQuestionTitle;
	
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
		
		setupQuestionContent();
	}

	private void setupQuestionContent() {
		tlQuestion = (TableLayout) getActivity().findViewById(R.id.tlQuestion);
		
		tvQuestionTitle.setText("Question "+this.question.getQuestionId());
		tvSectionTitle.setText("Section "+this.question.getSectionId());
	
		String questionText = question.getQuestionText();
		StringTokenizer st = new StringTokenizer(questionText);
		
		TableRow tr = new TableRow(getActivity());
		
		int numberOfTokensPerRow = 0;
		
		while(st.hasMoreTokens()) {
			
			if (numberOfTokensPerRow > NUMBER_OF_ELEMENTS_PER_ROW) {
				tr.setGravity(Gravity.CENTER_HORIZONTAL);
				tlQuestion.addView(tr);
				tr = new TableRow(getActivity());
				numberOfTokensPerRow = 0;
			}
			
			String token = st.nextToken();
			token.trim();
			
			if (!token.isEmpty()) {

				if (token.equals(UKNOWN_VARIABLE_PLACE_HOLDER)) {
					EditText et = new EditText(getActivity());

					et.setGravity(View.TEXT_ALIGNMENT_CENTER);
					et.setPadding(0, 0, 10, 0);
					tr.addView(et);
				}
				else {
					TextView tvQuestionPart = new TextView(getActivity());
					tvQuestionPart.setText(token);
					tvQuestionPart.setPadding(0, 0, 10, 0);
					tvQuestionPart.setGravity(View.TEXT_ALIGNMENT_CENTER);
					tr.addView(tvQuestionPart);
				}

				numberOfTokensPerRow++;
			}
		}
		
		if (numberOfTokensPerRow <= NUMBER_OF_ELEMENTS_PER_ROW) {
			tr.setGravity(Gravity.CENTER_HORIZONTAL);
			tlQuestion.addView(tr);
		}
	}

	public void setQuestion(Question q) {
		this.question = q;
	}

	public void clearAnswerFields() {
		int childCount = tlQuestion.getChildCount();
		for (int i = 0; i < childCount; i++) {
			TableRow tr = (TableRow) tlQuestion.getChildAt(i);
			int tableRowElementsCount = tr.getChildCount();
			for (int j = 0; j < tableRowElementsCount; j++) {
				View v = tr.getChildAt(j);
				if (v.getClass() == EditText.class) {
					((EditText) v).setText("");
				}
			}
		}
	}

	public void saveUserAnswersForQuestion() {
		int childCount = tlQuestion.getChildCount();
		for (int i = 0; i < childCount; i++) {
			TableRow tr = (TableRow) tlQuestion.getChildAt(i);
			int tableRowElementsCount = tr.getChildCount();
			for (int j = 0; j < tableRowElementsCount; j++) {
				View v = tr.getChildAt(j);
				if (v.getClass() == EditText.class) {
					this.question.setUserAnswer(((EditText)v).getText().toString());
				}
			}
		}
		
		Toast.makeText(getActivity(), "user answered: "+this.question.getUserAnswer(), Toast.LENGTH_LONG).show();
	}

	public void loadNextQuestion() {
		clearQuestionContent();
		setupQuestionContent();
	}

	private void clearQuestionContent() {
		this.tlQuestion.removeAllViews();
	}
}

