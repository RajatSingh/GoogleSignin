package com.appinvent.googlelibs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.ServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rajat on 24-02-2017.
 */

public class GetAuthAccessTokenAsync extends AsyncTask<String, String, String> {

    private Context context;
    private GoogleSignCallback mGoogleSignCallback;

    public GetAuthAccessTokenAsync(Context context,GoogleSignCallback googleSignCallback) {
        this.context = context;
        this.mGoogleSignCallback = googleSignCallback;
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
        hashMap.put("client_id","111516028293-la0kcmgn9qeka8v6qmcasvcvk70glc71.apps.googleusercontent.com");
        hashMap.put("client_secret","i1QEkkQuVt4M9uPdxH35eTGb");
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
        //NetworkUtils.sendGet("https://www.google.com/m8/feeds/contacts/default/thin?max-results=10000",accessToken);

       // return null;
    }



    @Override
    protected void onPostExecute(String authAccessToken) {
       Log.e("authAccessToken",authAccessToken);
        new GetGmailContactAsync(authAccessToken,mGoogleSignCallback).execute(authAccessToken);

    }
}