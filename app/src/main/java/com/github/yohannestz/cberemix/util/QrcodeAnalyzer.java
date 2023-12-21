package com.github.yohannestz.cberemix.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.Objects;

public class QrcodeAnalyzer implements ImageAnalysis.Analyzer {

    private final BarcodeListener barcodeListener;

    public QrcodeAnalyzer(BarcodeListener barcodeListener) {
        this.barcodeListener = barcodeListener;
    }

    @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
    @Override
    public void analyze(ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            BarcodeScannerOptions barcodeScannerOptions = new BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                    .enableAllPotentialBarcodes()
                    .build();

            BarcodeScanner scanner = BarcodeScanning.getClient(barcodeScannerOptions);

            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        Log.e("success: ", String.valueOf(barcodes.size()));
                        for (Barcode barcode : barcodes) {
                            barcodeListener.onBarcodeDetected(barcode.getRawValue() != null ? barcode.getRawValue() : "");
                            imageProxy.close();
                        }
                    })
                    .addOnFailureListener(exception -> {
                        Log.e("failure: ", exception.toString());
                        imageProxy.close();
                    })
                    .addOnCompleteListener(task -> {
                        Log.e("complete: ", task.toString());
                        imageProxy.close();
                    });
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public interface BarcodeListener {
        void onBarcodeDetected(String barcode);
    }
}