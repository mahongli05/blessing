package com.ma.blessing.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.blessing.HistoryActivity;
import com.ma.blessing.db.Columns.ContentStatus;
import com.rockerhieu.emojicon.R;

public class HistoryPage extends RelativeLayout implements OnClickListener {

    private TextView mEditHisotryView;
    private TextView mPendingView;
    private TextView mSuccessView;
    private TextView mFailView;

    public HistoryPage(Context context) {
        this(context, null);
    }

    public HistoryPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.history_page, this);
        mEditHisotryView = (TextView) findViewById(R.id.edit);
        mEditHisotryView.setOnClickListener(this);

        mPendingView = (TextView) findViewById(R.id.pending);
        mPendingView.setOnClickListener(this);

        mSuccessView = (TextView) findViewById(R.id.send_success);
        mSuccessView.setOnClickListener(this);

        mFailView = (TextView) findViewById(R.id.send_fail);
        mFailView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (mEditHisotryView == v) {
            showHistory(ContentStatus.SEND_EDIT);
        } else if (mFailView == v) {
            showHistory(ContentStatus.SEND_FAIL);
        } else if (mSuccessView == v) {
            showHistory(ContentStatus.SEND_SUCCESS);
        } else if (mPendingView == v) {
            showHistory(ContentStatus.SEND_PENDING);
        }
    }

    private void showHistory(int status) {
        Context context = getContext();
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(HistoryActivity.EXTRA_STATUS, status);
        context.startActivity(intent);
    }
}
