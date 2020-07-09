package com.example.inseptiontest.ui.editdevice;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class EditDeviceModule {

    private AppCompatActivity activity;

    public EditDeviceModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @EditDeviceScope
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    @EditDeviceScope
    Context provideContext(){
        return activity;
    }

    @Provides
    @EditDeviceScope
    EditDeviceContract.Presenter<EditDeviceContract.View> providePresenter(EditDevicePresenter<EditDeviceContract.View> presenter){
        return presenter;
    }
}
