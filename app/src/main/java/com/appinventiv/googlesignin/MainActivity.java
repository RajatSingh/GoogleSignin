package com.appinventiv.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appinvent.googlelibs.GoogleShareUtils;
import com.appinvent.googlelibs.GoogleSignInAI;
import com.appinvent.googlelibs.interfaces.GoogleSignCallback;
import com.appinvent.googlelibs.model.GmailContactBean;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.services.people.v1.model.Person;

import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleSignCallback {
    private GoogleSignInAI mGoogleSignInAI;
    private int GOOGLE_LOGIN_REQUEST_CODE = 1001;
    private String GOOGLE_WEB_CLIENT_ID = "111516028293-la0kcmgn9qeka8v6qmcasvcvk70glc71.apps.googleusercontent.com";
    private String GOOGLE_WEB_CLIENT_SECRET = "i1QEkkQuVt4M9uPdxH35eTGb";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doLogin(View view){
        mGoogleSignInAI = GoogleSignInAI.getInstance(this,1001,this,GOOGLE_WEB_CLIENT_ID,GOOGLE_WEB_CLIENT_SECRET,GoogleSignInAI.LOGIN_PURPOSE.LOGIN_FETCH_CONTACT.ordinal());
        mGoogleSignInAI.doSignIn();
    }
    public void doLogout(View view){
        if(mGoogleSignInAI!=null) {
            mGoogleSignInAI.doSignout();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(GOOGLE_LOGIN_REQUEST_CODE == requestCode) {
            mGoogleSignInAI.onActivityResult(data);
        }
    }

    @Override
    public void googleSignInSuccessResult(GoogleSignInAccount googleSignInAccount) {
        Toast.makeText(this,"Hello "+googleSignInAccount.getDisplayName(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void googleSignInFailureResult(String s) {
       Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void googleSignOutSussessResult(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void googleSignOutFailureResult(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void googleProfileInfo(Person person) {

    }

    @Override
    public void googleContactList(List<GmailContactBean> personList) {
        Toast.makeText(this,personList.size()+"",Toast.LENGTH_LONG).show();

    }

    public void shareOnGoogle(View view){
        GoogleShareUtils.onShareDialog(this,"Hello","https://appinventiv.com/");
    }
}
