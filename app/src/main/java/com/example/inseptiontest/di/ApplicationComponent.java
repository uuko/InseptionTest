package com.example.inseptiontest.di;

import android.app.Application;

import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.preference.LoginPreferencesProvider;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                APIModule.class,
        }
)

public interface ApplicationComponent {

    void inject(Application application);

    APIService getAPI();

    CompositeDisposable getCompositeDisposable();

    LoginPreferencesProvider getLoginPreferencesProvider();

    SchedulerProvider getSchedulerProvider();


}
