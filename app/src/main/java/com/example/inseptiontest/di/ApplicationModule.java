package com.example.inseptiontest.di;

import android.app.Application;
import android.content.Context;

import com.example.inseptiontest.base.preference.LoginPreferences;
import com.example.inseptiontest.base.preference.LoginPreferencesProvider;
import com.example.inseptiontest.base.scheduler.SchedulerImp;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext(){
        return application.getApplicationContext();
    }

   @Provides
   public Application provideApplication(){
        return application;
   }

   @Provides
   public SchedulerProvider provideSchedulerProvider(SchedulerImp schedulerImp){
        return schedulerImp;
   }

   @Provides
   public CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
   }

   @Provides
   public Observable<Object> provideUIObservable(SchedulerProvider schedulerProvider){
       return Observable.create(new ObservableOnSubscribe<Object>() {
           @Override
           public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
               e.onComplete();
           }
       }).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui());
   }

   @Provides
   @Singleton
    public LoginPreferencesProvider provideLoginPreferenceProvider(LoginPreferences loginPreferences){
        return loginPreferences;
   }
}
