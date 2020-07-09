package com.example.inseptiontest.ui.editdevice;

import com.example.inseptiontest.di.ApplicationComponent;

import dagger.Component;

@EditDeviceScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                EditDeviceModule.class
        }
)
public interface EditDeviceComponent {
    void inject(EditDeviceActivity editDeviceActivity);
}
