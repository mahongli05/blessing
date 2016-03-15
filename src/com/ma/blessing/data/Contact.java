package com.ma.blessing.data;

import java.io.Serializable;
import java.util.List;

import com.ma.blessing.db.Columns;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Contact implements Serializable {

    private static final long serialVersionUID = 201601291709L;

    public String id;
    public String name;
    public List<String> phones;
    public List<String> emails;
    public String preferredPhone;
    public String preferredEmail;
    public Bitmap photo;
    public String nameAsChar;

    public static Contact parseFromCursor(Cursor cursor) {
        if (cursor != null) {
            Contact contact = new Contact();
            contact.id = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_ID));
            contact.name = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_NAME));
            contact.preferredPhone = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_PREFERRED_PHONE));
            contact.preferredEmail = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_PREFFERED_EMAIL));
            contact.nameAsChar = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_CHARS));
            byte[] data = cursor.getBlob(cursor.getColumnIndex(Columns.CONTACT_PHOTO));
            if (data != null) {
                contact.photo = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            return contact;
        }
        return null;
    }
}
