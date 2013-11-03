package com.education.flashmath.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8573224643054756703L;
	
	private long questionId;
	private long sectionId;
	private Quiz quiz;
	private String questionText;
	private String correctAnswer;
	private String userAnswer;
	
	
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
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
