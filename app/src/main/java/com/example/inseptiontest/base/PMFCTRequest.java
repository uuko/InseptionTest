package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PMFCTRequest {
    @SerializedName("AuthorizedId")
    @Expose
    private String AuthorizedId="";

    @SerializedName("IdNo")
    @Expose
    private String IdNo="";

    @SerializedName("MNTCO")
    @Expose
    private String MNTCO="";

    @SerializedName("MNTFCT")
    @Expose
    private String MNTFCT="";

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

    public String getMNTCO() {
        return MNTCO;
    }

    public void setMNTCO(String MNTCO) {
        this.MNTCO = MNTCO;
    }

    public String getMNTFCT() {
        return MNTFCT;
    }

    public void setMNTFCT(String MNTFCT) {
        this.MNTFCT = MNTFCT;
    }
}
