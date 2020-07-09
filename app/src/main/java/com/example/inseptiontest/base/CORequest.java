package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CORequest {

    @SerializedName("AuthorizedId")
    @Expose
    private String AuthorizedId;

    @SerializedName("IdNo")
    @Expose
    private String IdNo;

    public String getAuthorizedId() {
        return AuthorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        AuthorizedId = authorizedId;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }
}
