package com.example.recruitdemo.UserBean;

import java.io.Serializable;

/**
 * Created by 张金瑞 on 2017/4/4.
 */
public class Leadership implements Serializable{
    private String name;
    private String id;

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
}
