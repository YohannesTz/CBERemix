package com.github.yohannestz.cberemix.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.data.Service;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> showText;
    private MutableLiveData<List<Service>> servicesLiveData;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        showText = new MutableLiveData<>();
        mText.setValue("5000.0 ETB");
        showText.setValue(false);
    }

    public LiveData<List<Service>> getServices() {
        if (servicesLiveData == null) {
            servicesLiveData = new MutableLiveData<>();
            loadServices();
        }
        return servicesLiveData;
    }

    private void loadServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("Service 1", R.drawable.ic_folder));
        services.add(new Service("Service 2", R.drawable.ic_folder));
        services.add(new Service("Service 3", R.drawable.ic_folder));
        services.add(new Service("Service 4", R.drawable.ic_folder));
        services.add(new Service("Service 5", R.drawable.ic_folder));
        servicesLiveData.setValue(services);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getShowText() {
        return showText;
    }
}