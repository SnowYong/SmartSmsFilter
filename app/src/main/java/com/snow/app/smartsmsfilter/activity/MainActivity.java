package com.snow.app.smartsmsfilter.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.snow.app.smartsmsfilter.R;
import com.snow.app.smartsmsfilter.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016.03.08.
 */
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private IntentFilter receiveSmsFilter;
    private SmartSmsFilterReceiver filterReceiver;

    private ListView smsListView;
    private ArrayAdapter<String> smsListAdapter;
    private List<String> smsList;

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

        smsListView = (ListView) findViewById(R.id.smart_sms_content_list);
        smsList = new ArrayList<String>();
        smsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsList);
        smsListView.setAdapter(smsListAdapter);
        smsListView.setOnItemClickListener(this);

        getSmsContent();
    }

    private void getSmsContent() {
        smsList.add(getString(R.string.list_first_item));

        Uri smsUri = Uri.parse("content://sms/");
        Uri contactsUri = null;
        ContentResolver resolver = getContentResolver();
        String[] smsProjection = {"address"};
        String[] contactsProjection = {ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor smsCursor = null;
        Cursor contactsCursor = null;

        try {
            smsCursor = resolver.query(smsUri, smsProjection, null, null, null);
            if (smsCursor.moveToFirst()) {
                do {
                    String smsFrom = smsCursor.getString(smsCursor.getColumnIndex("address"));
                    while (smsFrom.contains(" ")) {
                        smsFrom = smsFrom.replace(" ", "");
                    }


                    contactsUri = Uri.withAppendedPath(
                            ContactsContract.PhoneLookup.CONTENT_FILTER_URI, smsFrom);
                    try {
                        contactsCursor = resolver.query(contactsUri, contactsProjection,
                                null, null, null);
                        if (contactsCursor.moveToFirst()) {
                            smsFrom = contactsCursor.getString(contactsCursor
                                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (contactsCursor != null) {
                            contactsCursor.close();
                        }
                    }

                    if (!smsList.contains(smsFrom) && !smsFrom.startsWith("106")
                            && !smsFrom.startsWith("400") && !smsFrom.startsWith("9")) {
                        smsList.add(smsFrom);
                    }
                } while (smsCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (smsCursor != null) {
                smsCursor.close();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(filterReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContentResolver resolver = getContentResolver();
        Uri smsUri = Uri.parse("content://sms/");
        String[] smsProjection = {"address"};
        Cursor smsCursor = null;
        if (smsList.get(position).equals("骚扰短信")) {
            smsList.clear();
            try {
                smsCursor = resolver.query(smsUri, smsProjection, null, null, null);
                if (smsCursor.moveToFirst()) {
                    do {
                        String smsFrom = smsCursor.getString(smsCursor.getColumnIndex("address"));
                        while (smsFrom.contains(" ")) {
                            smsFrom = smsFrom.replace(" ", "");
                        }

                        if (smsFrom.startsWith("106") || smsFrom.startsWith("9") || smsFrom.startsWith("400")) {
                            if (!smsList.contains(smsFrom)) {
                                smsList.add(smsFrom);
                            }
                        }
                    } while (smsCursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (smsCursor != null) {
                    smsCursor.close();
                }
            }

            smsListAdapter.notifyDataSetChanged();
        }
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
