package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MNTFCTResponse {

    /**
     * MNTCO : 1
     * MNTFCT : M5
     * MNTFCTNM : 麥寮保養一廠
     */
    @SerializedName("MNTCO")
    @Expose
    private String MNTCO;
    @SerializedName("MNTFCT")
    @Expose
    private String MNTFCT;
    @SerializedName("MNTFCTNM")
    @Expose
    private String MNTFCTNM;

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

    public String getMNTFCTNM() {
        return MNTFCTNM;
    }

    public void setMNTFCTNM(String MNTFCTNM) {
        this.MNTFCTNM = MNTFCTNM;
    }
}
