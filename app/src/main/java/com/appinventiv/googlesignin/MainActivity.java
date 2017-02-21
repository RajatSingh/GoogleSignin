package com.appinventiv.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appinvent.googlelibs.GoogleSignCallback;
import com.appinvent.googlelibs.GoogleSignInAI;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity implements GoogleSignCallback {
    private GoogleSignInAI mGoogleSignInAI;
    private int GOOGLE_LOGIN_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doLogin(View view){
        mGoogleSignInAI = GoogleSignInAI.getInstance(this,1001,this);
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
        Toast.makeText(this,"AccessToken: "+googleSignInAccount.getIdToken(),Toast.LENGTH_LONG).show();
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
}
