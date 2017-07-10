package com.example.recruitdemo.CustomWidget;

import android.app.Application;

import com.example.recruitdemo.R;
import com.example.recruitdemo.Utils.SharedUtils;


/**
 * Created by Administrator on 2017/3/22.
 */
public class MyApplication extends Application {
    public static SharedUtils sharedUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedUtils=new SharedUtils();
//
//        x.Ext.init(this);
//        x.Ext.setDebug(true);
//
//        BaseAndroid.init(new BaseConfig()
//                .setAppColor(R.color.colorPrimary)//app主调颜色，用于标题栏等背景颜色
//                .setAppLogo(R.mipmap.ic_launcher)//app图标
//                .setFailPicture(R.mipmap.ic_launcher)//加载加载失败和加载中显示的图
//                .setCode(0)//网络请求成功返回的code数字，默认为1
//                .setHttpCode("code")//网络请求返回的code字段名称，默认为code
//                .setHttpMessage("msg")//网络请求返回的message字段名称，默认为message
//                .setHttpResult("resp"));//网络请求返回的result字段名称，默认为result
    }
}
