package com.example.inseptiontest.base;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class COResultList {
    @SerializedName("ResultList")
    private List<COResponse> resultList=new ArrayList<>();

    public List<COResponse> getResultList() {
        return resultList;
    }

    public void setResultList(List<COResponse> resultList) {
        this.resultList = resultList;
    }
}
