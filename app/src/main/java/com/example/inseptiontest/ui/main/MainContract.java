package com.example.inseptiontest.ui.main;

import com.example.inseptiontest.base.BaseAttatcher;
import com.example.inseptiontest.base.BaseView;

public interface MainContract {
    interface View extends BaseView{
        void onCenterCloud();
        void onDeviceChoose();
        void onFetchMessage();
        void onGetBaseInformation();
        void onDeviceEdit();
    }

    interface Presenter<V extends View> extends BaseAttatcher<V>{
        void AddChkInfo(ChooseDeviceItemData chooseDeviceItemData);
        void DisposableToken(String deviceid);
        String getDisposableToken();
    }
}
