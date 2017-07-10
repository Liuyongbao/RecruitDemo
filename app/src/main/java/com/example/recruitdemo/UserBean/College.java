package com.example.recruitdemo.UserBean;

import java.util.List;

/**
 * Created by 花卷 on 2017/3/22.
 * 市场部  四级联动 第一层  学院
 */

public class College {

    private String id;
    private String name;
    private List<Department> collegeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getCollegeList() {
        return collegeList;
    }

    public void setCollegeList(List<Department> collegeList) {
        this.collegeList = collegeList;
    }
}
