package com.education.flashmath.models;

public class Question {
	private long questionId;
	private Quiz quiz;
	private String questionText;
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	private String correctAnswer;
	private String unknownVariable;

	public void setUnknownVariable(String variable) {
		this.unknownVariable = variable;
	}
	public String getUnknownVariable() {
		return unknownVariable;
	}
	
	
}
