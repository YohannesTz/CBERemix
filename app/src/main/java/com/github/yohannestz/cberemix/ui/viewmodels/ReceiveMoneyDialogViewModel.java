package com.github.yohannestz.cberemix.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiveMoneyDialogViewModel extends ViewModel {
    private final MutableLiveData<String> qrPayLoad;

    public ReceiveMoneyDialogViewModel() {
        qrPayLoad = new MutableLiveData<>();
        qrPayLoad.setValue("100");
    }

    public LiveData<String> getQrPayload() {
        return qrPayLoad;
    }

    public void setQrPayLoad(String string) {
        qrPayLoad.setValue(string);
    }
}
