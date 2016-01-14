package com.ma.blessing.db;

import com.ma.blessing.db.ContactImportUtil.ContactImportCallback;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "blessing.db";

    public static final String CONTACT_TABLE = "contact";
    public static final String PHONE_TABLE = "phone";
    public static final String EMAIL_TABLE = "email";
    public static final String TAG_TABLE = "tag";
    public static final String SMS_TABLE = "sms";
    public static final String SEND_HISTORY_TABLE = "send_history";

    public static final String CREATE_CONTECT_TABLE = "create table "
            + "contact"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " name"    + " INTEGER, "
            + " contact_id"    + " TEXT,"
            + " preferred_phone"    + " TEXT,"
            + " preferred_email"    + " TEXT"
            + " unique (contact_id)"
            + " );";

    public static final String CREATE_PHONE_TABLE = "create table "
            + "phone"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " phone"    + " TEXT, "
            + " contact_id"    + " TEXT"
            + " );";

    public static final String CREATE_EMAIL_TABLE = "create table "
            + "email"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " email"    + " TEXT, "
            + " contact_id"    + " TEXT"
            + " );";

    public static final String CREATE_TAG_TABLE = "create table "
            + "tag"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " tag"    + " TEXT"
            + " );";

    public static final String CREATE_SMS_TABLE = "create table "
            + "sms"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " sms"    + " TEXT,"
            + " tags"    + " TEXT"
            + " );";

    public static final String CREATE_SEND_HISTORY_TABLE = "create table "
            + "send_history"
            + " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " content"    + " TEXT,"
            + " name"    + " TEXT,"
            + " phone"    + " TEXT,"
            + " group"    + " INTEGER,"
            + " type"    + " INTEGER,"
            + " time"    + " INTEGER NOT NULL"
            + " );";

    private static DatabaseHelper sInsctance;

    private Context mContext;

    public static void init(Context context) {
        sInsctance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        if (sInsctance == null) {
            throw new IllegalStateException("DatabaseHelper is not init");
        }
        return sInsctance;
    }

    private DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTECT_TABLE);
        db.execSQL(CREATE_PHONE_TABLE);
        db.execSQL(CREATE_EMAIL_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_SMS_TABLE);
        db.execSQL(CREATE_SEND_HISTORY_TABLE);

        importSystemContact(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void importSystemContact(final SQLiteDatabase db) {

        ContactImportUtil.importContact(mContext, ContactImportUtil.FLAG_EMAIL | ContactImportUtil.FLAG_PHONE,
                new ContactImportCallback() {

            @Override
            public void onContactStart() {
                db.beginTransaction();
            }

            @Override
            public void onContactImport(Bundle data) {
                String name = data.getString(ContactImportUtil.DATA_CONTACT_NAME);
                String id = data.getString(ContactImportUtil.DATA_CONTACT_ID);
                String[] phones = data.getStringArray(ContactImportUtil.DATA_CONTACT_PHONE);
                String[] emails = data.getStringArray(ContactImportUtil.DATA_CONTACT_EMAIL);

                if (!TextUtils.isEmpty(id)) {
                    ContentValues values = new ContentValues();
                    values.put(Columns.CONTACT_NAME, name);
                    values.put(Columns.CONTACT_ID, id);
                    db.insert("contact", null, values);

                    if (phones != null) {
                        values.remove(Columns.CONTACT_NAME);
                        for (String phone : phones) {
                            values.put(Columns.CONTACT_PHONE, phone);
                            db.insert("phone", null, values);
                        }
                    }

                    if (emails != null) {
                        values.remove(Columns.CONTACT_PHONE);
                        for (String email : emails) {
                            values.put(Columns.CONTACT_EMAIL, email);
                            db.insert("email", null, values);
                        }
                    }
                }
            }

            @Override
            public void onContactEnd(boolean success) {
                if (success) {
                    db.setTransactionSuccessful();
                }
                db.endTransaction();
            }
        });
    }
}
