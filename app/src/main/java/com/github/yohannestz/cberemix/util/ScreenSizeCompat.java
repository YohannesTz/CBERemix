package com.github.yohannestz.cberemix.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.RequiresApi;

public class ScreenSizeCompat {
    private static Api api = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ? new ApiLevel30() : new Api();

    public static Size getScreenSize(Context context) {
        return api.getScreenSize(context);
    }

    @SuppressWarnings("deprecation")
    private static class Api {
        public Size getScreenSize(Context context) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics metrics = display != null ? new DisplayMetrics() : Resources.getSystem().getDisplayMetrics();
            if (display != null) {
                display.getRealMetrics(metrics);
            }
            return new Size(metrics.widthPixels, metrics.heightPixels);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private static class ApiLevel30 extends Api {
        @Override
        public Size getScreenSize(Context context) {
            WindowMetrics metrics = context.getSystemService(WindowManager.class).getCurrentWindowMetrics();
            return new Size(metrics.getBounds().width(), metrics.getBounds().height());
        }
    }
}