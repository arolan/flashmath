package com.education.flashmath.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8573224643054756703L;
	
	private long questionId;
	private Quiz quiz;
	private String questionText;
	private ArrayList<String> correctAnswers;
	private ArrayList<String> userAnswers;
	private ArrayList<String> unknownVariables;
	
	
	public ArrayList<String> getUnknownVariables() {
		return unknownVariables;
	}
	
	public void setUnknownVariables(ArrayList<String> unknownVariables) {
		this.unknownVariables = unknownVariables;
	}
	
	public ArrayList<String> getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(ArrayList<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public ArrayList<String> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(ArrayList<String> userAnswers) {
		this.userAnswers = userAnswers;
	}
	public long getQuestionId() {
		return questionId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	
	
}
