package com.example.inseptiontest.ui.login;

import com.example.inseptiontest.base.BaseAttatcher;
import com.example.inseptiontest.base.BaseView;

public interface LoginContract {
    interface View extends BaseView{
        void onLoginClick();
        void getLoginResponse();
        void getAutoLoginResponse(String account);

    }

    interface Presenter<V extends BaseView> extends BaseAttatcher<V>{
        void onAutoLogin(String account,String password);
        void onLogin(LoginModel loginModel);
        String getAccessToken();
    }
}
