package com.example.inseptiontest.ui.adddevice;

import com.example.inseptiontest.base.COResponse;
import com.example.inseptiontest.base.EQKDResponse;
import com.example.inseptiontest.base.EQNOResponse;
import com.example.inseptiontest.base.MNTFCTResponse;
import com.example.inseptiontest.base.PMFCTResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddDeviceListData implements Serializable {
    private List<COResponse> mCODataList;
    private List<EQKDResponse> mEQKDDataList;
    private List<EQNOResponse> mEQNODataList;
    private List<MNTFCTResponse> mMNTFCTDataList;
    private List<PMFCTResponse> mPMFCTDataList;

    public List<COResponse> getmCODataList() {
        return mCODataList;
    }

    public void setmCODataList(List<COResponse> mCODataList) {
        this.mCODataList = mCODataList;
    }


    public List<MNTFCTResponse> getmMNTFCTDataList() {
        return mMNTFCTDataList;
    }

    public void setmMNTFCTDataList(List<MNTFCTResponse> mMNTFCTDataList) {
        this.mMNTFCTDataList = mMNTFCTDataList;
    }

    public List<EQKDResponse> getmEQKDDataList() {
        return mEQKDDataList;
    }

    public void setmEQKDDataList(List<EQKDResponse> mEQKDDataList) {
        this.mEQKDDataList = mEQKDDataList;
    }

    public List<EQNOResponse> getmEQNODataList() {
        return mEQNODataList;
    }

    public void setmEQNODataList(List<EQNOResponse> mEQNODataList) {
        this.mEQNODataList = mEQNODataList;
    }

    public List<PMFCTResponse> getmPMFCTDataList() {
        return mPMFCTDataList;
    }

    public void setmPMFCTDataList(List<PMFCTResponse> mPMFCTDataList) {
        this.mPMFCTDataList = mPMFCTDataList;
    }
}
