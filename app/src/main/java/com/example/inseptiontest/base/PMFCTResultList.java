package com.example.inseptiontest.base;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PMFCTResultList {
    @SerializedName("ResultList")
    private List<PMFCTResponse> resultList=new ArrayList<>();

    public List<PMFCTResponse> getResultList() {
        return resultList;
    }

    public void setResultList(List<PMFCTResponse> resultList) {
        this.resultList = resultList;
    }
}
