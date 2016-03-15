package com.ma.blessing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TitleBarActivity extends Activity {

    protected TextView mLeftTextView;
    protected TextView mRightTextView;
    protected TextView mTitleView;
    protected ImageView mLeftImageView;
    protected ImageView mRightImageView;
    protected View mLeftView;
    protected View mRightView;
    protected FrameLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_titlebar);
        setupView();
    }

    private void setupView() {
        mLeftImageView = (ImageView) findViewById(R.id.left_image);
        mLeftTextView = (TextView) findViewById(R.id.left_text);
        mLeftView = findViewById(R.id.left);
        mRightImageView = (ImageView) findViewById(R.id.right_image);
        mRightTextView = (TextView) findViewById(R.id.right_text);
        mRightView = findViewById(R.id.right);
        mTitleView = (TextView) findViewById(R.id.title);
        mContentLayout = (FrameLayout) findViewById(R.id.content);

        mLeftView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onSetupView();
    }

    protected void onSetupView() {

    }
}
