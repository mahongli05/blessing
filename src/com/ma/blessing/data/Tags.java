package com.ma.blessing.data;

public class Tags {

    String mName;
    int mValue;

    public Tags(int value, String name) {
        mName = name;
        mValue = value;
    }

    public int getValue() {
        return mValue;
    };

    public String getName() {
        return mName;
    }

    /*
     * 1. normal
     * 2. custom
     * 3. monkey
     * 4. boy
     * 5. girl
     * 6. teacher
     * ...
     *
     * */
}
