package com.example.inseptiontest.base;

public interface BaseAttatcher<V extends  BaseView> {
    void onAttatched(V view);
    void onDetached();
}
