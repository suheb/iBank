package com.kdapps.piggybank;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by suheb on 25/4/15.
 */
public class Utils {

    public static final String PHONE_NUMBER = "number";
    public static final String DISPLAY_NAME = "name";
    public static final String REG_ID = "registration_id";
    public static final String WEB_URL = "http://192.168.128.107/gcm/";
    public static final String TAG = "TAG";

    static void setAppParam(Context ctxt, String name, String value) {
        SharedPreferences settings = ctxt.getSharedPreferences(
                ctxt.getString(R.string.sharedpref), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);
        editor.commit();
    }

    static String getAppParam(Context ctxt, String name) {
        SharedPreferences settings = ctxt.getSharedPreferences(
                ctxt.getString(R.string.sharedpref), 0);

        return settings.getString(name, null);
    }

    static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
