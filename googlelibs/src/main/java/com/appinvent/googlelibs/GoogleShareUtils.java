package com.appinvent.googlelibs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.gms.plus.PlusShare;

/**
 * Created by Rajat on 27-02-2017.
 */

public class GoogleShareUtils {

    public static void onShareDialog(Activity mActivity,String text, String url) {
        if (TextUtils.isEmpty(url)) {
            Intent shareIntent = new PlusShare.Builder(mActivity)
                    .setType("text/plain")
                    .setText(text)
                    .getIntent();
            mActivity.startActivityForResult(shareIntent, 0);
        } else {
            Intent shareIntent = new PlusShare.Builder(mActivity)
                    .setType("text/plain")
                    .setText(text)
                    .setContentUrl(Uri.parse(url))
                    .getIntent();
            mActivity.startActivityForResult(shareIntent, 0);
        }

    }
}
