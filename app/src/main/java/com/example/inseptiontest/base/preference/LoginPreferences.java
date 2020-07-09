package com.example.inseptiontest.base.preference;

import android.content.Context;

import javax.inject.Inject;

public class LoginPreferences extends PreferencesHelper implements LoginPreferencesProvider {
    private final String LOGINACCOUNT="LOGINACCOUNT";
    private final String TOKEN="TOKEN";
    private final String SP_FiLE_NAME = LoginPreferences.class.getName();
    //??
    @Inject
    public LoginPreferences(Context context) {
        super(context);
    }

    @Override
    public void setToken(String token) {
        save(Type.STRING, TOKEN,token);

    }

    @Override
    public String getToken() {
        return (String) get(Type.STRING, TOKEN);
    }

    @Override
    public void setAccount(String account) {
        save(Type.STRING,LOGINACCOUNT,account);
    }

    @Override
    public String getAccount() {
        return (String)get(Type.STRING,LOGINACCOUNT);
    }

    @Override
    public String getClassName() {
        return SP_FiLE_NAME;
    }
}
