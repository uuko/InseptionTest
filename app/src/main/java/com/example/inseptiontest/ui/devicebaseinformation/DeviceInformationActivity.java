package com.example.inseptiontest.ui.devicebaseinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.inseptiontest.R;
import com.example.inseptiontest.base.BaseActivity;
import com.example.inseptiontest.databinding.ActivityDeviceInformationBinding;
import com.example.inseptiontest.ui.main.ChooseDeviceItemData;

import static com.example.inseptiontest.ui.main.MainActivity.DEVICE;

public class DeviceInformationActivity extends BaseActivity implements DeviceBaseInformContract.View{

    private ActivityDeviceInformationBinding activityDeviceInformationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_information);
        init();
    }

    private void init() {
        ChooseDeviceItemData chooseDeviceItemData=(ChooseDeviceItemData) getIntent().getExtras().getSerializable(DEVICE);
        activityDeviceInformationBinding= DataBindingUtil.setContentView(this,R.layout.activity_device_information);
        activityDeviceInformationBinding.setView(this);
        activityDeviceInformationBinding.setData(chooseDeviceItemData);
    }

    @Override
    public void onBack() {
        finish();
    }
}
