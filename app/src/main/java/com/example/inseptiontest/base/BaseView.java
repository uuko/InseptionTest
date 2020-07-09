package com.example.inseptiontest.base;

import android.content.DialogInterface;
import android.net.Uri;

import androidx.annotation.StringRes;

import java.util.List;

public interface BaseView {


    void showDialogInspection(String message);

    void showItemDialog(List<String> list, DialogInterface.OnClickListener onClickListener);

    //loading progressDialog
    void showProgressDialog(String text);
    void showProgressDialog(@StringRes int text);
    void dismissProgressDialog();

    String getResourceString(@StringRes int text);
    String getUriFromPath(Uri uri);
}
