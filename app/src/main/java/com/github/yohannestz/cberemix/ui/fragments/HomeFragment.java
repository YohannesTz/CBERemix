package com.github.yohannestz.cberemix.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.databinding.FragmentHomeBinding;
import com.github.yohannestz.cberemix.ui.adapters.ServiceAdapter;
import com.github.yohannestz.cberemix.ui.viewmodels.HomeViewModel;
import com.github.yohannestz.cberemix.util.SpacesItemDecoration;
import com.github.yohannestz.cberemix.util.Utils;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough().setDuration(500));
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().show();
        }
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navdrawer);

        binding.bankAccountText.setTextOn(homeViewModel.getText().getValue());
        Log.e("showText: ", Objects.requireNonNull(homeViewModel.getShowText().getValue()).toString());
        binding.bankAccountText.setTextOff("************");
        binding.bankAccountText.setIsOn(Boolean.TRUE.equals(homeViewModel.getShowText().getValue()));
        binding.moneyToggleBtn.setOnClickListener(v -> {
            binding.bankAccountText.toggle();
        });

        RecyclerView recyclerView = binding.servicesList;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = Utils.dpToPx(0);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        ServiceAdapter serviceAdapter = new ServiceAdapter();
        serviceAdapter.setOnClickListener((position, model, v) -> {
            MaterialContainerTransform transform = new MaterialContainerTransform();
            transform.setDuration(500);
            transform.setScrimColor(Color.TRANSPARENT);

            navController.navigate(R.id.action_nav_home_to_serviceDetailFragment);
        });
        recyclerView.setAdapter(serviceAdapter);
        homeViewModel.getServices().observe(getViewLifecycleOwner(), serviceAdapter::setData);

        binding.sendMoney.setOnClickListener(v -> {
            MaterialContainerTransform transform = new MaterialContainerTransform();
            transform.setDuration(500);
            transform.setScrimColor(Color.TRANSPARENT);

            navController.navigate(R.id.action_nav_home_to_sendMoneyFragment);
        });

        binding.recieveMoney.setOnClickListener(v -> {
            ReceiveMoneyDialogFragment receiveMoneyDialogFragment = new ReceiveMoneyDialogFragment();
            if (activity != null) {
                receiveMoneyDialogFragment.show(activity.getSupportFragmentManager(), receiveMoneyDialogFragment.TAG);
                //activity.getWindow().setNavigationBarColor(SurfaceColors.SURFACE_4.getColor(activity.getApplicationContext()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}