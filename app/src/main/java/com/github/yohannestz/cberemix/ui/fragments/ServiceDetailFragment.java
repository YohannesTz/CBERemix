package com.github.yohannestz.cberemix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.databinding.FragmentServiceDetailBinding;
import com.github.yohannestz.cberemix.ui.adapters.ServiceAdapter;
import com.github.yohannestz.cberemix.ui.viewmodels.ServiceDetailViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialContainerTransform;

public class ServiceDetailFragment extends Fragment {

    private FragmentServiceDetailBinding binding;
    private ServiceDetailViewModel serviceDetailViewModel;
    private NavController bottomNavController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        serviceDetailViewModel = new ViewModelProvider(this).get(ServiceDetailViewModel.class);
        binding = FragmentServiceDetailBinding.inflate(inflater, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = binding.serviceDetailList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ServiceAdapter serviceAdapter = new ServiceAdapter();
        serviceAdapter.setOnClickListener((position, model, v) -> {
            Snackbar.make(v, "List clicked!", Snackbar.LENGTH_LONG).show();
        });
        recyclerView.setAdapter(serviceAdapter);
        serviceDetailViewModel.getServices().observe(getViewLifecycleOwner(),serviceAdapter::setData);

        if (getActivity() != null) {
            bottomNavController = Navigation.findNavController(getActivity(),  R.id.nav_host_fragment_content_navdrawer);
            binding.backButton.setOnClickListener(view -> {
                bottomNavController.popBackStack();
            });
        }
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