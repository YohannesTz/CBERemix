package com.github.yohannestz.cberemix.ui.viewmodels;


import static com.patrykandpatrick.vico.core.entry.ChartEntryExtensionsKt.entriesOf;
import static com.patrykandpatrick.vico.core.entry.EntryListExtensionsKt.entryModelOf;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.patrykandpatrick.vico.core.entry.ChartEntryModel;

import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends ViewModel {


    public LiveData<ChartEntryModel> getEntryModelList() {
        ChartEntryModel chartEntryModel = entryModelOf(
                entriesOf(4f, 12f, 8f, 16f),
                entriesOf(12f, 16f, 4f, 12f));
        return (LiveData<ChartEntryModel>) chartEntryModel;
    }

}
