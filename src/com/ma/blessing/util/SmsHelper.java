package com.ma.blessing.util;

import java.util.ArrayList;

import com.ma.blessing.receiver.SmsReceiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsHelper {

    private static final String EXTRA_PHONE = "phone";
    private static final String EXTRA_TEXT = "text";

    private static SmsHelper sInscance = new SmsHelper();

    private SmsHelper() {};

    public static SmsHelper getInstance() {
        return sInscance;
    }

    public void sendMessage(Context context, String phone, String message) {
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> list = manager.divideMessage(message); // 因为一条短信有字数限制，因此要将长短信拆分
        for (String text : list) {
            Intent sentIntent = new Intent(SmsReceiver.SMS_SENT_ACTION);
            sentIntent.putExtra(EXTRA_PHONE, phone);
            sentIntent.putExtra(EXTRA_TEXT, text);
            int id = phone.hashCode() * 31 + text.hashCode();
            PendingIntent sentPI = PendingIntent.getBroadcast(context, id, sentIntent, 0);
            Intent deliveryIntent = new Intent(SmsReceiver.SMS_DELIVERY_ACTION);
            deliveryIntent.putExtra(EXTRA_PHONE, phone);
            deliveryIntent.putExtra(EXTRA_TEXT, text);
            PendingIntent deliveryPI = PendingIntent.getBroadcast(context, id, deliveryIntent, 0);
            manager.sendTextMessage(phone, null, text, sentPI, deliveryPI);
        }
    }

    public void handleSentIntent(Context context, Intent intent, int resultCode) {
        switch (resultCode) {
        case Activity.RESULT_OK:
            Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
            break;
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            break;
        case SmsManager.RESULT_ERROR_RADIO_OFF:
            break;
        case SmsManager.RESULT_ERROR_NULL_PDU:
            break;
        }
    }

    public void handleDeliveryIntent(Context context, Intent intent) {

        Toast.makeText(context, "收信人已经成功接收", Toast.LENGTH_SHORT).show();
    }

    public static void init(final Context context) {

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                sentIntent, 0);
        // register the Broadcast Receivers
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                Toast.makeText(context, "收信人已经成功接收", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));
    }
}
