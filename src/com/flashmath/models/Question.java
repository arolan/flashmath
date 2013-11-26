package com.flashmath.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.flashmath.util.Constants.SubjectCategory;

public class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8573224643054756703L;
	
	protected long questionId;
	protected SubjectCategory subjectId;
	protected String questionText;
	protected String correctAnswer;
	protected String userAnswer;
	protected String explanation;
	
	public SubjectCategory getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(SubjectCategory subjectId) {
		this.subjectId = subjectId;
	}

	public Question(JSONObject json) {
		this.userAnswer = "";
		try {
			this.questionText = json.getString("text");
			this.correctAnswer = json.getString("answer");
			this.explanation = json.getString("explanation");
		} catch (JSONException e) {
			this.questionText = null;
			this.correctAnswer = null;
			this.explanation = null;
		}
	}
	
	public String getExplanation() {
		return explanation;
	}
	
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public long getQuestionId() {
		return questionId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public boolean verifyUserAnswerCorrectness() {
		return this.correctAnswer.equals(this.userAnswer);
	}

	public static ArrayList<Question> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Question> questions = new ArrayList<Question>();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Question question = new Question(jsonArray.getJSONObject(i));
				question.setQuestionId(i + 1);
				question.setSubjectId(null);
				questions.add(question);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return questions;
	}
}
