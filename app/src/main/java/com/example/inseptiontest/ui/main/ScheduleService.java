package com.example.inseptiontest.ui.main;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateFormat;

import androidx.annotation.Nullable;

import com.example.inseptiontest.base.APIService;
import com.example.inseptiontest.base.AddChkInfoResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.inseptiontest.ui.login.LoginActivity.Account;
import static com.example.inseptiontest.ui.main.MainActivity.CURRENT_DATA_COUNT;
import static com.example.inseptiontest.ui.main.MainActivity.DATASET;

public class ScheduleService extends Service {

    public static String REFRESH="REFRESH";
    private Retrofit retrofit;
    private Handler handler;
    private Handler refreshHandler;
    private boolean isEnd;
    private boolean isStart=true;
    private String account;
    private int listDataSize;
    private Integer deviceOnLeaveCount;
    private Integer currentDataCount;
    private List<ChooseDeviceItemData> chooseDeviceItemDataList=new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(minuteRunnable);
        refreshHandler.removeCallbacks(refreshRunnable);
    }

    private void init() {
        handler=new Handler();
        refreshHandler=new Handler();
        isEnd=false;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isStart){
            deviceOnLeaveCount=intent.getIntExtra(CURRENT_DATA_COUNT,0);
            account=intent.getStringExtra(Account);
            currentDataCount=deviceOnLeaveCount;
            onCreateApi();
            handler.post(minuteRunnable);
            refreshHandler.post(refreshRunnable);
            isStart=false;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable refreshRunnable=new Runnable() {
        @Override
        public void run() {
            Intent broadcastIntent=new Intent();
            broadcastIntent.setAction(DATASET);
            broadcastIntent.putExtra(REFRESH,currentDataCount);
            sendBroadcast(broadcastIntent);
            refreshHandler.postDelayed(refreshRunnable,2000);
        }
    };
    //拿到目前巡檢人員在哪邊之後先傳上去一次再判斷Delay三分鐘或五分鐘
    //先拿巡檢人員在哪的詳細資料
    private Runnable minuteRunnable=new Runnable() {

        @Override
        public void run() {
            getCurrentDataList();
            if (isEnd){
                //handler.postDelayed(minuteRunnable,300000);
                handler.postDelayed(minuteRunnable,10000);
            }else{
                handler.postDelayed(minuteRunnable,5000);
                //handler.postDelayed(minuteRunnable,180000);
            }
        }
    };

    private void getCurrentDataList() {
        //CurrentDate
        String CONTENT_STRING = "content://tw.com.efpg.processe_equip.provider.ShareCloud/ShareCloud";
        Calendar mCal = Calendar.getInstance();
        CharSequence currentDate;
        currentDate = DateFormat.format("yyyy/MM/dd", mCal.getTime());
        Uri uri = Uri.parse(CONTENT_STRING);
        Cursor cursor = this.getContentResolver().query(
                uri,
                null,
                "CurrentJob",
                null, null
        );
        chooseDeviceItemDataList=new ArrayList<>();
        if (cursor!=null){
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
                mChooseDeviceItemData.setRecordDate(currentDate.toString());
                mChooseDeviceItemData.setEQNM(cursor.getString(cursor.getColumnIndexOrThrow("EQNM")));
                mChooseDeviceItemData.setEQKD(cursor.getString(cursor.getColumnIndexOrThrow("EQKD")));
                mChooseDeviceItemData.setProgress(cursor.getInt(cursor.getColumnIndexOrThrow("Progress")));
                mChooseDeviceItemData.setCO(cursor.getString(cursor.getColumnIndexOrThrow("CO")));
                mChooseDeviceItemData.setCONM(cursor.getString(cursor.getColumnIndexOrThrow("CONM")));
                mChooseDeviceItemData.setPMFCTNM(cursor.getString(cursor.getColumnIndexOrThrow("PMFCTNM")));
                mChooseDeviceItemData.setUploadNM("");
                mChooseDeviceItemData.setUploadEMP(account);
                mChooseDeviceItemData.setChcekDataFromAPP(true);
                chooseDeviceItemDataList.add(mChooseDeviceItemData);
            }
            listDataSize=chooseDeviceItemDataList.size();
            if (currentDataCount<=listDataSize){
                if (currentDataCount==listDataSize){
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date curDate=new Date(System.currentTimeMillis());
                    String formateEnd=dateFormat.format(curDate);
                    chooseDeviceItemDataList.get(listDataSize-1).setRecordDate(formateEnd);
                    chooseDeviceItemDataList.get(listDataSize-1).setEQNO("end");
                    chooseDeviceItemDataList.get(listDataSize-1).setPosition(currentDataCount);
                    AddChkInfo(chooseDeviceItemDataList.get(listDataSize-1));
                    currentDataCount++;
                }
                else {
                    if (chooseDeviceItemDataList.get(currentDataCount).getProgress()==100){
                        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date curDate=new Date(System.currentTimeMillis());
                        String formateEnd=format.format(curDate);
                        chooseDeviceItemDataList.get(currentDataCount).setRecordDate(formateEnd);
                        chooseDeviceItemDataList.get(currentDataCount).setPosition(currentDataCount);
                        AddChkInfo(chooseDeviceItemDataList.get(currentDataCount));
                        currentDataCount++;
                    }
                }
            }
            if (currentDataCount>=chooseDeviceItemDataList.size()){
                isEnd=true;
            }
        }


    }

    private void onCreateApi() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .build());
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://cloud.fpcetg.com.tw/FPC/API/MTN/API_MTN/MTN/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private void AddChkInfo(final ChooseDeviceItemData mChooseDeviceItemData) {
        String authorizedId = "e1569364-6066-48af-8f47-8f11bb4916dd";
        AddChkInfoRequest addChkInfoRequest=new AddChkInfoRequest(authorizedId,
                mChooseDeviceItemData.getOPCO(),
                mChooseDeviceItemData.getOPPLD(),
                mChooseDeviceItemData.getWAYID(),
                mChooseDeviceItemData.getWAYNM(),
                mChooseDeviceItemData.getCO(),
                mChooseDeviceItemData.getCONM(),
                mChooseDeviceItemData.getPMFCT(),
                mChooseDeviceItemData.getPMFCTNM(),
                mChooseDeviceItemData.getEQNO(),
                account,
                mChooseDeviceItemData.getUploadNM(),
                mChooseDeviceItemData.getRecordDate());

        APIService apiService=retrofit.create(APIService.class);
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiService.onAddChkInfo("AddChkInfo",addChkInfoRequest)
        .observeOn(AndroidSchedulers.mainThread())
         .subscribeOn(Schedulers.io())
         .subscribeWith(new DisposableObserver<AddChkInfoResponse>() {
             @Override
             public void onNext(AddChkInfoResponse addChkInfoResponse) {
                 if (mChooseDeviceItemData.getPosition()>=listDataSize){
                     stopSelf();
                 }
             }

             @Override
             public void onError(Throwable e) {
                AddChkInfo(mChooseDeviceItemData);
             }

             @Override
             public void onComplete() {

             }
         }));

    }
}
