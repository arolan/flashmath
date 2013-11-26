package com.flashmath.util;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.activeandroid.query.Select;
import com.flashmath.models.OfflineScore;
import com.flashmath.network.FlashMathClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ConnectivityUtil{

	public static final String INTERNET_CONNECTION_IS_NOT_AVAILABLE = "Internet connection is not available";
	
	private static void sendCachedScores(final Context context) {
		ArrayList<OfflineScore> scores = new Select().from(OfflineScore.class).execute();
		if (scores != null && scores.size() != 0) {
			FlashMathClient client = FlashMathClient.getClient(context);
			for (int i = 0; i < scores.size(); i++) {
				final OfflineScore score = scores.get(i);
				client.putScore(score.getSubject(), String.valueOf(score.getScore()), new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonScores) {
						score.delete();
					}
				});
			}
		}
	}

	public static boolean isInternetConnectionAvailable(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		if (isConnected) {
			sendCachedScores(context);
		}
		return isConnected;
	}
}
