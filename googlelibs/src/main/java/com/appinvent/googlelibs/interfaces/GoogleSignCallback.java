package com.appinvent.googlelibs.interfaces;


import com.appinvent.googlelibs.model.GmailContactBean;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

/**
 * Created by Rajat on 21-02-2017.
 */

public interface GoogleSignCallback {
     void googleSignInSuccessResult(GoogleSignInAccount googleSignInAccount);
     void googleSignInFailureResult(String s);
     void googleSignOutSussessResult(String s);
     void googleSignOutFailureResult(String s);
     void googleContactList(List<GmailContactBean> contactListResponse);
}
