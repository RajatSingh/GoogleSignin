package com.appinvent.googlelibs.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.appinvent.googlelibs.PeopleHelper;
import com.appinvent.googlelibs.interfaces.GoogleSignCallback;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 23-02-2017.
 */

public class PeopleContactsAsync extends AsyncTask<String, Void, List<Person>> {
    private Context mContext;
    private String mWebclientId, mWebclientSceret;
    private List<Person> connections;
    private GoogleSignCallback mGoogleSignCallback;
    public PeopleContactsAsync(Context context,String webclientId,String webclientSceret,GoogleSignCallback googleSignCallback) {
        this.mContext = context;
        this.mWebclientId = webclientId;
        this.mWebclientSceret = webclientSceret;
        this.mGoogleSignCallback = googleSignCallback;
        connections = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Person> doInBackground(String... params) {
          try {
            People peopleService = PeopleHelper.setUp(mContext, params[0],mWebclientId,mWebclientSceret);
            ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                    // This line's really important! Here's why:
                    // http://stackoverflow.com/questions/35604406/retrieving-information-about-a-contact-with-google-people-api-java
                    .setRequestMaskIncludeField("person.names,person.emailAddresses,person.phoneNumbers")
                    .execute();
            connections = response.getConnections();

            /*for (Person person : connections) {
                if (!person.isEmpty()) {
                    List<Name> names = person.getNames();
                    List<EmailAddress> emailAddresses = person.getEmailAddresses();
                    List<PhoneNumber> phoneNumbers = person.getPhoneNumbers();

                    if (phoneNumbers != null)
                        for (PhoneNumber phoneNumber : phoneNumbers)
                            Log.d(TAG, "phone: " + phoneNumber.getValue());

                    if (emailAddresses != null)
                        for (EmailAddress emailAddress : emailAddresses)
                            Log.d(TAG, "email: " + emailAddress.getValue());

                    if (names != null)
                        for (Name name : names)
                            nameList.add(name.getDisplayName());

                }
            }
*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connections;
    }


    @Override
    protected void onPostExecute(List<Person> nameList) {
        super.onPostExecute(nameList);
        //mGoogleSignCallback.googleContactList(nameList);

    }
}

