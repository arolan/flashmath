package com.education.flashmath.network;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import android.content.Context;
import android.provider.Settings.Secure;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FlashMathClient {
	private static FlashMathClient flashMathClient;
	private RequestParams auth;
	private AsyncHttpClient client;
	
	private FlashMathClient(Context context) {  // Private constructor
		auth = new RequestParams();
		// TODO Replace this "global" with the device token
		String android_id = Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID); 
		auth.put("token", encryptPassword(android_id));
		client = new AsyncHttpClient();
	}
	
	public static FlashMathClient getClient(Context context) {
		if (flashMathClient == null) {
			flashMathClient = new FlashMathClient(context);
		}
		return flashMathClient;
	}
	
	public void getQuestions(String subject, AsyncHttpResponseHandler handler) {
		String url = "http://flashmathapi.herokuapp.com/quizzes/" + subject + "/";
    	client.get(url, auth, handler);
	}
	
	public void getScores(String subject, AsyncHttpResponseHandler handler) {
		String url = "http://flashmathapi.herokuapp.com/scores/" + subject + "/";
    	client.get(url, auth, handler);
	}

	public void clearScores(String subject, AsyncHttpResponseHandler handler) {
		String url = "http://flashmathapi.herokuapp.com/scores/" + subject + "/clear";
    	client.get(url, auth, handler);
	}
	
	public void putScore(String subject, String score, AsyncHttpResponseHandler handler) {
		String url = "http://flashmathapi.herokuapp.com/scores/" + subject + "/" + score + "/";
    	client.get(url, auth, handler);
	}
	
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
