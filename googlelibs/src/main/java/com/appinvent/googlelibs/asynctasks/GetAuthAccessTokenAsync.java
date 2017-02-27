package com.appinvent.googlelibs.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.appinvent.googlelibs.NetworkUtils;
import com.appinvent.googlelibs.interfaces.GoogleSignCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rajat on 24-02-2017.
 */

public class GetAuthAccessTokenAsync extends AsyncTask<String, String, String> {

    private Context context;
    private GoogleSignCallback mGoogleSignCallback;
    private String mWebclientId, mWebclientSceret;

    public GetAuthAccessTokenAsync(Context context,GoogleSignCallback googleSignCallback,String webclientId,String webclientSceret) {
        this.context = context;
        this.mGoogleSignCallback = googleSignCallback;
        this.mWebclientId = webclientId;
        this.mWebclientSceret = webclientSceret;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        String accessToken = args[0];
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("code",accessToken);
        hashMap.put("client_id",mWebclientId);
        hashMap.put("client_secret",mWebclientSceret);
        hashMap.put("redirect_uri","");
        hashMap.put("grant_type","authorization_code");
        String response = NetworkUtils.performPostCall("https://accounts.google.com/o/oauth2/token",hashMap);
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }



    @Override
    protected void onPostExecute(String authAccessToken) {
       //Log.e("authAccessToken",authAccessToken);
        new GetGmailContactAsync(authAccessToken,mGoogleSignCallback).execute(authAccessToken);

    }
}