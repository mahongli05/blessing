package com.ma.blessing.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.blessing.R;
import com.ma.blessing.data.Contact;

public class ContactItemView extends RelativeLayout {

    private ImageView mPictureView;
    private ImageView mTipsView;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private TextView mHeadView;

    public ContactItemView(Context context) {
        this(context, null);
    }

    public ContactItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.contact_item, this);
        mPictureView = (ImageView) findViewById(R.id.picture);
        mTipsView = (ImageView) findViewById(R.id.tips);
        mTitleView = (TextView) findViewById(R.id.title);
        mSubTitleView = (TextView) findViewById(R.id.sub_title);
        mHeadView = (TextView) findViewById(R.id.head);
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

    public void bindView(Contact contact, String head) {

        mTitleView.setText(contact.name);
        mSubTitleView.setText(contact.preferredPhone);
        if (contact.photo != null) {
            mPictureView.setImageBitmap(contact.photo);
        }

        if (TextUtils.isEmpty(head)) {
            mHeadView.setVisibility(GONE);
        } else {
            mHeadView.setVisibility(VISIBLE);
            mHeadView.setText(head);
        }
    }
}
