package com.ma.blessing.db;

import java.util.ArrayList;
import java.util.List;

import com.ma.blessing.util.IOUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactImportUtil {

    public static final int FLAG_PHONE = 1;
    public static final int FLAG_EMAIL = 2;

    public static final String DATA_CONTACT_ID = "contact_id";
    public static final String DATA_CONTACT_NAME = "contact_name";
    public static final String DATA_CONTACT_PHONE = "contact_phone";
    public static final String DATA_CONTACT_EMAIL = "contact_email";

    private static final String TAG = "ContactUtil";

    /*
     * 读取联系人的信息
     */
    public static void importContact(Context context, int requestFlag, ContactImportCallback callback) {

        if (context == null || callback == null) {
            return;
        }

        boolean importSuccess = false;
        callback.onContactStart();
        Cursor cursor = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                return;
            }

            final int contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            while(cursor.moveToNext()) {
                String contactId = cursor.getString(contactIdIndex);
                String name = cursor.getString(nameIndex);
                Bundle bundle = new Bundle();
                bundle.putString(DATA_CONTACT_ID, contactId);
                bundle.putString(DATA_CONTACT_NAME, name);

                if ((requestFlag & FLAG_PHONE) == FLAG_PHONE) {
                    List<String> phoneList = getPhonesFromSystem(resolver, contactId);
                    String[] phones = new String[phoneList.size()];
                    bundle.putStringArray(DATA_CONTACT_PHONE, phoneList.toArray(phones));
                }

                if ((requestFlag & FLAG_EMAIL) == FLAG_EMAIL) {
                    List<String> emailList = getEmailsFromSystem(resolver, contactId);
                    String[] emails = new String[emailList.size()];
                    bundle.putStringArray(DATA_CONTACT_PHONE, emailList.toArray(emails));
                }

                callback.onContactImport(bundle);

                importSuccess = true;

                Log.d(TAG, String.format("contactId: %s, name: %s", contactId, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(cursor);
            callback.onContactEnd(importSuccess);
        }
    }

    /*
     * 查找该联系人的phone信息
     */
    private static List<String> getPhonesFromSystem(ContentResolver resolver, String contactId) {

        List<String> phones = new ArrayList<String>();
        Cursor cursor = null;
        try {
            cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            if (cursor != null && cursor.getCount() > 0) {
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                while(cursor.moveToNext()) {
                    String phoneNumber = cursor.getString(phoneIndex);
                    Log.i(TAG, phoneNumber);
                    phones.add(phoneNumber);
                }
            }
        } finally {
            IOUtil.close(cursor);
        }
        return phones;
    }

    private static List<String> getEmailsFromSystem(ContentResolver resolver, String contactId) {
        List<String> emails = new ArrayList<String>();
        Cursor cursor = null;
        try {
            cursor = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null, null);
            int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            while(cursor.moveToNext()) {
                String email = cursor.getString(emailIndex);
                emails.add(email);
                Log.i(TAG, email);
            }
        } finally {
            IOUtil.close(cursor);
        }
        return emails;
    }

    public interface ContactImportCallback {
        void onContactStart();
        void onContactEnd(boolean success);
        void onContactImport(Bundle data);
    }
}
