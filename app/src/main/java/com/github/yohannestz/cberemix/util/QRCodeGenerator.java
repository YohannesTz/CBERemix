package com.github.yohannestz.cberemix.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.github.alexzhirkevich.customqrgenerator.HighlightingType;
import com.github.alexzhirkevich.customqrgenerator.QrData;
import com.github.alexzhirkevich.customqrgenerator.QrErrorCorrectionLevel;
import com.github.alexzhirkevich.customqrgenerator.QrHighlighting;
import com.github.alexzhirkevich.customqrgenerator.QrHighlightingKt;
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawableKt;
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QVectorShapeUtilsKt;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes;
import com.github.yohannestz.cberemix.R;
import com.google.android.material.elevation.SurfaceColors;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import kotlin.Pair;

public class QRCodeGenerator {
    private final Context context;

    public QRCodeGenerator(Context context) {
        this.context = context;
    }

    public Drawable drawableFromText(String data) {
        if (Utils.isHex(data)) {
            data = data.toUpperCase();
        }
        QrData qrData = new QrData.Text(data);
        return QrCodeDrawableKt.QrCodeDrawable(qrData, getQrVectorOptions(data), StandardCharsets.UTF_8);
    }

    private QrVectorOptions getQrVectorOptions(String data) {
        if (data.length() < 140)
            return getDesignQrVectorOptions();
        else
            return getCompatibilityQrVectorOptions();
    }

    private QrVectorOptions getDesignQrVectorOptions() {
        QrVectorLogo.Builder qrVectorLogoBuilder = new QrVectorLogo.Builder();
        qrVectorLogoBuilder.setDrawable(ContextCompat.getDrawable(context, R.drawable.cbe));
        qrVectorLogoBuilder.setShape(new QrVectorLogoShape.RoundCorners(0.5f));
        qrVectorLogoBuilder.setPadding(new QrVectorLogoPadding.Natural(.1f));
        qrVectorLogoBuilder.setSize(0.1f);

        QrVectorLogo qrVectorLogo = qrVectorLogoBuilder.build();

        QrVectorOptions.Builder optionsBuilder = getDefaultOptionsBuilder()
                .setShapes(
                        new QrVectorShapes(
                                new QrVectorPixelShape.Rect(.75f),
                                new QrVectorPixelShape.Rect(.75f),
                                new QrVectorBallShape.RoundCorners(.15f, true, true, true, true),
                                new QrVectorFrameShape.RoundCorners(.15f, 1f, true, true, true, true, 7),
                                true
                        )
                )
                .setColors(
                        new QrVectorColors(
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context)),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary)),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary)),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary))
                        )
                )
                .setLogo(qrVectorLogo);

        optionsBuilder.setErrorCorrectionLevel(QrErrorCorrectionLevel.High);
        return optionsBuilder.build();
    }

    private QrVectorOptions getCompatibilityQrVectorOptions() {
        QrVectorLogo.Builder qrVectorLogoBuilder = new QrVectorLogo.Builder();
        qrVectorLogoBuilder.setDrawable(ContextCompat.getDrawable(context, R.drawable.cbe_vector));
        qrVectorLogoBuilder.setShape(new QrVectorLogoShape.RoundCorners(0.5f));
        qrVectorLogoBuilder.setPadding(new QrVectorLogoPadding.Natural(.1f));
        qrVectorLogoBuilder.setSize(0.1f);

        QrVectorLogo qrVectorLogo = qrVectorLogoBuilder.build();

        return getDefaultOptionsBuilder()
                .setShapes(
                        new QrVectorShapes(
                                new QrVectorPixelShape.RoundCorners(.0f),
                                new QrVectorPixelShape.RoundCorners(.35f),
                                new QrVectorBallShape.RoundCorners(.15f, true, true, true, true),
                                new QrVectorFrameShape.RoundCorners(.15f, 1f, true, true, true, true, 7),
                                true
                        )
                )
                .setLogo(qrVectorLogo)
                .build();

    }

    private QrVectorOptions.Builder getDefaultOptionsBuilder() {
        return new QrVectorOptions.Builder()
                .setPadding(.1f)
                .setErrorCorrectionLevel(QrErrorCorrectionLevel.High);
    }

    //for future
    private static Bitmap drawableToBitmap(Drawable drawable, int size) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
