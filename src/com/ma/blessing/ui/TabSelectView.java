package com.ma.blessing.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabSelectView extends LinearLayout {

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

    public TabSelectView(Context context) {
        this(context, null);
    }

    public TabSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabSelectView(Context context, AttributeSet attrs, int defStyle) {
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

    public void initItems(int[] titleIds, int selectColorId, int normalColorId) {
        setColors(selectColorId, normalColorId);
        String[] titles = new String[titleIds.length];
        for (int i = 0; i < titleIds.length; i++) {
            titles[i] = getResources().getString(titleIds[i]);
        }
        setTitles(titles);
    }

    private void setTitles(String[] titles) {
        mItems = new TabSelectItem[titles.length];
        LayoutParams params =
                new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < titles.length; i++) {
            mItems[i] = new TabSelectItem(getContext());
            mItems[i].setText(titles[i]);
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
        }
        if (mItemSelectListener != null) {
            mItemSelectListener.onSelect(index);
        }
    }

    private class TabSelectItem extends RelativeLayout {

        private TextView mTitileView;
        private View mIconView;

        public TabSelectItem(Context context) {
            super(context);
            setupView();
        }

        private void setupView() {
            mTitileView = new TextView(getContext());
            mTitileView.setGravity(Gravity.CENTER);
            mTitileView.setSingleLine();
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(mTitileView, params);

            mIconView = new View(getContext());
            params = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(mIconView, params);
        }

        public void setText(CharSequence text) {
            mTitileView.setText(text);
        }

        public void setSelect(boolean select) {
            if (select) {
                mTitileView.setTextColor(mSelectColor);
                mIconView.setBackgroundColor(mSelectColor);
            } else {
                mTitileView.setTextColor(mNormalColor);
                mIconView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        }
    }

    public interface OnItemSelectListener {
        void onSelect(int index);
    }
}
