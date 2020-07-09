package com.example.inseptiontest.ui.main;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inseptiontest.Application;
import com.google.gson.annotations.SerializedName;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private AppCompatActivity activity;

    public MainModule(AppCompatActivity activity) {
        this.activity = activity;
    }



    @MainScope
    @Provides
    Context provideContext(){
        return activity;
    }


    @MainScope
    @Provides
    AppCompatActivity provideActivity(){
        return activity;
    }

    @MainScope
    @Provides
    MainContract.Presenter<MainContract.View> providePresenter(MainPresenter<MainContract.View> presenter){
        return presenter;
    }

}
