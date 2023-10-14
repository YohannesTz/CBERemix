package com.github.yohannestz.cberemix.util;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;

import java.nio.ByteBuffer;

public class Utils {
    public static int dpToPx(final float dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isHex(String input) {
        return input.matches("^[0-9a-f]+$|^[0-9A-F]+$");
    }

    public static Bitmap imageToBitmap(Image image) {
        try {
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int width = image.getWidth();
            int height = image.getHeight();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;

            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.RGB_565);
            bitmap.copyPixelsFromBuffer(buffer);

            return Bitmap.createBitmap(bitmap, 0, 0, width, height);
        } catch (Exception e) {
            Log.e("TAG", "Error converting image to bitmap: " + e.getMessage());
            return null;
        } finally {
            image.close();
        }
    }
}
