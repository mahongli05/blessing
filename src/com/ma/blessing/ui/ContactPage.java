package com.ma.blessing.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ma.blessing.R;
import com.ma.blessing.ui.PinYinVerticalNavigation.NavigationListener;

public class ContactPage extends FrameLayout implements NavigationListener {

    private ListView mListView;
    private PinYinVerticalNavigation mNavigationView;
    private TextView mNavigationTextView;

    public ContactPage(Context context) {
        this(context, null);
    }

    public ContactPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.contact_page, this);
        mListView = (ListView) findViewById(R.id.contact_list);
        mNavigationTextView = (TextView) findViewById(R.id.navigation_text);
        mNavigationView = (PinYinVerticalNavigation) findViewById(R.id.navigation);

        mNavigationView.setNavigationListener(this);
        mListView.setAdapter(new ContactAdapter());
    }

    @Override
    public void onNavigationStart(String currentChar) {
        mNavigationTextView.setText(currentChar);
        mNavigationTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNavigationFinished(String currentChar) {
        mNavigationTextView.setText(currentChar);
        mNavigationTextView.setVisibility(View.GONE);
    }

    @Override
    public void onNavigationChanged(String currentChar) {
        mNavigationTextView.setText(currentChar);
    }

    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(getContext());
                ((TextView) convertView).setText("吗矮冬瓜");
            }
            return convertView;
        }

    }
}
