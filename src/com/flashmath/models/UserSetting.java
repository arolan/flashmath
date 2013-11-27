package com.flashmath.models;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.net.Uri;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "UserSettings")
public class UserSetting extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294202288384935215L;

	@Column(name = "UserProfileImageBitmap")
	private String userProfileImageBitmapURI;
	
	@Column(name = "UserName")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserProfileImageBitmapURI() {
		return userProfileImageBitmapURI;
	}

	public void setUserProfileImageBitmapURI(String userProfileImageBitmapURI) {
		this.userProfileImageBitmapURI = userProfileImageBitmapURI;
	}
	
	
}
