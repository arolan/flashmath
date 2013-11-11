package com.education.flashmath.models;

import java.util.Date;

public class Score {
	
	public Score(int att, int value, Date date) {
		this.att = att;
		this.value = value;
		this.date = date;
	}
	
	public int att;
	public int value;
	public Date date;
}
