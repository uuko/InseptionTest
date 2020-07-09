package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PMFCTResponse implements Serializable {
    @SerializedName("MNTCO")
    @Expose
    private String MNTCO="";

    @SerializedName("MNTFCT")
    @Expose
    private String MNTFCT="";

    @SerializedName("CO")
    @Expose
    private String CO="";

    @SerializedName("PMFCT")
    @Expose
    private String PMFCT="";

    @SerializedName("PMFCTNM")
    @Expose
    private String PMFCTNM="";

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

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getPMFCT() {
        return PMFCT;
    }

    public void setPMFCT(String PMFCT) {
        this.PMFCT = PMFCT;
    }

    public String getPMFCTNM() {
        return PMFCTNM;
    }

    public void setPMFCTNM(String PMFCTNM) {
        this.PMFCTNM = PMFCTNM;
    }
}
