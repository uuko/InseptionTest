package com.example.inseptiontest.ui.login;

import com.example.inseptiontest.di.ApplicationComponent;

import dagger.Component;

@LoginScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                LoginModule.class
        }
)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
