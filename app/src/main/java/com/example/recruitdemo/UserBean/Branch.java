package com.example.recruitdemo.UserBean;

import java.io.Serializable;

/**
 * Created by 张金瑞 on 2017/3/23.
 *市场部  四级联动  最后一层分局
 */
public class Branch implements Serializable{
    private String name;
    private String id;
    private String parentId;

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
}
