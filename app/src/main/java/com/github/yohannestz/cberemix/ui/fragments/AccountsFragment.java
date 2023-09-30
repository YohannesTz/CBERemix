package com.github.yohannestz.cberemix.ui.fragments;

import static com.patrykandpatrick.vico.core.entry.ChartEntryExtensionsKt.entriesOf;
import static com.patrykandpatrick.vico.core.entry.EntryListExtensionsKt.entryModelOf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.databinding.FragmentAccountsBinding;
import com.patrykandpatrick.vico.core.entry.ChartEntryModel;

public class AccountsFragment extends Fragment {
    private FragmentAccountsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        ChartEntryModel chartEntryModel = entryModelOf(entriesOf(4f, 12f, 8f, 16f), entriesOf(12f, 16f, 4f, 12f));
        binding.chartView.setModel(chartEntryModel);
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