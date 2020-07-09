package com.example.inseptiontest.ui.editdevice;

import com.example.inseptiontest.base.BaseAttatcher;
import com.example.inseptiontest.base.BaseView;

public interface EditDeviceContract {
    interface View extends BaseView{

        void onAddDeviceClick();

    }

    interface Presenter<V extends  View> extends BaseAttatcher<V>{

    }
}
