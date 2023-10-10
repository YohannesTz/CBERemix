package com.github.yohannestz.cberemix.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {
    public static int dpToPx(final float dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isHex(String input) {
        return input.matches("^[0-9a-f]+$|^[0-9A-F]+$");
    }
}
