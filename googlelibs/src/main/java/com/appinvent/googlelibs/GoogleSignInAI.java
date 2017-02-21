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


    public static GoogleSignInAI getInstance(Activity activity, int requestCode,GoogleSignCallback  googleSignCallback) {
        if (mGoogleSignInAI == null) {
            mGoogleSignInAI = new GoogleSignInAI(activity,requestCode,googleSignCallback);
        }
        return mGoogleSignInAI;
    }

    public GoogleSignInAI(Activity activity, int requestCode,GoogleSignCallback  googleSignCallback) {
        this.mActivity = activity;
        this.GOOGLE_SIGN_IN_REQUEST_CODE = requestCode;
        mGoogleSignCallback = googleSignCallback;
        setUpGoogleClient();
    }

    /*
    * Configure google sign in request
    */
    private void setUpGoogleClient(){
        mGoogleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestId()
                .requestProfile()
                .requestIdToken("111516028293-vl0dkscqrogeimsvbet7v1j8d1olkh20.apps.googleusercontent.com")
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
         getProfileInfo(googleSignInAccount);

        }else{
       //Failure Message
            mGoogleSignCallback.googleSignInFailureResult(googleSignInResult.getStatus().getStatusMessage());
        }
        mSignInClicked = false;

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