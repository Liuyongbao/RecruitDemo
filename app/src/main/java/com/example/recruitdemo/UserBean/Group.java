package com.example.recruitdemo.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 * ExpandableListView组
 */
public class Group {
    private String grname;
    private List<College> flist;
    public Group(String grname){
        this.grname=grname;
    }
    public String getGrname() {
        return grname;
    }
    public List<College> getFlist() {
        return flist;
    }
    public void setFlist(List<College> flist) {
        this.flist = flist;
    }

}
