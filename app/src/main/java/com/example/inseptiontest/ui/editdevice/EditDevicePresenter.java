package com.example.inseptiontest.ui.editdevice;

import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.BasePresenter;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EditDevicePresenter<V extends EditDeviceContract.View> extends BasePresenter<V>implements EditDeviceContract.Presenter<V> {

    @Inject
    public EditDevicePresenter(APIService api, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(api, schedulerProvider, compositeDisposable);
    }
}
