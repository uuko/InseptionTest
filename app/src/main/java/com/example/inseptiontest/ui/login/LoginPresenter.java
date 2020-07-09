package com.example.inseptiontest.ui.login;

import com.example.inseptiontest.base.BasePresenter;
import com.example.inseptiontest.R;
import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.LoginResponse;
import com.example.inseptiontest.base.preference.LoginPreferencesProvider;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class LoginPresenter<V extends  LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V> {


    private String AuthorizedId="acd9be92-46bf-4185-8721-5b60c67f0742";
    private String AccessToken;
    @Inject
    LoginPreferencesProvider loginPreferencesProvider;

    @Inject
    public LoginPresenter(APIService api, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(api, schedulerProvider, compositeDisposable);
    }

    //自動登入
    @Override
    public void onAutoLogin(final String account,final String password) {
        getView().showProgressDialog(R.string.auto_loading);
        String url=getView().getResourceString(R.string.api_accesstoken);

        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setAuthorizedId(AuthorizedId);
        loginRequest.setNotesId(account);
        loginRequest.setPassword(password);

        getCompositeDisposable().add(getApiService().onLogin(url,loginRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<LoginResponse>() {

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if ("true".equals(loginResponse.getmResult())) {
                            AccessToken = loginResponse.getmToken();
                            loginPreferencesProvider.setToken(loginResponse.getmToken());
                            loginPreferencesProvider.setAccount(account);
                            getView().dismissProgressDialog();
                            getView().getAutoLoginResponse(account);
                        } else {
                            getView().showDialogInspection("自動登入失敗");
                            getView().dismissProgressDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissProgressDialog();
                        getView().showDialogInspection("自動登入失敗");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void onLogin(final LoginModel loginModel) {
        getView().showProgressDialog(R.string.login_loading);
        String url=getView().getResourceString(R.string.api_accesstoken);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setNotesId(loginModel.getAccount());
        loginRequest.setPassword(loginModel.getPassword());
        loginRequest.setAuthorizedId(AuthorizedId);
        final String account=loginModel.getAccount();
        getCompositeDisposable().add(getApiService().onLogin(url,loginRequest)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribeWith(new DisposableObserver<LoginResponse>() {
                @Override
                public void onNext(LoginResponse loginResponse) {
                    if ("true".equals(loginResponse.getmResult())) {
                        AccessToken = loginResponse.getmToken();
                        loginPreferencesProvider.setToken(loginResponse.getmToken());
                        loginPreferencesProvider.setAccount(account);
                        getView().dismissProgressDialog();
                        getView().getLoginResponse();
                    } else {
                        getView().showDialogInspection("自動登入失敗");
                        getView().dismissProgressDialog();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    getView().dismissProgressDialog();
                    getView().showDialogInspection("登入失敗");
                }

                @Override
                public void onComplete() {

                }
            }));


    }

    public String getAccessToken(){
        return AccessToken;
    }

}
