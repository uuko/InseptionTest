package com.example.inseptiontest;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.inseptiontest.di.APIModule;
import com.example.inseptiontest.di.ApplicationComponent;
import com.example.inseptiontest.di.ApplicationModule;
import com.example.inseptiontest.di.DaggerApplicationComponent;


public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //app大小超過65k用自定義的東西就要用
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .aPIModule(new APIModule())
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }


}
