
package com.example.inseptiontest.ui.login;

import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.inseptiontest.R;
import com.example.inseptiontest.base.BaseActivity;
import com.example.inseptiontest.databinding.ActivityLoginBinding;
import com.example.inseptiontest.Application;
import com.example.inseptiontest.ui.main.MainActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private String account="",pwd="";
    public static String AccessToken="ACCESS_TOKEN";
    public static String Account="ACCOUNT";
    public static boolean loginstatus;
    private LoginComponent loginComponent;
    ActivityLoginBinding activityLoginBinding;
    private LoginModel loginModel;
    private static final  int REQUEST_PERMISSION=20200707;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};
    @Inject
    LoginContract.Presenter<LoginContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        requestPermissions(permissions,REQUEST_PERMISSION);
        getAccount();
        if (loginstatus){
            presenter.onAutoLogin(account,pwd);
        }
    }

    private void init() {
        loginstatus=false;
        loginModel=new LoginModel();
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setView(this);
        activityLoginBinding.setData(loginModel);
        loginComponent=DaggerLoginComponent.builder()
                .applicationComponent(((Application)getApplication().getApplicationContext()).getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build();
        loginComponent.inject(this);
        presenter.onAttatched(this);
    }

    //自動拿取資料，判斷是否要自動登入
    private void getAccount() {
        account="";
        pwd="";
        String Content_String="content://tw.com.efpg.processe_equip.provider.ShareCloud/ShareCloud";
        Uri uri=Uri.parse(Content_String);
        Cursor cursor=this.getContentResolver().query(
                uri,
                null, //select * from all column
                "LoginStatus" //where
                ,null,
                null // order by
        );
        if (cursor==null){
            showDialogInspection(getResourceString(R.string.auto_login_error_message));
        }else{
            while (cursor.moveToNext()){
                String isSuccess = cursor.getString(cursor.getColumnIndexOrThrow("IsSuccess"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("Message"));
                if(isSuccess.equals("true")){
                    account = cursor.getString(cursor.getColumnIndexOrThrow("Account"));
                    pwd = cursor.getString(cursor.getColumnIndexOrThrow("PWD"));
//                    loginModel.setAccount(account);
//                    loginModel.setPassword(pwd);
                }

                if (account.length()==0 && pwd.length()==0){
                    loginstatus=false;
                }else{
                    loginstatus=true;
                }
            }
        }

    }

    @Override
    public void onLoginClick() {
        if (loginstatus){
            if (OnCheckifNullInput()){
                presenter.onLogin(loginModel);
            }
        }else {
            if (OnCheckifNullInput()){
                presenter.onLogin(loginModel);
            }
        }


    }

    private boolean OnCheckifNullInput() {
        if (loginModel.getAccount().length()==0){
            showDialogInspection(getResourceString(R.string.login_account_hint));
            return false;
        }

        if (loginModel.getPassword().length()==0){
            showDialogInspection(getResourceString(R.string.login_password_hint));
            return false;
        }
        return true;
    }

    @Override
    public void getLoginResponse() {

        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra(AccessToken,presenter.getAccessToken());
        intent.putExtra(Account,loginModel.getAccount());
        startActivity(intent);
    }

    @Override
    public void getAutoLoginResponse(String account) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra(AccessToken,presenter.getAccessToken());
        intent.putExtra(Account,account);
        startActivity(intent);
    }
}
