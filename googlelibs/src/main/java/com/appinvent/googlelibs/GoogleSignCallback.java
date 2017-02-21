package com.appinvent.googlelibs;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Rajat on 21-02-2017.
 */

public interface GoogleSignCallback {
     void googleSignInSuccessResult(GoogleSignInAccount googleSignInAccount);
     void googleSignInFailureResult(String s);
     void googleSignOutSussessResult(String s);
     void googleSignOutFailureResult(String s);
}
