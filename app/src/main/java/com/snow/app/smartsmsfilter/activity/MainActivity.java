package com.snow.app.smartsmsfilter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Window;

import com.snow.app.smartsmsfilter.R;
import com.snow.app.smartsmsfilter.util.BaseActivity;

/**
 * Created by Administrator on 2016.03.08.
 */
public class MainActivity extends BaseActivity {
    private IntentFilter receiveSmsFilter;
    private SmartSmsFilterReceiver filterReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        receiveSmsFilter = new IntentFilter();
        receiveSmsFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiveSmsFilter.setPriority(100);
        filterReceiver = new SmartSmsFilterReceiver();
        registerReceiver(filterReceiver, receiveSmsFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(filterReceiver);
    }

    public class SmartSmsFilterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            String address = messages[0].getOriginatingAddress();
            if (address.startsWith("106") || address.startsWith("400")) {

                abortBroadcast();
            } else {
                String fullMessage = "";
                for (SmsMessage message : messages) {
                    fullMessage += message.getMessageBody();
                }
            }
        }
    }
}
