package com.rajatbeck.otpverification;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rajatbeck on 10/25/2016.
 */

public class PrefManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String KEY_IS_WAITING_FOR_SMS = "is_Waiting_for_sms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.prefs_key), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setKeyIsWaitingForSms(boolean isWaitingForSms) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaitingForSms);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return sharedPreferences.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setKeyMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return sharedPreferences.getString(KEY_MOBILE_NUMBER, null);
    }

    public void setKeyIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
    }

    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
