package com.ma.blessing.ui;

import com.ma.blessing.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemFootView extends LinearLayout {

    private TextView mTitleView;

    public ItemFootView(Context context) {
        this(context, null);
    }

    public ItemFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemFootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.item_foot_view, this);
        setOrientation(VERTICAL);
        mTitleView = (TextView) findViewById(R.id.title);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
