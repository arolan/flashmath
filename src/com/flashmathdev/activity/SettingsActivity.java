package com.flashmathdev.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.flashmathdev.R;
import com.flashmathdev.models.UserSetting;
import com.flashmathdev.util.ImageUtility;

public class SettingsActivity extends Activity {

	private ImageView ivProfileImage;
	private UserSetting currentUserSettings;
	private EditText etProfileName;
	private ImageView ivChangeName;
	private String originalName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		ActionBar ab = getActionBar();
		ab.setTitle("Settings");
		originalName = "";
		
		ivProfileImage = (ImageView) findViewById(R.id.ivProfilePicture);
		etProfileName = (EditText) findViewById(R.id.etProfileName);
		ivChangeName = (ImageView) findViewById(R.id.ivChangeName);
		
		
		
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
			originalName = currentUserSettings.getUserName();
			etProfileName.setText(originalName);
		}
		
		etProfileName.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etProfileName.getText().toString().equals(originalName)) {
					ivChangeName.setBackgroundDrawable(
							SettingsActivity.this.getResources().getDrawable(R.drawable.ic_action_change_name));
				} else {
					ivChangeName.setBackgroundDrawable(
							SettingsActivity.this.getResources().getDrawable(R.drawable.ic_action_change_name_dirty));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	
	public void saveName(View v) {
		originalName = etProfileName.getText().toString();
		currentUserSettings.setUserName(originalName);
		currentUserSettings.save();
		ivChangeName.setBackgroundDrawable(
				this.getResources().getDrawable(R.drawable.ic_action_change_name));
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
	        .setMessage("Where is your picture?") 
	        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	        		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        		startActivityForResult(takePicture, 0);
	                dialog.dismiss();
	            }
	        })
	        .setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
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
