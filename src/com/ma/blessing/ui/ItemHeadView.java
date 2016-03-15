package com.ma.blessing.ui;

import com.ma.blessing.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemHeadView extends RelativeLayout {

    private TextView mTitleView;

    public ItemHeadView(Context context) {
        this(context, null);
    }

    public ItemHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.item_head_view, this);
        mTitleView = (TextView) findViewById(R.id.title);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
