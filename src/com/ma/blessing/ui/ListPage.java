package com.ma.blessing.ui;

import com.rockerhieu.emojicon.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListPage extends LinearLayout {

    private TextView mEmptyView;
    private ListView mListView;

    public ListPage(Context context) {
        this(context, null);
    }

    public ListPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.send_history_page, this);
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        mListView = (ListView) findViewById(R.id.contact_list);
        mListView.setEmptyView(mEmptyView);
    }

    public void bindDate(ListAdapter adapter, String emptyMessage) {
        mListView.setAdapter(adapter);
        mEmptyView.setText(emptyMessage);
    }
}
