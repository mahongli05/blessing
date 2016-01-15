package com.ma.blessing.ui;

import com.ma.blessing.R;
import com.ma.blessing.data.Contact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoryItemView extends RelativeLayout {

    private ImageView mPictureView;
    private ImageView mTipsView;
    private TextView mTitleView;
    private TextView mSubTitleView;
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
        inflate(context, R.layout.contact_item, this);
        mPictureView = (ImageView) findViewById(R.id.picture);
        mTipsView = (ImageView) findViewById(R.id.tips);
        mTitleView = (TextView) findViewById(R.id.title);
        mSubTitleView = (TextView) findViewById(R.id.sub_title);
        mMessageView = (TextView) findViewById(R.id.message);
    }

    public void setPicture(int pictureId) {
        mPictureView.setImageDrawable(getResources().getDrawable(pictureId));
    }

    public void setTitle(int titleId) {
        mTitleView.setText(titleId);
    }

    public void setSubTitle(int subtitleId) {
        mSubTitleView.setText(subtitleId);
    }

    public void showTips() {
        mTipsView.setVisibility(View.VISIBLE);
    }

    public void hidTips() {
        mTipsView.setVisibility(View.INVISIBLE);
    }

    public void bindView(Contact contact, String message) {

        mTitleView.setText(contact.name);
        mSubTitleView.setText(contact.preferredPhone);
        mMessageView.setText(message);
        if (contact.photo != null) {
            mPictureView.setImageBitmap(contact.photo);
        }
    }
}
