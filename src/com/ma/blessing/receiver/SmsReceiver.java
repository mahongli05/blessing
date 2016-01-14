package com.ma.blessing.receiver;

import com.ma.blessing.util.SmsHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    public static final String SMS_SENT_ACTION = "com.ma.blessing.SMS_SENT_ACTION";

    public static final String SMS_DELIVERY_ACTION = "com.ma.blessing.SMS_DELIVERY_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (SMS_SENT_ACTION.equals(action)) {
            SmsHelper.getInstance().handleSentIntent(context, intent, getResultCode());
        } else if (SMS_DELIVERY_ACTION.equals(action)) { 
            SmsHelper.getInstance().handleDeliveryIntent(context, intent);
        }

        Log.d(TAG, intent.toString());
    }

}
