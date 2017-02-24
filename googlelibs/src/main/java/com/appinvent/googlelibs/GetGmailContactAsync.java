package com.appinvent.googlelibs;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Rajat on 24-02-2017.
 */

public class GetGmailContactAsync extends AsyncTask<String, Void, String> {
    private String mAccessToken;
    private GoogleSignCallback mGoogleSignCallback;

    public GetGmailContactAsync(String accessToken,GoogleSignCallback googleSignCallback) {
        this.mAccessToken = accessToken;
        this.mGoogleSignCallback = googleSignCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = NetworkUtils.sendGet("https://www.google.com/m8/feeds/contacts/default/thin?max-results=10000",mAccessToken);
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mGoogleSignCallback.googleContactList(s);
    }
}
