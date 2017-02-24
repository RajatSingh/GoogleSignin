package com.appinvent.googlelibs; /**
 * Created by Rajat on 21-02-2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.api.services.people.v1.PeopleScopes;

/**
 * Created by Rajat on 21-02-2017.
 */

public class GoogleSignInAI implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{
    private static GoogleSignInAI mGoogleSignInAI;
    private Activity mActivity;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private int GOOGLE_SIGN_IN_REQUEST_CODE;
    private GoogleSignCallback mGoogleSignCallback;
    private String mWebclientId, mWebclientSceret;
    private static int mLoginPurpose;

    /**
     * Enum for getting the login purpose
     */
    public enum LOGIN_PURPOSE {
        LOGIN_SOCIAL,
        LOGIN_FETCH_CONTACT,
        SHARE_DIALOG,
        FETCH_FRIENDS,
        SHARE_ON_CIRCLE
    }


    //http://stackoverflow.com/questions/38743940/deprecated-plus-peopleapi-loadhttp://stackoverflow.com/questions/38743940/deprecated-plus-peopleapi-load
    public static GoogleSignInAI getInstance(Activity activity, int requestCode,GoogleSignCallback  googleSignCallback,String webclientId,String webclientSceret,int loginPurpose) {
        mLoginPurpose = loginPurpose;
        if (mGoogleSignInAI == null) {
            mGoogleSignInAI = new GoogleSignInAI(activity,requestCode,googleSignCallback,webclientId,webclientSceret);
        }
       return mGoogleSignInAI;
    }

    public GoogleSignInAI(Activity activity, int requestCode,GoogleSignCallback  googleSignCallback,String webclientId,String webclientSceret) {
        this.mActivity = activity;
        this.GOOGLE_SIGN_IN_REQUEST_CODE = requestCode;
        this.mGoogleSignCallback = googleSignCallback;
        this.mWebclientId = webclientId;
        this.mWebclientSceret = webclientSceret;
        if(mLoginPurpose==LOGIN_PURPOSE.LOGIN_SOCIAL.ordinal()){
            setUpGoogleClientForGoogleLogin();
        }else if(mLoginPurpose==LOGIN_PURPOSE.LOGIN_FETCH_CONTACT.ordinal()){
            setUpGoogleClientForContacts();
        }

    }

    /*
    * Configure google sign in request for contacts
    */
    private void setUpGoogleClientForContacts(){
        mGoogleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN),
                        new Scope(PeopleScopes.CONTACTS_READONLY),
                        new Scope(PeopleScopes.USER_EMAILS_READ),
                        new Scope(PeopleScopes.USERINFO_EMAIL),
                        new Scope(PeopleScopes.USER_PHONENUMBERS_READ))
                .requestServerAuthCode(mWebclientId)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGoogleSignInOptions)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

    }

    /*
    * Configure google sign in request for contacts
    */
    private void setUpGoogleClientForGoogleLogin(){
        mGoogleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGoogleSignInOptions)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
     if(mGoogleApiClient!=null){
         mGoogleApiClient.connect();
     }
    }

    public void doSignIn(){
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        mSignInClicked = true;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    public void onActivityResult(Intent data) {
        if(mSignInClicked) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(signInResult);
        }
    }


    private void handleSignInResult(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
         GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
         if(mLoginPurpose==LOGIN_PURPOSE.LOGIN_SOCIAL.ordinal()){
             getProfileInfo(googleSignInAccount);
         }else if(mLoginPurpose==LOGIN_PURPOSE.LOGIN_FETCH_CONTACT.ordinal()){
             getUserContactsList(googleSignInAccount);
         }


        }else{
       //Failure Message
            mGoogleSignCallback.googleSignInFailureResult(googleSignInResult.getStatus().getStatusMessage());
        }
        mSignInClicked = false;

    }

    private void getUserContactsList(GoogleSignInAccount googleSignInAccount) {
        new GetAuthAccessTokenAsync(mActivity,mGoogleSignCallback).execute(googleSignInAccount.getServerAuthCode());
    }

    private void getProfileInfo(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignCallback.googleSignInSuccessResult(googleSignInAccount);
    }

    public void doSignout(){
        if(mGoogleApiClient!=null&& mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        mGoogleApiClient.disconnect();
                        mGoogleSignCallback.googleSignOutSussessResult(status.getStatusMessage());
                    }else{
                        mGoogleSignCallback.googleSignOutFailureResult(status.getStatusMessage());
                    }
                }
            });
        }

    }




}