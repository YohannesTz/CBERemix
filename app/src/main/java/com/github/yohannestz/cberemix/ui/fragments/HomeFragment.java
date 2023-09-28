package com.github.yohannestz.cberemix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.data.Service;
import com.github.yohannestz.cberemix.databinding.FragmentHomeBinding;
import com.github.yohannestz.cberemix.ui.adapters.ServiceAdapter;
import com.github.yohannestz.cberemix.ui.viewmodels.HomeViewModel;
import com.github.yohannestz.cberemix.util.SpacesItemDecoration;
import com.github.yohannestz.cberemix.util.Utils;

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
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_navdrawer);

        RecyclerView recyclerView = binding.servicesList;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = Utils.dpToPx(0);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        ServiceAdapter serviceAdapter = new ServiceAdapter();
        serviceAdapter.setOnClickListener((position, model) -> {
            navController.navigate(R.id.action_nav_home_to_serviceDetailFragment);
        });
        recyclerView.setAdapter(serviceAdapter);
        homeViewModel.getServices().observe(getViewLifecycleOwner(), serviceAdapter::setData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}