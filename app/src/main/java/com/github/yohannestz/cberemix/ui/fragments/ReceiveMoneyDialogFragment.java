package com.github.yohannestz.cberemix.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.alexzhirkevich.customqrgenerator.QrData;
import com.github.alexzhirkevich.customqrgenerator.style.BitmapScale;
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawableKt;
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions;
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
import com.github.yohannestz.cberemix.databinding.RecieveDialogLayoutBinding;
import com.github.yohannestz.cberemix.util.QRCodeGenerator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import kotlin.Pair;

public class ReceiveMoneyDialogFragment extends BottomSheetDialogFragment {

    private RecieveDialogLayoutBinding binding;
    final String TAG = "com.github.yohannestz.cberemix.ui.fragments.ReceiveMoneyDialogFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RecieveDialogLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        if (activity != null) {
            activity.getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(activity.getApplicationContext()));
        }

        binding.bankAccountNumber.setTextOff("************");
        binding.bankAccountNumber.setTextOn("100000340560");
        binding.bankAccountNumber.setOnClickListener(v -> {
            binding.bankAccountNumber.toggle();
        });
        binding.amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(getContext());
                String payload = Objects.requireNonNull(binding.amountEditText.getText()).toString();
                Drawable qrDrawable = qrCodeGenerator.drawableFromText(payload);
                binding.imageView3.setImageDrawable(qrDrawable);
            }
        });

    }
}
