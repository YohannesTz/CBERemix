package com.github.yohannestz.cberemix.ui.viewmodels;

import static com.patrykandpatrick.vico.core.entry.ChartEntryExtensionsKt.entriesOf;
import static com.patrykandpatrick.vico.core.entry.EntryListExtensionsKt.entryModelOf;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patrykandpatrick.vico.core.component.shape.ShapeComponent;
import com.patrykandpatrick.vico.core.component.text.TextComponent;
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions;
import com.patrykandpatrick.vico.core.entry.ChartEntryModel;
import com.patrykandpatrick.vico.core.entry.FloatEntry;
import com.patrykandpatrick.vico.core.legend.HorizontalLegend;
import com.patrykandpatrick.vico.core.legend.Legend;
import com.patrykandpatrick.vico.core.legend.LegendItem;

import java.util.ArrayList;
import java.util.List;

public class AccountsViewModel extends ViewModel {

    private final MutableLiveData<ChartEntryModel> chartEntryModelMutableLiveData;


    public AccountsViewModel() {
        chartEntryModelMutableLiveData = new MutableLiveData<>();
        ChartEntryModel chartEntryModel = entryModelOf(
                generateFloatEntries(5),
                generateFloatEntries(10)
        );
        chartEntryModelMutableLiveData.setValue(chartEntryModel);
    }

    public List<FloatEntry> generateFloatEntries(int dataSize) {
        List<FloatEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {
            float y = (float) (100 + Math.random() * (100f - 1000f));
            FloatEntry entry = new FloatEntry(i, y);
            entries.add(entry);
        }

        printAll(entries);
        return entries;
    }

    public void printAll(List<FloatEntry> entries) {
        for (FloatEntry entry: entries) {
            Log.e("floatEntry: ", entry.toString());
        }
    }

    public LiveData<ChartEntryModel> getEntryModelList() {
        return chartEntryModelMutableLiveData;
    }

    public Legend getLegends(String... names) {
        TextComponent textComponent = new TextComponent.Builder()
                .build();
        ShapeComponent shapeComponent = new ShapeComponent();
        List<LegendItem> legendItems = new ArrayList<>();

        for (String name: names) {
            legendItems.add(new LegendItem(shapeComponent, textComponent, name));
        }

        return new HorizontalLegend(
            legendItems, 10f,10f,10f, 10f, new MutableDimensions(10f, 10f)
        );
    }
}
