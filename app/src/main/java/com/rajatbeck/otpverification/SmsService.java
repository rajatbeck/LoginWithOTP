package com.rajatbeck.otpverification;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by rajatbeck on 10/25/2016.
 */

public class SmsService extends IntentService {

    public SmsService(String name) {
        super(name);
    }

    public SmsService() {
        super("Sms Thread");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO:call SMS verification API
    }
}
