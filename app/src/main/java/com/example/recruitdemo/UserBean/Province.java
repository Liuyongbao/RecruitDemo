package com.example.recruitdemo.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 * 省市区  三级联动 省类
 */
public class Province {
    private String pName;
    private List<City> cityList;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
