package com.ma.blessing.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ma.blessing.R;
import com.ma.blessing.data.HistoryItem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoryItemView extends RelativeLayout {

    private ImageView mAvatarView;
    private TextView mNameView;
    private TextView mPhoneView;
    private TextView mTimeView;
    private TextView mMessageView;

    public HistoryItemView(Context context) {
        this(context, null);
    }

    public HistoryItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.send_history_item, this);
        mAvatarView = (ImageView) findViewById(R.id.avatar);
        mNameView = (TextView) findViewById(R.id.name);
        mPhoneView = (TextView) findViewById(R.id.phone);
        mTimeView = (TextView) findViewById(R.id.time);
        mMessageView = (TextView) findViewById(R.id.detail);
    }

    public void bindView(HistoryItem item, SimpleDateFormat format) {
        if (item.phone != null) {
            mAvatarView.setImageBitmap(item.photo);
        }
        mNameView.setText(item.name);
        mMessageView.setText(item.content);
        if (item.sendTime != 0) {
            Date date = new Date(item.sendTime);
            mTimeView.setText(format.format(date));
        }
        mPhoneView.setText(item.phone);
    }
}
