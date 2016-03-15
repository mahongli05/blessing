package com.ma.blessing.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BottomSelectBar extends LinearLayout implements OnPageChangeListener {

    private int mSelectColor;
    private int mNormalColor;
    private TabSelectItem[] mItems;
    private TabSelectItem mCurrentSelect;
    private OnItemSelectListener mItemSelectListener;

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (Integer)v.getTag();
            if (mItemSelectListener != null) {
                mItemSelectListener.onSelect((Integer) v.getTag());
            }
            select(index);
        }
    };

    public BottomSelectBar(Context context) {
        this(context, null);
    }

    public BottomSelectBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSelectBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(HORIZONTAL);
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    private void setColors(int selectColorId, int normalColorId) {
        mSelectColor = getResources().getColor(selectColorId);
        mNormalColor = getResources().getColor(normalColorId);
    }

    public void initItems(int[] titleIds, int[] drawableIds, int selectColorId, int normalColorId) {
        if (titleIds == null || drawableIds == null || titleIds.length != drawableIds.length) {
            throw new IllegalArgumentException("initItems params is illegal");
        }
        setColors(selectColorId, normalColorId);
        String[] titles = new String[titleIds.length];
        Drawable[] drawables = new Drawable[drawableIds.length];
        for (int i = 0; i < titleIds.length; i++) {
            titles[i] = getResources().getString(titleIds[i]);
        }
        for (int i = 0; i < drawableIds.length; i++) {
            drawables[i] = getResources().getDrawable(drawableIds[i]);
        }
        setTitles(titles, drawables);
    }

    private void setTitles(String[] titles, Drawable[] drawables) {
        mItems = new TabSelectItem[titles.length];
        LayoutParams params =
                new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < titles.length; i++) {
            mItems[i] = new TabSelectItem(getContext());
            mItems[i].setText(titles[i]);
            mItems[i].setDrawable(drawables[i]);
            mItems[i].setSelect(false);
            mItems[i].setTag(i);
            mItems[i].setOnClickListener(mOnClickListener);
            addView(mItems[i], params);
        }
    }

    public void updateTitle(int index, String title) {
        if (index >=0 && index < mItems.length) {
            mItems[index].setText(title);
        }
    }

    public void select(int index) {
        TabSelectItem targetItem = mItems[index];
        if (targetItem != mCurrentSelect) {
            targetItem.setSelect(true);
            if (mCurrentSelect != null) {
                mCurrentSelect.setSelect(false);
            }
            mCurrentSelect = targetItem;
            if (mItemSelectListener != null) {
                mItemSelectListener.onSelect(index);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        select(position);
    }

    private class TabSelectItem extends TextView {

        private Drawable mDrawable;

        public TabSelectItem(Context context) {
            super(context);
            setGravity(Gravity.CENTER_HORIZONTAL);
        }

        public void setDrawable(Drawable drawable) {
            mDrawable = drawable;
            setCompoundDrawablesWithIntrinsicBounds(null, mDrawable, null, null);
        }

        public void setSelect(boolean select) {
            if (select) {
                setTextColor(mSelectColor);
                mDrawable.setColorFilter(mSelectColor, PorterDuff.Mode.SRC_IN);
            } else {
                setTextColor(mNormalColor);
                mDrawable.setColorFilter(null);
            }
        }
    }

    public interface OnItemSelectListener {
        void onSelect(int index);
    }
}
