package com.example.inseptiontest.base;

import com.example.inseptiontest.ui.login.LoginRequest;
import com.example.inseptiontest.ui.main.AddChkInfoRequest;
import com.example.inseptiontest.ui.main.DisposableTokenRequest;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIService {

    @POST
    Observable<COResultList> onSearchCO(@Url String url,@Body CORequest coRequest);
    @POST
    Observable<MNTFCTResultList> onSearchMNTFCT(@Url String url,@Body MNFCTRequest coManufatureRequest);

    @POST
    Observable<PMFCTResultList> onSearchPMFCT(@Url String url,@Body PMFCTRequest pmfctRequest);

    @POST
    Observable<EQKDResultList> onSearchEQKD(@Url String url, @Body EQKDRequest mEQKDRequest);
    @POST
    Observable<EQNOResultList> onSearchEQNO(@Url String url, @Body EQNORequest mEQNORequest);

    @POST
    Observable<AddChkInfoResponse> onAddChkInfo(@Url String url, @Body AddChkInfoRequest addChkInfoRequest);
    @POST
    Observable<DisposableTokenResponse> onDisposableToken(@Url String url, @Body DisposableTokenRequest mDisposableTokenRequest);
    @POST
    Observable<LoginResponse> onLogin(@Url String url, @Body LoginRequest loginRequest);
}
