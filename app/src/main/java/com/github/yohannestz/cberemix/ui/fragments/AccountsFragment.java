package com.github.yohannestz.cberemix.ui.fragments;

import static com.patrykandpatrick.vico.core.entry.ChartEntryExtensionsKt.entriesOf;
import static com.patrykandpatrick.vico.core.entry.EntryListExtensionsKt.entryModelOf;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.yohannestz.cberemix.databinding.FragmentAccountsBinding;
import com.github.yohannestz.cberemix.ui.viewmodels.AccountsViewModel;
import com.patrykandpatrick.vico.core.chart.line.LineChart;

import java.util.Objects;

public class AccountsFragment extends Fragment {
    private FragmentAccountsBinding binding;
    private AccountsViewModel accountsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountsViewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.chartView.setModel(Objects.requireNonNull(accountsViewModel.getEntryModelList().getValue()));
        binding.chartView.setLegend(accountsViewModel.getLegends("Deposit", "Withdrawal"));
        binding.chartView.animate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}