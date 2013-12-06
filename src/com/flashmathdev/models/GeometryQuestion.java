package com.flashmathdev.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeometryQuestion extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924331896021814918L;
	
	protected String shape;
	protected String type;
	protected int operand1;
	protected int operand2 = 0;
	protected int operand3 = 0;
	
	public GeometryQuestion(JSONObject json, String subject) {
		
		super(json);
		String[] operandsArray = this.questionText.split(" ");
		this.shape = operandsArray[0];
		this.type = operandsArray[1];
		this.operand1 = Integer.valueOf(operandsArray[2]);
		if (!this.shape.equals("Square")) {
			this.operand2 = Integer.valueOf(operandsArray[3]);
			if (this.shape.equals("Triangle") && this.type.equals("Perimeter")) {
				this.operand3 = Integer.valueOf(operandsArray[4]);
			}
		}
	}

	public static ArrayList<Question> fromJSONArray(JSONArray jsonArray, String subject) {
		ArrayList<Question> questions = new ArrayList<Question>();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				GeometryQuestion question = new GeometryQuestion(jsonArray.getJSONObject(i), subject);
				question.setQuestionId(i + 1);
				questions.add(question);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return questions;
	}
	
	public String getShape() {
		return shape;
	}
	
	public String getType() {
		return type;
	}

	public int getOperand1() {
		return operand1;
	}
	
	public int getOperand2() {
		return operand2;
	}
	
	public int getOperand3() {
		return operand3;
	}
	
	public void setShape(String shape) {
		this.shape = shape;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setOperand1(int operand1) {
		this.operand1 = operand1;
	}
	
	public void setOperand2(int operand2) {
		this.operand2 = operand2;
	}

	public void setOperand3(int operand3) {
		this.operand3 = operand3;
	}

}
