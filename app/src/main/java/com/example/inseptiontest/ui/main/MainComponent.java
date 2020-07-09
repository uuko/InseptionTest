package com.example.inseptiontest.ui.main;

import com.example.inseptiontest.di.ApplicationComponent;

import dagger.Component;

@MainScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                MainModule.class
        }
)
public interface MainComponent {
    void  inject(MainActivity activity);
}
