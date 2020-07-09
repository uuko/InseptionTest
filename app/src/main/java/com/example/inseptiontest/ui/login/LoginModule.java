package com.example.inseptiontest.ui.login;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private AppCompatActivity activity;

    public LoginModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @LoginScope
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    @LoginScope
    Context provideContext(){
        return activity;
    }

    @Provides
    @LoginScope
    LoginContract.Presenter<LoginContract.View> providePresenter(LoginPresenter<LoginContract.View> presenter){
        return presenter;

    }
}
