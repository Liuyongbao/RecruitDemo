package com.example.recruitdemo.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 * 省市区 三级联动 市类
 */
public class City {

    private String cName;
    private List<Area> areaList;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
