package com.example.inseptiontest.ui.adddevice;

import com.example.inseptiontest.base.BaseAttatcher;
import com.example.inseptiontest.base.BaseView;
import com.example.inseptiontest.base.COResponse;
import com.example.inseptiontest.base.MNTFCTResponse;
import com.example.inseptiontest.base.MNTFCTResultList;
import com.example.inseptiontest.base.PMFCTResponse;

import java.util.List;

public interface AddDeviceContract {

    interface View extends BaseView{
        void OnMaintenancePlantClick();
        void OnCompanyClick();
        void onProduceFactoryClick();
        void setMNTFCTData(List<MNTFCTResponse> mntfctData);
        void setCOData(List<COResponse> coData);
        void setPMFCT(List<PMFCTResponse> pmfctResponseList);

    }

    interface Presenter<V extends View> extends BaseAttatcher<V>{
        void SearchMNTFCT();
        void SearchCO();
        void SearchPMFCT(String MNTCO,String MNTFCT);
//        void SearchEQKD();
//        void SearchEQNO();
    }
}
