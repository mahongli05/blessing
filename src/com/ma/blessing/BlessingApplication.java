package com.ma.blessing;

import com.ma.blessing.db.DatabaseHelper;

import android.app.Application;
import android.content.Context;

public class BlessingApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        DatabaseHelper.init(base);
        super.attachBaseContext(base);
    }
}
