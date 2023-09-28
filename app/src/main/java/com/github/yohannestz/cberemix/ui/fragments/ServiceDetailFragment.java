package com.github.yohannestz.cberemix.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.databinding.FragmentHomeBinding;
import com.github.yohannestz.cberemix.databinding.FragmentServiceDetailBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialFadeThrough;

public class ServiceDetailFragment extends Fragment {

    private FragmentServiceDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceDetailBinding.inflate(inflater, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        /*    binding.serviceDetailToolBar.getMenu().clear();
            activity.setSupportActionBar(binding.serviceDetailToolBar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);*/
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
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