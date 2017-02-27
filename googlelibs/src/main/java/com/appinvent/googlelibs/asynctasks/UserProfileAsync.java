package com.appinvent.googlelibs.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.appinvent.googlelibs.PeopleHelper;
import com.appinvent.googlelibs.interfaces.GoogleSignCallback;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;

/**
 * Created by Rajat on 24-02-2017.
 */

public class UserProfileAsync extends AsyncTask<String, Void, Person> {
    private Context mContext;
    private String mWebclientId, mWebclientSceret;
    private Person person;
    private GoogleSignCallback mGoogleSignCallback;
    public UserProfileAsync(Context context,String webclientId,String webclientSceret,GoogleSignCallback googleSignCallback) {
        this.mContext = context;
        this.mWebclientId = webclientId;
        this.mWebclientSceret = webclientSceret;
        this.mGoogleSignCallback = googleSignCallback;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Person doInBackground(String... params) {
        try {
            People peopleService = PeopleHelper.setUp(mContext, params[0],mWebclientId,mWebclientSceret);
            person =peopleService.people().get("people/me").execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return person;
    }


    @Override
    protected void onPostExecute(Person person) {
        super.onPostExecute(person);
        //mGoogleSignCallback.googleContactList(nameList);

    }
}