package com.example.inseptiontest.ui.main;

import android.util.Log;

import com.example.inseptiontest.R;
import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.AddChkInfoResponse;
import com.example.inseptiontest.base.BasePresenter;
import com.example.inseptiontest.base.DisposableTokenResponse;
import com.example.inseptiontest.base.preference.LoginPreferencesProvider;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainPresenter<V extends  MainContract.View> extends BasePresenter<V> implements MainContract.Presenter<V>{

    @Inject
    public MainPresenter(APIService api, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(api, schedulerProvider, compositeDisposable);
    }

    @Inject
    LoginPreferencesProvider loginPreferencesProvider;

    private String disposableToken;

    @Override
    public void AddChkInfo(final ChooseDeviceItemData mChooseDeviceItemData) {
        Log.d("tagonPresenter", "onAddChkInfo: "+mChooseDeviceItemData.getEQNO());
        String authorizedId ="e1569364-6066-48af-8f47-8f11bb4916dd";
        AddChkInfoRequest mAddChkInfoRequest=new AddChkInfoRequest(authorizedId,
                mChooseDeviceItemData.getCO(),
                mChooseDeviceItemData.getMNTFCT(),
                mChooseDeviceItemData.getWAYID(),
                mChooseDeviceItemData.getWAYNM(),
                mChooseDeviceItemData.getCO(),
                mChooseDeviceItemData.getCONM(),
                mChooseDeviceItemData.getPMFCT(),
                mChooseDeviceItemData.getPMFCTNM(),
                mChooseDeviceItemData.getEQNO(),
                mChooseDeviceItemData.getUploadEMP(),
                mChooseDeviceItemData.getUploadNM(),
                mChooseDeviceItemData.getRecordDate());
        String url = getView().getResourceString(R.string.api_on_AddChkInfo);
        getCompositeDisposable().add(getApiService().onAddChkInfo(url, mAddChkInfoRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<AddChkInfoResponse>() {

                    @Override
                    public void onNext(AddChkInfoResponse addChkInfoResponse) {
                        Log.e("ggggg",""+addChkInfoResponse.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("gggg","error:"+e);
                        AddChkInfo(mChooseDeviceItemData);
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    @Override
    public void DisposableToken(String deviceid) {
        String authorizedId ="fec40e7e-48c2-4226-81ca-5044b72a8e1f";
        String url = getView().getResourceString(R.string.api_on_DisposableToken);
        DisposableTokenRequest mDisposableTokenRequest=new DisposableTokenRequest(authorizedId,loginPreferencesProvider.getToken(),deviceid);
        getCompositeDisposable().add(getApiService().onDisposableToken(url, mDisposableTokenRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<DisposableTokenResponse>() {
                    @Override
                    public void onNext(DisposableTokenResponse disposableTokenResponse) {

                        disposableToken = disposableTokenResponse.getmDisposableToken();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err","ERROR：" + e);

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    @Override
    public String getDisposableToken(){
        return disposableToken;
    }
}
