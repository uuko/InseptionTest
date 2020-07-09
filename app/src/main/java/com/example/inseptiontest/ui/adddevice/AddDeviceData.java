package com.example.inseptiontest.ui.adddevice;

public class AddDeviceData  {




    //日期
    private CharSequence date;

    //保養廠
    private String mntfctnm ="";

    //公司
    private String company="";


    public String getPmfctn() {
        return pmfctn;
    }

    public void setPmfctn(String pmfctn) {
        this.pmfctn = pmfctn;
    }

    private String pmfctn="";
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }



    public CharSequence getDate() {
        return date;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }

    public String getMntfctnm() {
        return mntfctnm;
    }

    public void setMntfctnm(String mntfctnm) {
        this.mntfctnm = mntfctnm;
    }
}
