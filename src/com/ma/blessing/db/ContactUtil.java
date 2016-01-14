package com.ma.blessing.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactUtil {

    public static Cursor queryAllContact() {
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        return db.query(DatabaseHelper.CONTACT_TABLE, null,
                null, null, null, null, "DES　by time");
    }
}
