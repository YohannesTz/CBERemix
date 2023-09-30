package com.github.yohannestz.cberemix.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.yohannestz.cberemix.databinding.FragmentRecentsBinding;
import com.google.android.material.transition.MaterialContainerTransform;

public class RecentsFragment extends Fragment {
    private FragmentRecentsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.toggleTextView.setTextOn("Your Secret Text.");
        binding.toggleTextView.setTextOff("**************");
        binding.toggleBtn.setOnClickListener(v -> {
            binding.toggleTextView.toggle();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialContainerTransform().setDuration(2500));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}