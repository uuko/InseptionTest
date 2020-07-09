package com.example.inseptiontest.ui.adddevice;

import com.example.inseptiontest.di.ApplicationComponent;
import com.example.inseptiontest.di.ApplicationModule;

import dagger.Component;

@AddDeviceScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                AddDeviceModule.class
        }
)
public interface AddDeviceComponent {
    void inject(AddDeviceActivity addDeviceActivity);
}
