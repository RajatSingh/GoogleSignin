package com.appinvent.googlelibs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 24-02-2017.
 */

public class GetGmailContactAsync extends AsyncTask<String, Void, List<GmailContactBean>> {
    private String mAccessToken;
    private GoogleSignCallback mGoogleSignCallback;
    private ArrayList<GmailContactBean> mGmailContactBeanList;


    public GetGmailContactAsync(String accessToken,GoogleSignCallback googleSignCallback) {
        this.mAccessToken = accessToken;
        this.mGoogleSignCallback = googleSignCallback;
        mGmailContactBeanList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<GmailContactBean> doInBackground(String... strings) {
        String response = NetworkUtils.sendGet("https://www.google.com/m8/feeds/contacts/default/thin?max-results=10000&alt=json",mAccessToken);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject feedObject = jsonObject.getJSONObject("feed");
            JSONArray entryObject = feedObject.getJSONArray("entry");

            for (int i=0;i<entryObject.length();i++){

                GmailContactBean bean = new GmailContactBean();
                bean.setmUserName((entryObject.getJSONObject(i).getJSONObject("title")).getString("$t"));
                bean.setmUserEmail((entryObject.getJSONObject(i).getJSONArray("gd$email")).getJSONObject(0).getString("address"));
                mGmailContactBeanList.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mGmailContactBeanList;
    }

    @Override
    protected void onPostExecute(List<GmailContactBean> contactBeanList) {
        super.onPostExecute(contactBeanList);
        mGoogleSignCallback.googleContactList(contactBeanList);
    }
}
