package com.rajatbeck.otpverification;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SMSActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TextInputLayout mobileNumberLayout, otpNumberLayout;
    private EditText mobileNumber, otpNumber;
    private Button btnSms, btnOtp;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mobileNumberLayout = (TextInputLayout) findViewById(R.id.mobile_number_layout);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        otpNumberLayout = (TextInputLayout) findViewById(R.id.otp_number_layout);
        otpNumber = (EditText) findViewById(R.id.otp_number);
        btnSms = (Button) findViewById(R.id.btn_call_otp);
        btnOtp = (Button) findViewById(R.id.btn_verify_otp);

        btnOtp.setOnClickListener(this);
        btnSms.setOnClickListener(this);

        prefManager = new PrefManager(getApplicationContext());
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (prefManager.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_call_otp:
                validateForm();
                break;
            case R.id.btn_verify_otp:
                validateOtp();
                break;
        }
    }

    public void validateForm() {
        String number = mobileNumber.getText().toString().trim();
        if (number.isEmpty() || !isValidPhoneNumber(number)) {
            mobileNumberLayout.setError("Enter correct data");
            mobileNumber.requestFocus();
            return;
        } else {
            mobileNumberLayout.setErrorEnabled(false);
            prefManager.setKeyMobileNumber(number);
            //TODO:call an API for sms request
            prefManager.setKeyIsWaitingForSms(true);
            viewPager.setCurrentItem(1);
        }
    }

    public void validateOtp() {
        String otp = otpNumber.getText().toString().trim();

        if (!otp.isEmpty()) {
            otpNumberLayout.setErrorEnabled(false);
            Intent grapprIntent = new Intent(getApplicationContext(), SmsService.class);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            otpNumberLayout.setError("field is empty");
            otpNumber.requestFocus();
        }
    }

    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }
}
