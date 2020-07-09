package com.example.inseptiontest.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.inseptiontest.Application;
import com.example.inseptiontest.R;
import com.example.inseptiontest.base.BaseActivity;
import com.example.inseptiontest.databinding.ActivityMainBinding;
import com.example.inseptiontest.ui.devicebaseinformation.DeviceInformationActivity;
import com.example.inseptiontest.ui.editdevice.EditDeviceActivity;

import java.lang.annotation.Inherited;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.example.inseptiontest.ui.login.LoginActivity.Account;
import static com.example.inseptiontest.ui.login.LoginActivity.loginstatus;
import static com.example.inseptiontest.ui.main.ScheduleService.REFRESH;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainContract.Presenter<MainContract.View> presenter;

    private String deviceId=android.os.Build.SERIAL;
    private MainModel mainModel=new MainModel();
    public static  String DEVICE="DEVICE";
    private String account;
    public static String CURRENT_DATA_COUNT="CURRENT_DATA_COUNT";
    public static String DATASET="DATASET";
    private String AccessToken;
    //下一個設備,再開
    private int deviceonLeaveTheRoute=0;
    //在回傳會把值給他當作目前的值取資料設進去text
    private int currentCount;
    private ActivityMainBinding activityMainBinding;
    private MainComponent mainComponent;
    private List<ChooseDeviceItemData> deviceDataList=new ArrayList<>();
    private Intent scheduleServiceIntent;
    private IntentFilter broadcastIntentfilter;
    private boolean deviceWheatherInBefore=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        presenter.onAttatched(this);
        presenter.DisposableToken(deviceId);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, broadcastIntentfilter);
    }


    private void getInspectMainData() {
        String CONTENT_STRING = "content://tw.com.efpg.processe_equip.provider.ShareCloud/ShareCloud";
        Uri uri = Uri.parse(CONTENT_STRING);
        Cursor cursor=this.getContentResolver().query(
                 uri,
                null,
                "CurrentJob",
                null,null
        );
        if (cursor==null){
            showDialogInspection(getResourceString(R.string.get_inspection_data_error_message));
        }else {
            while (cursor.moveToNext()){
                String WAYID = cursor.getString(cursor.getColumnIndexOrThrow("WAYID"));
                String EQNO = cursor.getString(cursor.getColumnIndexOrThrow("EQNO"));
                ChooseDeviceItemData mChooseDeviceItemData = new ChooseDeviceItemData();
                mChooseDeviceItemData.setOPCO(cursor.getString(cursor.getColumnIndexOrThrow("OPCO")));
                mChooseDeviceItemData.setOPPLD(cursor.getString(cursor.getColumnIndexOrThrow("OPPLD")));
                mChooseDeviceItemData.setPMFCT(cursor.getString(cursor.getColumnIndexOrThrow("PMFCT")));
                //MNTFCT=OPPLD
                mChooseDeviceItemData.setMNTFCT(cursor.getString(cursor.getColumnIndexOrThrow("OPPLD")));
                mChooseDeviceItemData.setWAYID(WAYID);
                mChooseDeviceItemData.setWAYNM(cursor.getString(cursor.getColumnIndexOrThrow("WAYNM")));
                mChooseDeviceItemData.setEQNO(EQNO);
                mChooseDeviceItemData.setEQNM(cursor.getString(cursor.getColumnIndexOrThrow("EQNM")));
                mChooseDeviceItemData.setEQKD(cursor.getString(cursor.getColumnIndexOrThrow("EQKD")));
                mChooseDeviceItemData.setProgress(cursor.getInt(cursor.getColumnIndexOrThrow("Progress")));
                mChooseDeviceItemData.setCO(cursor.getString(cursor.getColumnIndexOrThrow("CO")));
                mChooseDeviceItemData.setCONM(cursor.getString(cursor.getColumnIndexOrThrow("CONM")));
                mChooseDeviceItemData.setPMFCTNM(cursor.getString(cursor.getColumnIndexOrThrow("PMFCTNM")));
                mChooseDeviceItemData.setUploadNM("");
                mChooseDeviceItemData.setUploadEMP(account);
                mChooseDeviceItemData.setChcekDataFromAPP(true);
                deviceDataList.add(mChooseDeviceItemData);
                if (mChooseDeviceItemData.getProgress() == 100) {
                    deviceonLeaveTheRoute++;
                }
                //

            }
            if (deviceDataList.get(deviceDataList.size() - 1).getProgress() == 100) {
                deviceonLeaveTheRoute = deviceDataList.size() - 1;
            }
            if (loginstatus){
                mainModel.setRouteCodeData(deviceDataList.get(deviceonLeaveTheRoute).getWAYID());
                mainModel.setDeviceNumberData(deviceDataList.get(deviceonLeaveTheRoute).getEQNO()+" "+deviceDataList.get(deviceonLeaveTheRoute).getEQNM());
            }else {
                deviceDataList=new ArrayList<>();
            }


        }

    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (loginstatus){
                currentCount=intent.getIntExtra(REFRESH,0);
                if (currentCount>=deviceDataList.size()){
                    currentCount=deviceDataList.size()-1;
;                }
                Log.d("tagRecevie", "onReceive: "+currentCount);
                deviceonLeaveTheRoute=currentCount;
                mainModel.setRouteCodeData(deviceDataList.get(currentCount).getWAYID());
                String data=deviceDataList.get(currentCount).getEQNO()+" "+deviceDataList.get(currentCount).getEQNM();
                mainModel.setDeviceNumberData(data);
                activityMainBinding.setData(mainModel);
            }
        }
    };
    private void init() {
        broadcastIntentfilter = new IntentFilter();
        Intent intent = this.getIntent();
        account=getIntent().getStringExtra(Account);
        AccessToken=getIntent().getStringExtra(AccessToken);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setView(this);


        mainModel.setLoginStatus(loginstatus);
        mainModel.setDeviceWheatherInBefore(deviceWheatherInBefore);
        activityMainBinding.setData(mainModel);
        mainComponent=DaggerMainComponent.builder()
                .applicationComponent(((Application)getApplicationContext()).getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
        mainComponent.inject(this);

        currentCount=0;
        deviceonLeaveTheRoute = 0;

        if (loginstatus){
            getInspectMainData();
            onStartScheduleService();
        }
        //mainModel.setRouteCodeData(deviceDataList.get(deviceonLeaveTheRoute).getEQNO()+" "+deviceDataList.get(deviceonLeaveTheRoute).getEQNM());
    }

    private void onStartScheduleService() {
        scheduleServiceIntent= new Intent(this,ScheduleService.class);
        Bundle serviceBundle=new Bundle();
        serviceBundle.putString(Account,account);
        Log.d("logStart", "onStartScheduleService: "+deviceonLeaveTheRoute);
        serviceBundle.putInt(CURRENT_DATA_COUNT,deviceonLeaveTheRoute);
        broadcastIntentfilter=new IntentFilter();
        broadcastIntentfilter.addAction(DATASET);
        scheduleServiceIntent.putExtras(serviceBundle);
        this.startService(scheduleServiceIntent);
    }

    @Override
    public void onCenterCloud() {

        if (mainModel.getDeviceNumberData().length()==0){
            showDialogInspection(getResourceString(R.string.no_device));
        }else {
            Intent centerCloud=new Intent();
            centerCloud.setAction(Intent.ACTION_VIEW);
            if (deviceDataList.get(currentCount).isChcekDataFromAPP()) {
                deviceDataList.get(currentCount).setMNTCO("1");
            }
            centerCloud.setData(Uri.parse("https://cloud.fpcetg.com.tw/FPC/WEB/MTN/MTN_EQPT/Default.aspx?" +
                    "CO=" + deviceDataList.get(currentCount).getCO() + "&" +
                    "PMFCT=" + deviceDataList.get(currentCount).getPMFCT() + "&" +
                    "MNTCO=" + deviceDataList.get(currentCount).getMNTCO() + "&" +
                    "MNTFCT=" + deviceDataList.get(currentCount).getMNTFCT() + "&" +
                    "EQNO=" + deviceDataList.get(currentCount).getEQNO() + "&" +
                    "token=" + presenter.getDisposableToken()));
            startActivity(centerCloud);
        }
    }

    @Override
    public void onDeviceChoose() {
        List<String> diaologDeviceIDString=new ArrayList<>();
        for (ChooseDeviceItemData itemData:deviceDataList){
            diaologDeviceIDString.add(itemData.getEQNO()+ " " + itemData.getEQNM());
        }
        showItemDialog(diaologDeviceIDString,onDeviceChooseListener);
    }

    @Override
    public void onFetchMessage() {

    }

    @Override
    public void onGetBaseInformation() {
        if (mainModel.getDeviceNumberData().length()==0){
            showDialogInspection(getResourceString(R.string.no_device));
        }else {
            Intent intent=new Intent(this, DeviceInformationActivity.class);
            intent.putExtra(DEVICE,deviceDataList.get(currentCount));
            startActivity(intent);
        }
    }

    @Override
    public void onDeviceEdit() {
        Intent intent=new Intent(this, EditDeviceActivity.class);
//        Bundle bundle=new Bundle();
        startActivity(intent);
    }

    private DialogInterface.OnClickListener onDeviceChooseListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //自動登入
            if (loginstatus){
                if (which != deviceonLeaveTheRoute){
                    mainModel.setDeviceNumberData(deviceDataList.get(which).getEQNO()+ " " + deviceDataList.get(which).getEQNM());
                    mainModel.setDeviceWheatherInBefore(false);
                    activityMainBinding.setData(mainModel);
                    onStopScheduleService();
                }
                else {
                    onStartScheduleService();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                    Date curDate=new Date(System.currentTimeMillis());
                    String date=simpleDateFormat.format(curDate);
                    deviceDataList.get(currentCount).setRecordDate(date);
                    presenter.AddChkInfo(deviceDataList.get(currentCount));
                    mainModel.setDeviceNumberData(deviceDataList.get(which).getEQNO() + " " + deviceDataList.get(which).getEQNM());
                    mainModel.setDeviceWheatherInBefore(true);

                }
            }
            //設定currentNumber跟which一樣才能addChkInfo現在在哪不會有錯
            Log.d("tagwh", "tagwh");
            currentCount=which;

        }
    };

    private void onStopScheduleService(){
        this.stopService(scheduleServiceIntent);
    }



}
