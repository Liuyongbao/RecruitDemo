package com.example.recruitdemo.UserBean;

import java.io.Serializable;

/**
 * Created by 张金瑞 on 2017/3/29.
 */
public class Employee implements Serializable{
    private String name;
    private String id;
    private int size;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
