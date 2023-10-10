package com.github.yohannestz.cberemix.util;

import static com.github.alexzhirkevich.customqrgenerator.style.ColorUtillsKt.Color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.alexzhirkevich.customqrgenerator.HighlightingType;
import com.github.alexzhirkevich.customqrgenerator.QrData;
import com.github.alexzhirkevich.customqrgenerator.QrErrorCorrectionLevel;
import com.github.alexzhirkevich.customqrgenerator.QrHighlighting;
import com.github.alexzhirkevich.customqrgenerator.QrHighlightingKt;
import com.github.alexzhirkevich.customqrgenerator.style.BitmapScale;
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

public class QRCodeGenerator {
    private final Context context;

    public QRCodeGenerator(Context context) {
        this.context = context;
    }

    // drawables had bad performance in animations. Bitmaps see to work fine.
    public Bitmap bitmapFromText(String data, int size) {
        return drawableToBitmap(drawableFromText(data), size);
    }

    public Drawable drawableFromText(String data) {
        if (Utils.isHex(data)) {
            // If we have a hex string, uppercase it so Alphanumeric encoding is used instead of binary, which produces smaller codes
            data = data.toUpperCase();
        }
        QrData qrData = new QrData.Text(data);
        return QrCodeDrawableKt.QrCodeDrawable(qrData, getQrVectorOptions(data), StandardCharsets.UTF_8);
    }

    private QrVectorOptions getQrVectorOptions(String data) {
        if (data.length() < 140)
            return getDesignQrVectorOptions(data);
        else
            return getCompatibilityQrVectorOptions();
    }

    private QrVectorOptions getDesignQrVectorOptions(String data) {
        QrVectorLogo.Builder qrVectorLogoBuilder = new QrVectorLogo.Builder();
        qrVectorLogoBuilder.setDrawable(ContextCompat.getDrawable(context, R.drawable.cbe_vector));
        qrVectorLogoBuilder.setShape(new QrVectorLogoShape.RoundCorners(0.25f));
        qrVectorLogoBuilder.setPadding(new QrVectorLogoPadding.Natural(.2f));
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
                                new QRRandomPixelColor(data, ContextCompat.getColor(context, R.color.md_theme_light_primary), ContextCompat.getColor(context, R.color.md_theme_light_secondary), 0.02f, 5),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary)),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary))
                        )
                )
                .setBackground(
                        new QrVectorBackground(
                                ContextCompat.getDrawable(context, R.drawable.bg_box),
                                (drawable, i, i1) -> Bitmap.createBitmap(i, i1, Bitmap.Config.ARGB_8888),
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context))
                        )
                )
                .setLogo(qrVectorLogo)
                .setAnchorsHighlighting(new QrHighlighting(
                        new HighlightingType.Styled(
                                QrHighlightingKt.QrVersionEyeShape(
                                        new QrVectorFrameShape.AsPixelShape(new QrVectorPixelShape.Rect(.75f), 5),
                                        QVectorShapeUtilsKt.asBallShape(new QrVectorPixelShape.Rect(.75f))),
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context)),
                                new QrVectorPixelShape.Rect(.0f),
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context))
                        ),
                        new HighlightingType.Styled(
                                QrHighlightingKt.QrVersionEyeShape(
                                        new QrVectorFrameShape.AsPixelShape(new QrVectorPixelShape.Rect(.75f), 5),
                                        QVectorShapeUtilsKt.asBallShape(new QrVectorPixelShape.Rect(.75f))),
                                new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary)),
                                new QrVectorPixelShape.Rect(.0f),
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context))
                        ),
                        new HighlightingType.Styled(
                                new QrVectorPixelShape.Rect(.75f), new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context)),
                                new QrVectorPixelShape.Rect(.75f), new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary))),
                        1.0f
                ));


        if (data.length() > 60) {
            optionsBuilder.setErrorCorrectionLevel(QrErrorCorrectionLevel.Low);
        } else {
            optionsBuilder.setErrorCorrectionLevel(QrErrorCorrectionLevel.Medium);
        }

        new HighlightingType.Styled(new QrVectorFrameShape.RoundCorners(.15f, 1f, true, true, true, true, 7), new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context)), new QrVectorFrameShape.RoundCorners(.15f, 1f, true, true, true, true, 7), new QrVectorColor.Solid(ContextCompat.getColor(context, R.color.md_theme_light_primary)));
        QrHighlightingKt.QrVersionEyeShape(
                new QrVectorFrameShape.AsPixelShape(
                        new QrVectorPixelShape.Rect(.75f), 5
                ),
                QVectorShapeUtilsKt.asBallShape(new QrVectorPixelShape.Rect(.75f))
        );

        return optionsBuilder.build();
    }

    private QrVectorOptions getCompatibilityQrVectorOptions() {
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
                .build();
    }

    private QrVectorOptions.Builder getDefaultOptionsBuilder() {
        return new QrVectorOptions.Builder()
                .setPadding(.2f)
                .setBackground(
                        new QrVectorBackground(
                                ContextCompat.getDrawable(context, R.drawable.bg_box),
                                (drawable, i, i1) -> Bitmap.createBitmap(i, i1, Bitmap.Config.ARGB_8888),
                                new QrVectorColor.Solid(SurfaceColors.SURFACE_1.getColor(context))
                        )
                )
                .setErrorCorrectionLevel(QrErrorCorrectionLevel.Low);
    }

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
