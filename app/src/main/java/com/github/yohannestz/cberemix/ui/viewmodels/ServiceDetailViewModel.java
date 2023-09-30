package com.github.yohannestz.cberemix.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.data.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailViewModel extends ViewModel {

    private MutableLiveData<List<Service>> serviceDetailList;

    public LiveData<List<Service>> getServices() {
        if (serviceDetailList == null) {
            serviceDetailList = new MutableLiveData<>();
            loadServices();
        }
        return serviceDetailList;
    }

    private void loadServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("Detail Service 1", R.drawable.ic_folder));
        services.add(new Service("Detail Service 2", R.drawable.ic_folder));
        services.add(new Service("Detail Service 3", R.drawable.ic_folder));
        services.add(new Service("Detail Service 4", R.drawable.ic_folder));
        services.add(new Service("Detail Service 5", R.drawable.ic_folder));
        services.add(new Service("Detail Service 6", R.drawable.ic_folder));
        services.add(new Service("Detail Service 7", R.drawable.ic_folder));
        services.add(new Service("Detail Service 8", R.drawable.ic_folder));
        serviceDetailList.setValue(services);
    }

}
