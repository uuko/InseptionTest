package com.example.inseptiontest.ui.adddevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.example.inseptiontest.Application;
import com.example.inseptiontest.R;
import com.example.inseptiontest.base.BaseActivity;
import com.example.inseptiontest.base.COResponse;
import com.example.inseptiontest.base.MNTFCTResponse;
import com.example.inseptiontest.base.PMFCTResponse;
import com.example.inseptiontest.databinding.ActivityAddDeviceBinding;
import com.example.inseptiontest.ui.main.ChooseDeviceItemData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class AddDeviceActivity extends BaseActivity implements  AddDeviceContract.View {

    @Inject
    AddDeviceContract.Presenter<AddDeviceContract.View> presenter;

    private AddDeviceListData addDeviceListData;
    private AddDeviceComponent addDeviceComponent;
    private ActivityAddDeviceBinding activityAddDeviceBinding;
    private AddDeviceData addDeviceData;
    private CharSequence date;
    private List<String> dialogStringList;
    private ChooseDeviceItemData mChooseDeviceItemData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        init();
        presenter.onAttatched(this);
    }

    private void init() {
        addDeviceComponent=DaggerAddDeviceComponent.builder()
                .applicationComponent((((Application)getApplicationContext()).getApplicationComponent()))
                .build();
        addDeviceComponent.inject(this);

        activityAddDeviceBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_device);
        activityAddDeviceBinding.setView(this);

        addDeviceData=new AddDeviceData();
        //date
        Calendar calendar=Calendar.getInstance();
        date= DateFormat.format("yyyy/MM/dd",calendar);
        addDeviceData.setDate(date);
        dialogStringList=new ArrayList<>();
        mChooseDeviceItemData=new ChooseDeviceItemData();
        addDeviceListData=new AddDeviceListData();

    }

    @Override
    public void OnMaintenancePlantClick() {
        presenter.SearchMNTFCT();
    }

    @Override
    public void OnCompanyClick() {
        presenter.SearchCO();
    }

    @Override
    public void onProduceFactoryClick() {
        if ((addDeviceData.getMntfctnm().length()==0 )|| (addDeviceData.getCompany().length()==0)){
            showDialogInspection("請選擇保養廠及公司");
        }else {
            presenter.SearchPMFCT(mChooseDeviceItemData.getMNTCO(),mChooseDeviceItemData.getMNTFCT());
        }
    }

    @Override
    public void setMNTFCTData(List<MNTFCTResponse> mntfctData) {
        dialogStringList.clear();
        for (MNTFCTResponse mntfctResponse:mntfctData){
            dialogStringList.add(mntfctResponse.getMNTFCTNM());
        }
        addDeviceListData.setmMNTFCTDataList(mntfctData);
        showItemDialog(dialogStringList,onMaintenanceDialogItemClick);

    }

    private DialogInterface.OnClickListener onMaintenanceDialogItemClick=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            addDeviceData.setMntfctnm(addDeviceListData.getmMNTFCTDataList().get(which).getMNTFCTNM());
            mChooseDeviceItemData.setMNTFCTNM(addDeviceListData.getmMNTFCTDataList().get(which).getMNTFCTNM());
            mChooseDeviceItemData.setMNTCO(addDeviceListData.getmMNTFCTDataList().get(which).getMNTCO());
            mChooseDeviceItemData.setMNTFCT(addDeviceListData.getmMNTFCTDataList().get(which).getMNTFCT());
        }
    };

    @Override
    public void setCOData(List<COResponse> coData) {
        dialogStringList.clear();
        addDeviceListData.setmCODataList(coData);

        for(COResponse mCOResponse: coData){
            dialogStringList.add(mCOResponse.getCO());
        }
        showItemDialog(dialogStringList,onCompanyDialogItemClick);
    }

    @Override
    public void setPMFCT(List<PMFCTResponse> pmfctResponseList) {
        dialogStringList.clear();
        addDeviceListData.setmPMFCTDataList(pmfctResponseList);
        for(PMFCTResponse mPMFCTResponse: pmfctResponseList){
            dialogStringList.add(mPMFCTResponse.getPMFCTNM());
        }
        showItemDialog(dialogStringList,onProductionDialogItemClick);

    }
    private DialogInterface.OnClickListener onProductionDialogItemClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            addDeviceData.setPmfctn(addDeviceListData.getmPMFCTDataList().get(which).getPMFCTNM());
            mChooseDeviceItemData.setPMFCTNM(addDeviceListData.getmPMFCTDataList().get(which).getPMFCTNM());
            mChooseDeviceItemData.setPMFCT(addDeviceListData.getmPMFCTDataList().get(which).getPMFCTNM());
        }
    };
    private DialogInterface.OnClickListener onCompanyDialogItemClick=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            addDeviceData.setCompany(addDeviceListData.getmCODataList().get(which).getCONM());
            mChooseDeviceItemData.setCONM(addDeviceListData.getmCODataList().get(which).getCONM());
            mChooseDeviceItemData.setCO(addDeviceListData.getmCODataList().get(which).getCO());
        }
    };


}
