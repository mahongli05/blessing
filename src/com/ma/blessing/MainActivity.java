package com.ma.blessing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ma.blessing.ui.BottomSelectBar;
import com.ma.blessing.ui.BottomSelectBar.OnItemSelectListener;
import com.ma.blessing.ui.ContactPage;
import com.ma.blessing.ui.HistoryPage;

public class MainActivity extends Activity implements OnPageChangeListener {

    private TextView mTitleView;
    private ViewPager mViewPager;
    private BottomSelectBar mBottomSelectBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blessing);
        setupView();
    }

    private void setupView() {
        mTitleView = (TextView) findViewById(R.id.title);
        mViewPager = (ViewPager) findViewById(R.id.content);
        mBottomSelectBar = (BottomSelectBar) findViewById(R.id.bottom_bar);

        findViewById(R.id.right).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mTitleView.setText(getString(R.string.blessing));

        mBottomSelectBar.initItems(new int[] {R.string.blessing_message,
                R.string.contact, R.string.history, R.string.me},
                new int[] {R.drawable.emoji_0023, R.drawable.emoji_0030,
                R.drawable.emoji_0031, R.drawable.emoji_0032},
                R.color.select_color, R.color.normal_color);
        mBottomSelectBar.setOnItemSelectListener(new OnItemSelectListener() {

            @Override
            public void onSelect(int index) {
                setCurrentPage(index);
            }
        });

        mViewPager.setAdapter(new ScreenAdapter(this));
        mViewPager.addOnPageChangeListener(this);

        setCurrentPage(0);
    }

    private void setCurrentPage(int index) {
        int viewPagerIndex = mViewPager.getCurrentItem();
        if (viewPagerIndex != index) {
            mViewPager.setCurrentItem(index);
        }

        mBottomSelectBar.select(index);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int currentPage, float offset, int offsetInPix) {

    }

    @Override
    public void onPageSelected(int index) {
        setCurrentPage(index);
    }

    private class ScreenAdapter extends PagerAdapter {

        private View[] mViews;

        ScreenAdapter(Context context) {
            super();
            mViews = new View[4];
            TextView textView = new TextView(context);
            textView.setText("page 0");
            mViews[0] = textView;
            mViews[1] = new ContactPage(context);
            mViews[2] = new HistoryPage(context);
            textView = new TextView(context);
            textView.setText("page 3");
            mViews[3] = textView;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return mViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews[position]);
            return mViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews[position]);
        }
    };
}
