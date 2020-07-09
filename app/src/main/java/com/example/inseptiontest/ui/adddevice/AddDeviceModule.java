package com.example.inseptiontest.ui.adddevice;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inseptiontest.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AddDeviceModule {

    private AppCompatActivity activity;

    public AddDeviceModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @AddDeviceScope
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    @AddDeviceScope
    Context provideContext() {
        return activity;
    }

    @Provides
    @AddDeviceScope
    AddDeviceContract.Presenter<AddDeviceContract.View> providePresenter(
            AddDevicePresenter<AddDeviceContract.View> presenter
    ){
        return presenter;
    }
}
