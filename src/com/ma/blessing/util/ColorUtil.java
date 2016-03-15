package com.ma.blessing.util;

public class ColorUtil {

    public static int mixColors(int colorA, int colorB, int progress) {
        return 0;
    }

    public static int combineColor(int[] colors) {
        return (colors[0] << 24)
                | (colors[1] << 16)
                | (colors[2] << 8)
                | colors[3];
    }

    public static int[] divideColor(int color) {
        int[] colors = new int[4];
        colors[0] = (color & 0xff000000) >> 24;
        colors[1] = (color & 0x00ff0000) >> 16;
        colors[2] = (color & 0x0000ff00) >> 8;
        colors[3] = (color & 0x000000ff);
        return colors;
    }
}
