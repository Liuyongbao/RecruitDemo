package com.example.recruitdemo.UserBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/23.
 * 市场部  四级联动  第三层 分部类
 */
public class Division implements Serializable{
    private String name;
    private String id;
    private String parentId;
    private List<Branch> divisionList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Branch> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Branch> divisionList) {
        this.divisionList = divisionList;
    }
}
