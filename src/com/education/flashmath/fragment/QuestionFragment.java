package com.education.flashmath.fragment;

import java.util.ArrayList;
import java.util.Iterator;
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

	private static final int NUMBER_OF_ELEMENTS_PER_ROW = 4;
	private Question question;
	private TableLayout tlQuestion;
	
	public Question getQuestion() {
		return question;
	}

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ) {
		return inf.inflate(R.layout.fragment_question, parent, false);	
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setupQuestionContent();
	}

	private void setupQuestionContent() {
		tlQuestion = (TableLayout) getActivity().findViewById(R.id.tlQuestion);
		
		String questionText = question.getQuestionText();
		StringTokenizer st = new StringTokenizer(questionText);
		ArrayList<String> variables = question.getUnknownVariables();
		
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
				int numberOfVariables = variables.size();
				
				boolean foundMatch = false;
				for (int i = 0; i < numberOfVariables; i++) {
					String variable = variables.get(i);
					if (token.equals("@'"+variable+"'")) {
						EditText et = new EditText(getActivity());
						et.setHint(variable);
						et.setGravity(View.TEXT_ALIGNMENT_CENTER);
						et.setPadding(0, 0, 10, 0);
						tr.addView(et);
						foundMatch = true;
						break;
					}
				}
				
				if(!foundMatch) {
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
		ArrayList<String> answers = new ArrayList<String>();
		int childCount = tlQuestion.getChildCount();
		for (int i = 0; i < childCount; i++) {
			TableRow tr = (TableRow) tlQuestion.getChildAt(i);
			int tableRowElementsCount = tr.getChildCount();
			for (int j = 0; j < tableRowElementsCount; j++) {
				View v = tr.getChildAt(j);
				if (v.getClass() == EditText.class) {
					answers.add(((EditText)v).getText().toString());
				}
			}
		}
		this.question.setUserAnswers(answers);
		Toast.makeText(getActivity(), ""+this.question.getUserAnswers().size(), Toast.LENGTH_LONG).show();
	}

	public void loadNextQuestion() {
		clearQuestionContent();
		setupQuestionContent();
	}

	private void clearQuestionContent() {
		this.tlQuestion.removeAllViews();
	}
}

