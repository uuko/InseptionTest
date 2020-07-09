package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MNFCTRequest {
    @SerializedName("AuthorizedId")
    @Expose
    private String authorizedId;
    @SerializedName("IdNo")
    @Expose
    private String idNo;



    public String getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        this.authorizedId = authorizedId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
