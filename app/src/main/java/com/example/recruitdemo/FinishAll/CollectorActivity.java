package com.example.recruitdemo.FinishAll;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/28. 一键销毁
 */
public class CollectorActivity {
    //声明一个list集合装 Activity
    private static List<AppCompatActivity> list=new ArrayList<AppCompatActivity>();

    /*
    将Activity添加到list集合中
     */
    public static void addActivity(AppCompatActivity a){
        list.add(a);
    }
    /*
    将添加到list集合中的Activity从里面删除
     */
    public static void removeActivity(AppCompatActivity a){
        list.remove(a);
    }
    /*
    遍历所有添加到list中的Activity，并进行删除
     */
    public static void finishActivity(){
        for(AppCompatActivity a:list){
            if(!a.isFinishing()){
                a.finish();
            }
        }
    }
}
