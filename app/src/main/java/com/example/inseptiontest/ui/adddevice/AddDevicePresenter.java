package com.example.inseptiontest.ui.adddevice;

import android.util.Log;

import com.example.inseptiontest.R;
import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.BasePresenter;
import com.example.inseptiontest.base.CORequest;
import com.example.inseptiontest.base.COResponse;
import com.example.inseptiontest.base.COResultList;
import com.example.inseptiontest.base.MNFCTRequest;
import com.example.inseptiontest.base.MNTFCTResultList;
import com.example.inseptiontest.base.PMFCTRequest;
import com.example.inseptiontest.base.PMFCTResponse;
import com.example.inseptiontest.base.PMFCTResultList;
import com.example.inseptiontest.base.preference.LoginPreferencesProvider;
import com.example.inseptiontest.base.scheduler.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class AddDevicePresenter<V extends AddDeviceContract.View> extends BasePresenter<V> implements AddDeviceContract.Presenter<V> {

    private String KEY_PMFCT="25d5cf12-a1aa-428b-8297-3dc042580e24";
    private String KEY_SearchCO="6c66fcbd-6dfe-45a2-ad6b-cbcda09b25bd";
    private String KEY_SearchMNTFCT="345972b6-d20f-43d8-8688-d253477a6b26";
    @Inject
    LoginPreferencesProvider loginPreferencesProvider;
    @Inject
    public AddDevicePresenter(APIService api, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(api, schedulerProvider, compositeDisposable);
    }

    @Override
    public void SearchMNTFCT() {
        getView().showProgressDialog(R.string.waiting_loading);
        String url=getView().getResourceString(R.string.api_on_SearchMNTFCT);
        MNFCTRequest mnfctRequest=new MNFCTRequest();
        mnfctRequest.setIdNo(KEY_SearchMNTFCT);
        mnfctRequest.setAuthorizedId(loginPreferencesProvider.getAccount());

        getCompositeDisposable().add(getApiService().onSearchMNTFCT(url,mnfctRequest)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribeWith(new DisposableObserver<MNTFCTResultList>() {
            @Override
            public void onNext(MNTFCTResultList mntfctResultList) {
                getView().setMNTFCTData(mntfctResultList.getmNTFCTResponse());
                getView().dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("mntfctResultList", "onError: "+e );
                getView().dismissProgressDialog();
                getView().showDialogInspection(getView().getResourceString(R.string.add_device_error));
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void SearchCO() {
        getView().showProgressDialog(R.string.waiting_loading);
        String url=getView().getResourceString(R.string.api_on_SearchCO);
        final CORequest coRequest=new CORequest();
        coRequest.setAuthorizedId(KEY_SearchCO);
        coRequest.setIdNo(loginPreferencesProvider.getAccount());
        getCompositeDisposable().add(getApiService().onSearchCO(url,coRequest)
        .observeOn(getSchedulerProvider().ui())
        .subscribeOn(getSchedulerProvider().io())
        .subscribeWith(new DisposableObserver<COResultList>() {
            @Override
            public void onNext(COResultList coResultList) {
                getView().dismissProgressDialog();
                getView().setCOData(coResultList.getResultList());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("coResultList", "onError: "+e );
                getView().dismissProgressDialog();
                getView().showDialogInspection(getView().getResourceString(R.string.add_device_error));

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void SearchPMFCT(String MNTCO, String MNTFCT) {
        getView().showProgressDialog(R.string.waiting_loading);
        String url=getView().getResourceString(R.string.api_on_SearchPMFCT);
        PMFCTRequest pmfctRequest=new PMFCTRequest();
        pmfctRequest.setIdNo(KEY_SearchMNTFCT);
        pmfctRequest.setAuthorizedId(loginPreferencesProvider.getAccount());
        pmfctRequest.setMNTCO(MNTCO);
        pmfctRequest.setMNTFCT(MNTFCT);;
        getCompositeDisposable().add(getApiService().onSearchPMFCT(url,pmfctRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<PMFCTResultList>() {
                    @Override
                    public void onNext(PMFCTResultList pmfctResultList) {
                        getView().dismissProgressDialog();
                        getView().setPMFCT(pmfctResultList.getResultList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("pmfResultList", "onError: "+e );
                        getView().dismissProgressDialog();
                        getView().showDialogInspection(getView().getResourceString(R.string.add_device_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


}
