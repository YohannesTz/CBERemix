package com.github.yohannestz.cberemix.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.github.yohannestz.cberemix.databinding.RecieveDialogLayoutBinding;
import com.github.yohannestz.cberemix.util.QRCodeGenerator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.elevation.SurfaceColors;

import java.util.Objects;

public class ReceiveMoneyDialogFragment extends BottomSheetDialogFragment {

    private RecieveDialogLayoutBinding binding;
    private String qrDataText = "https://cbe.com/receive?account=100003450&amount=0";
    final String TAG = "com.github.yohannestz.cberemix.ui.fragments.ReceiveMoneyDialogFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RecieveDialogLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Enable scrim
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setNavigationBarColor(SurfaceColors.SURFACE_1.getColor(requireContext()));
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }

        binding.bankAccountNumber.setTextOff("************");
        binding.bankAccountNumber.setTextOn("100000340560");
        binding.bankAccountNumber.setOnClickListener(v -> {
            binding.bankAccountNumber.toggle();
        });
        binding.amountEditText.setText("10");
        binding.qrImageView.setImageDrawable(generateQr(qrDataText));

        binding.amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String payload = Objects.requireNonNull(binding.amountEditText.getText()).toString();
                binding.qrImageView.setImageDrawable(generateQr(payload));
            }
        });

        binding.shareLink.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CBE Payment Request");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "CBE Payment Request \n" + qrDataText);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private Drawable generateQr(String payload) {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(getContext());
        qrDataText = "https://cbe.com/receive?account=100003450&amount=" + payload;
        return qrCodeGenerator.drawableFromText(qrDataText);
    }
}
