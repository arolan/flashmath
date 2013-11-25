package com.education.flashmath.models;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "OfflineScore")
public class OfflineScore extends Model implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3284009289092726429L;
	
	@Column(name = "Subject")
	private String subject;
	
	@Column(name = "Score")
	private int score;
	
	//shows when the score was created to prevent double sending to the server
	@Column(name = "TimeStampInSeconds")
	private int timeStampInSeconds;

	
	public int getTimeStampInSeconds() {
		return timeStampInSeconds;
	}

	public void setTimeStampInSeconds(int timeStampInSeconds) {
		this.timeStampInSeconds = timeStampInSeconds;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
