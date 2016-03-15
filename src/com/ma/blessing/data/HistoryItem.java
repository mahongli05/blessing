package com.ma.blessing.data;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ma.blessing.db.Columns;

public class HistoryItem {

    public String id;
    public String name;
    public String phone;
    public String email;
    public Bitmap photo;
    public int status;

    public String content;
    public long sendTime;
    public long updateTime;

    public static HistoryItem parseFromCursor(Cursor cursor) {
        if (cursor != null) {
            HistoryItem item = new HistoryItem();
            item.id = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_ID));
            item.name = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_NAME));
            item.phone = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_PREFERRED_PHONE));
            item.email = cursor.getString(cursor.getColumnIndex(Columns.CONTACT_PREFFERED_EMAIL));
            byte[] data = cursor.getBlob(cursor.getColumnIndex(Columns.CONTACT_PHOTO));
            if (data != null) {
                item.photo = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            item.content = cursor.getString(cursor.getColumnIndex(Columns.CONTENT_CONTENT));
            item.status = cursor.getInt(cursor.getColumnIndex(Columns.CONTENT_STATUS));
            item.sendTime = cursor.getInt(cursor.getColumnIndex(Columns.CONTENT_SEND_TIME));
            item.updateTime = cursor.getInt(cursor.getColumnIndex(Columns.CONTENT_UPDATE_TIME));
            return item;
        }
        return null;
    }
}
