package com.example.inseptiontest.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COResponse {

    @SerializedName("CO")
    @Expose
    private String CO="";

    @SerializedName("CONM")
    @Expose
    private String CONM="";

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getCONM() {
        return CONM;
    }

    public void setCONM(String CONM) {
        this.CONM = CONM;
    }



}
