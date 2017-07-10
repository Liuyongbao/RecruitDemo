package com.example.recruitdemo.UserBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/23.
 *
 * 市场部  四级联动  第二层 部门类
 */
public class Department implements Serializable{
    private String name;
    private String id;
    private String parentId;
    private List<Division> departmentList;

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

    public List<Division> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Division> departmentList) {
        this.departmentList = departmentList;
    }
}
