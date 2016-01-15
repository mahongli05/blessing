package com.ma.blessing.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PinYinVerticalNavigation extends View {

    private static final float TEXT_HEIGHT_RATE = 0.8f;

    public static final String[] CHARS = {".", "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" , "#"};

    private Paint mPaint;
    private float mTextSize;
    private float mTextPlaceSize;
    private float mTextOffsetY;
    private NavigationListener mListener;
    private String mCurrentChar;

    public PinYinVerticalNavigation(Context context) {
        this(context, null);
    }

    public PinYinVerticalNavigation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinYinVerticalNavigation(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setCharColor(int color) {
        mPaint.setColor(color);
    }

    public void setNavigationListener(NavigationListener listener) {
        mListener = listener;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTextPlaceSize = (h - getPaddingTop() - getPaddingBottom()) / (float) CHARS.length;
        mTextSize = mTextPlaceSize * TEXT_HEIGHT_RATE;
        mTextOffsetY = (mTextPlaceSize - mTextPlaceSize) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChars(canvas);
    }

    private void drawChars(Canvas canvas) {
        for (int i = 0; i < CHARS.length; i++) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(mTextSize);
            float y = mTextPlaceSize * (i + 1) + mTextOffsetY + getPaddingTop();
            canvas.drawText(CHARS[i], 10, y, mPaint);// 画字符到控件上
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        float y = (int) event.getY();// 获致触摸到控件上的Y坐标值
        String currentChar = getCurrentChar(y);

        switch (event.getAction()) {// 判断触摸事件的类型
        case MotionEvent.ACTION_DOWN: {// 当类型为按下
            if (mListener != null) {
                mListener.onNavigationStart(currentChar);
            }
            break;
        }
        case MotionEvent.ACTION_UP:// 当类型为抬起
        case MotionEvent.ACTION_CANCEL:
            if (mListener != null) {
                mListener.onNavigationFinished(currentChar);
            }
            break;
        case MotionEvent.ACTION_MOVE: {// 当类型为移动
            if (mListener != null && !currentChar.equals(mCurrentChar)) {
                mListener.onNavigationChanged(currentChar);
            }
            break;
        }
        default:
            break;
        }

        mCurrentChar = currentChar;

        return true;
    }

    private String getCurrentChar(float y) {
        float validY = y - getPaddingTop();
        float validH = getHeight() - getPaddingBottom() - getPaddingTop();
        int position = (int) ((validY / validH) * (float) CHARS.length);// //获取具体按下哪个字母
        if (position < 0 || position >= CHARS.length) {
            return null;
        }
        return CHARS[position];
    }

    public interface NavigationListener {
        void onNavigationStart(String currentChar);
        void onNavigationFinished(String currentChar);
        void onNavigationChanged(String currentChar);
    }
}
