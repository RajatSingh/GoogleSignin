package com.appinvent.googlelibs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Rajat on 22-02-2017.
 */

public class NetworkUtils {
    public static String hitWebService(String url,String accessToken) {
        String result = null;
        HttpURLConnection httpURLConnection = null;
        String parameter="";
        DataOutputStream printout;
        try {
            if (parameter != null) {
                httpURLConnection = (HttpURLConnection) (new URL(url)).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
               httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer "+accessToken);
                httpURLConnection.connect();
                // Send POST output.
                printout = new DataOutputStream(
                        httpURLConnection.getOutputStream());
                printout.writeBytes(parameter.toString());
                Log.d("message", parameter.toString());
                printout.flush();
                printout.close();

                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line + "\n");
                        }
                        result = stringBuilder.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
