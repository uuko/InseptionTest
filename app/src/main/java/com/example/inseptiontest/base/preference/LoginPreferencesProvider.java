package com.example.inseptiontest.base.preference;

public interface LoginPreferencesProvider {
    void setToken(String token);
    String getToken();
    void setAccount(String account);
    String getAccount();
}
