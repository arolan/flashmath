package com.flashmath.util;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionUtil {
	public static JSONArray getMockQuestions(String subject) {
		JSONArray questions = new JSONArray();
		for (int i = 0; i < 10; i++) {
			questions.put(generateQuestion(subject));
		}
		
		return questions;
	}
	
	private static JSONObject generateQuestion(String subject) {
		JSONObject question = new JSONObject();
		
		try {
			String questionText = "";
			String answer = "";
			
			if (subject.equalsIgnoreCase("Addition")) {
				int x = getRandomInt(5, 20);
				int y = getRandomInt(5, 20);
				questionText = "('" + String.valueOf(x) + "', '" + String.valueOf(y) + "')";
				answer = String.valueOf(x + y);
			} else if (subject.equalsIgnoreCase("Subtraction")) {
				int x = getRandomInt(5, 20);
				int y = getRandomInt(3, x - 1);
				questionText = "('" + String.valueOf(x) + "', '" + String.valueOf(y) + "')";
				answer = String.valueOf(x - y);
			} else if (subject.equalsIgnoreCase("Multiplication")) {
				int x = getRandomInt(3, 7);
				int y = getRandomInt(3, 7);
				questionText = "('" + String.valueOf(x) + "', '" + String.valueOf(y) + "')";
				int a = x * y;
				answer = String.valueOf(a);
			} else if (subject.equalsIgnoreCase("Division")) {
				int a = getRandomInt(3, 7);
				int y = getRandomInt(3, 7);
				int x = a * y;
				questionText = "('" + String.valueOf(x) + "', '" + String.valueOf(y) + "')";
				answer = String.valueOf(a);
			} else if (subject.equalsIgnoreCase("Fraction")) {
				int m = getRandomInt(3, 5);
				int n = getRandomInt(1, 4);
				int d = getRandomInt(n + 1, 8);
				int ad = d * m;
				questionText = "('" + String.valueOf(n) + "', '" + String.valueOf(d) + "', '" + String.valueOf(ad) + "')";
				answer = String.valueOf(n * m);
			}
			
			question.put("explanation", " ");
			question.put("text", questionText);
			question.put("answer", answer);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question;
	}
	
	private static int getRandomInt(int min, int max) {
	    Random rand = new Random();
	    return rand.nextInt((max - min) + 1) + min;
	}
}
