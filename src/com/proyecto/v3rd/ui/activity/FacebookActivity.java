package com.proyecto.v3rd.ui.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public abstract class FacebookActivity extends V3rdActivity {
    public static final String TAG = "FACEBOOK";
    private Facebook mFacebook;
    public static final String APP_ID = "458802534140209";
    private AsyncFacebookRunner mAsyncRunner;
    private static final String[] PERMS = new String[] { "publish_stream" };
    private SharedPreferences sharedPrefs;
    private Context mContext;
 
 
    public void setConnection() {
            mContext = this;
            mFacebook = new Facebook(APP_ID);
            mAsyncRunner = new AsyncFacebookRunner(mFacebook);
    }
 
    public void getID() {
            if (isSession()) {
                    Log.d(TAG, "sessionValid");
                    mAsyncRunner.request("me", new IDRequestListener());
            } else {
                    // no logged in, so relogin
                    Log.d(TAG, "sessionNOTValid, relogin");
                    mFacebook.authorize(this, PERMS, new LoginDialogListener());
            }
    }
 
    public boolean isSession() {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            String access_token = sharedPrefs.getString("access_token", "x");
            Long expires = sharedPrefs.getLong("access_expires", -1);
            Log.d(TAG, access_token);
 
            if (access_token != null && expires != -1) {
                    mFacebook.setAccessToken(access_token);
                    mFacebook.setAccessExpires(expires);
            }
            return mFacebook.isSessionValid();
    }
 
    private class LoginDialogListener implements DialogListener {
 
            @Override
            public void onComplete(Bundle values) {
                    Log.d(TAG, "LoginONComplete");
                    String token = mFacebook.getAccessToken();
                    long token_expires = mFacebook.getAccessExpires();
                    Log.d(TAG, "AccessToken: " + token);
                    Log.d(TAG, "AccessExpires: " + token_expires);
                    sharedPrefs = PreferenceManager
                                    .getDefaultSharedPreferences(mContext);
                    sharedPrefs.edit().putLong("access_expires", token_expires)
                                    .commit();
                    sharedPrefs.edit().putString("access_token", token).commit();
                    mAsyncRunner.request("me", new IDRequestListener());
            }
 
            @Override
            public void onFacebookError(FacebookError e) {
                    Log.d(TAG, "FacebookError: " + e.getMessage());
            }
 
            @Override
            public void onError(DialogError e) {
                    Log.d(TAG, "Error: " + e.getMessage());
            }
 
            @Override
            public void onCancel() {
                    Log.d(TAG, "OnCancel");
            }
    }
 
    private class IDRequestListener implements RequestListener {
 
            @Override
            public void onComplete(String response, Object state) {
                    try {
                            Log.d(TAG, "IDRequestONComplete");
                            Log.d(TAG, "Response: " + response.toString());
                            JSONObject json = Util.parseJson(response);
 
                            final String id = json.getString("id");
                            final String name = json.getString("name");
                    } catch (JSONException e) {
                            Log.d(TAG, "JSONException: " + e.getMessage());
                    } catch (FacebookError e) {
                            Log.d(TAG, "FacebookError: " + e.getMessage());
                    }
            }
 
            @Override
            public void onIOException(IOException e, Object state) {
                    Log.d(TAG, "IOException: " + e.getMessage());
            }
 
            @Override
            public void onFileNotFoundException(FileNotFoundException e,
                            Object state) {
                    Log.d(TAG, "FileNotFoundException: " + e.getMessage());
            }
 
            @Override
            public void onMalformedURLException(MalformedURLException e,
                            Object state) {
                    Log.d(TAG, "MalformedURLException: " + e.getMessage());
            }
 
            @Override
            public void onFacebookError(FacebookError e, Object state) {
                    Log.d(TAG, "FacebookError: " + e.getMessage());
            }
 
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            mFacebook.authorizeCallback(requestCode, resultCode, data);
    }
     
    public void postOnWall(String msg, String infografia_url) {
         try {

	        	Bundle params = new Bundle();
	 	        String response = mFacebook.request("me");
	 	        params.putString("message", msg);
	 	        params.putString("name", "V3RD");
	 	        params.putString("caption", "Pensando en verde");
	 	        params.putString("link", infografia_url);
	 	        params.putString("description", "Utilizá nuestra aplicación para saber donde tirar la basura y mejorar el medio ambiente. Reciclá, Reducí y Reutilizá.");
	 	        params.putString("picture", infografia_url);
                response = mFacebook.request("me/feed", params, 
                        "POST");
              
                if (response == null || response.equals("") || 
                        response.equals("false")) {
                	Toast.makeText(this, "Error al compartir en Facebook :(", Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(this, "Compartir en Facebook exitoso!", Toast.LENGTH_SHORT).show();
                }
                loading.hide();
         } catch(Exception e) {
             e.printStackTrace();
         }
    }
}
