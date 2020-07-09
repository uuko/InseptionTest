package com.example.inseptiontest.base;

import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.BaseAttatcher;
import com.example.inseptiontest.base.BaseView;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> implements BaseAttatcher<V> {


    private V view;
    private APIService api;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    @Inject
    public BasePresenter(APIService api, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void onAttatched(V view) {
        this.view=view;
    }

    @Override
    public void onDetached() {
        this.view=null;
    }


    public V getView(){
        return view;
    }

    public APIService getApiService(){
        return api;
    }

    public SchedulerProvider getSchedulerProvider(){
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }



}
