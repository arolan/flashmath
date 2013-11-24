package com.education.flashmath.utils;

import org.json.JSONArray;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.education.flashmath.models.OfflineScore;
import com.education.flashmath.network.FlashMathClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ConnectivityUtility extends BroadcastReceiver {

	private static OfflineScore unsentScore;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//debugIntent(intent, "flashmath");
		if (unsentScore != null) {
			FlashMathClient client = FlashMathClient.getClient(context);
			final Context localContext = context;
			if(this.unsentScore != null && ConnectivityUtility.isInternetConnectionAvailable(context)) {
				client.putScore(unsentScore.getSubject(), String.valueOf(unsentScore.getScore()), 
						new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonScores) {
						//score has been sent successfully, so clear the score
						unsentScore = null;
						Toast.makeText(localContext, "Sent offline score successfully!", Toast.LENGTH_LONG).show();
					}
				});
			}
		}
	}

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

	public static boolean  isInternetConnectionAvailable(Context context) {
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
		ConnectivityUtility.unsentScore = unsentScore;
	}

	

}
