package com.ma.blessing.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class SelectTextView extends EditText {

    public SelectTextView(Context context) {
        this(context, null);
    }

    public SelectTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void setSelection(int start, int stop) {
    	// TODO Auto-generated method stub
    	super.setSelection(start, stop);
    }
    
    @Override
    public void setSelection(int index) {
    	// TODO Auto-generated method stub
    	super.setSelection(index);
    }
    
    private void updateSelection() {
    }
    
    @Override
    public void setText(CharSequence text, BufferType type) {
    	// TODO Auto-generated method stub
    	super.setText(text, type);
    }
}
