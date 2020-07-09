package com.example.inseptiontest.ui.editdevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.inseptiontest.Application;
import com.example.inseptiontest.R;
import com.example.inseptiontest.base.BaseActivity;
import com.example.inseptiontest.databinding.ActivityEditDeviceBinding;
import com.example.inseptiontest.ui.adddevice.AddDeviceActivity;

import javax.inject.Inject;

public class EditDeviceActivity extends BaseActivity implements EditDeviceContract.View {

    private EditDeviceComponent editDeviceComponent;

    private ActivityEditDeviceBinding activityEditDeviceBinding;

    @Inject
    EditDeviceContract.Presenter<EditDeviceContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        init();
        presenter.onAttatched(this);

    }

    private void init() {

        editDeviceComponent=DaggerEditDeviceComponent.builder()
                .applicationComponent(((Application)getApplicationContext()).getApplicationComponent())
                .editDeviceModule(new EditDeviceModule(this))
                .build();
        editDeviceComponent.inject(this);

        activityEditDeviceBinding= DataBindingUtil.setContentView(this,R.layout.activity_edit_device);
        activityEditDeviceBinding.setView(this);

    }

    @Override
    public void onAddDeviceClick() {
        Intent addIntent=new Intent(this, AddDeviceActivity.class);
        startActivity(addIntent);
    }
}
