package com.flashmath.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.education.flashmath.R;
import com.flashmath.models.UserSetting;
import com.flashmath.util.ImageUtility;

public class SettingsActivity extends Activity {

	private ImageView ivProfileImage;
	private UserSetting currentUserSettings;
	private EditText etProfileName;
	private TextView tvProfileImageSectionTitle;
	private TextView tvProfileNameTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		tvProfileImageSectionTitle =  (TextView) findViewById(R.id.tvProfilePictureTitle);
		tvProfileImageSectionTitle.setBackgroundColor(Color.parseColor("#7979FF"));
		tvProfileImageSectionTitle.setTextColor(Color.WHITE);
		tvProfileImageSectionTitle.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		
		tvProfileNameTitle =  (TextView) findViewById(R.id.tvProfileName);
		tvProfileNameTitle.setBackgroundColor(Color.parseColor("#7979FF"));
		tvProfileNameTitle.setTextColor(Color.WHITE);
		tvProfileNameTitle.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);

		
		ActionBar ab = getActionBar();
		ab.setTitle("Settings");
		
		ivProfileImage = (ImageView) findViewById(R.id.ivProfilePicture);
		etProfileName = (EditText) findViewById(R.id.etProfileName);
		
		ArrayList<UserSetting> currentUserSettingsObjects = new Select().from(UserSetting.class).execute();
		if (currentUserSettingsObjects != null && currentUserSettingsObjects.size() > 0) {
			currentUserSettings = currentUserSettingsObjects.get(0);
		} 
		
		if (currentUserSettings == null) {
			currentUserSettings = new UserSetting();
		} else {
			
			//work in progress
			if(currentUserSettings.getUserProfileImageBitmapURI() != null) {
				ivProfileImage.setImageURI(Uri.parse(currentUserSettings.getUserProfileImageBitmapURI()));
			}
			etProfileName.setText(currentUserSettings.getUserName());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	
	public void saveName(View v) {
		currentUserSettings.setUserName(etProfileName.getText().toString());
		currentUserSettings.save();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etProfileName.getWindowToken(), 0);
	}
	
	public void changePicture(View v) {
		AlertDialog diaBox = AskChangePictureOption();
		diaBox.show();
	}
	
	private AlertDialog AskChangePictureOption() {
	    AlertDialog pictureSourceDialog = new AlertDialog.Builder(this)
	        //set message, title, and icon
	        .setTitle("Picture Source") 
	        .setMessage("How would you like to change your picture?") 
	        .setPositiveButton("Using Camera", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	        		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        		startActivityForResult(takePicture, 0);
	                dialog.dismiss();
	            }
	        })
	        .setNeutralButton("From Gallery", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	            	startActivityForResult(pickPhoto , 1);
	                dialog.dismiss();
	            }
	        })
	        .setCancelable(true)
	        .create();
	        return pictureSourceDialog;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
		case 0:
			if(resultCode == RESULT_OK){  
				Bundle extras = imageReturnedIntent.getExtras();
				Bitmap bitmap = (Bitmap) extras.get("data");
				ivProfileImage.setImageBitmap(bitmap);
				currentUserSettings.setUserProfileImageBitmapURI(ImageUtility.getRealPathFromURI(ImageUtility.getImageUri(this, bitmap), this));
				currentUserSettings.save();
			}

			break; 
		case 1:
			if(resultCode == RESULT_OK){  
				Uri selectedImage = imageReturnedIntent.getData();
				ivProfileImage.setImageURI(selectedImage);
				currentUserSettings.setUserProfileImageBitmapURI(ImageUtility.getRealPathFromURI(selectedImage, this));
				currentUserSettings.save();
			}
			break;
		}
	}

}
