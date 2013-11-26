package com.flashmath.util;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.flashmath.models.OfflineScore;
import com.flashmath.network.FlashMathClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ConnectivityUtil extends BroadcastReceiver {

	public static final String INTERNET_CONNECTION_IS_NOT_AVAILABLE = "Internet connection is not available";
	private static OfflineScore unsentScore;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		ArrayList<OfflineScore> scoresToSent = new Select().from(OfflineScore.class).execute();
		
		if (scoresToSent != null && scoresToSent.size() > 0) {
			boolean unsentScoreIsSaved = false;
			if (unsentScore != null) {
				for (int i = 0; i < scoresToSent.size(); i++) {
					OfflineScore os = scoresToSent.get(i);
					if (os != null && os.getTimeStampInSeconds() == unsentScore.getTimeStampInSeconds()) {
						unsentScoreIsSaved = true;
						break;
					}
				}
				
			}
			
			if (!unsentScoreIsSaved && unsentScore != null) {
				scoresToSent.add(unsentScore);
			}
			//clear it since it's either in the db or has been added to the array
			unsentScore = null;
		} else if (unsentScore != null && scoresToSent == null){
			scoresToSent = new ArrayList<OfflineScore>();
			scoresToSent.add(unsentScore);
			unsentScore = null;
		}
		
		
		
		if (scoresToSent != null) {
			FlashMathClient client = FlashMathClient.getClient(context);
			final Context localContext = context;
			if(ConnectivityUtil.isInternetConnectionAvailable(context)) {
				for (int i = 0; i < scoresToSent.size(); i++) {
					final OfflineScore osToSent = scoresToSent.get(i);
					if (osToSent != null) {
						client.putScore(osToSent.getSubject(), String.valueOf(osToSent.getScore()), 
								new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONArray jsonScores) {
								//score has been sent successfully, so clear the score
								osToSent.delete();
								Toast.makeText(localContext, "Sent offline score successfully!", Toast.LENGTH_LONG).show();
							}
						});
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void debugIntent(Intent intent, String tag) {
		Log.v(tag, "action: " + intent.getAction());
		Log.v(tag, "component: " + intent.getComponent());
		Bundle extras = intent.getExtras();
		if (extras != null) {
			for (String key: extras.keySet()) {
				Log.v(tag, "key [" + key + "]: " +
						extras.get(key));
			}
		}
		else {
			Log.v(tag, "no extras");
		}
	}

	public static boolean isInternetConnectionAvailable(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

	public static OfflineScore getUnsentScore() {
		return unsentScore;
	}

	public static void setUnsentScore(OfflineScore unsentScore) {
		ConnectivityUtil.unsentScore = unsentScore;
	}

	

}
