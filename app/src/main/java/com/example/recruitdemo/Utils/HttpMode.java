package com.example.recruitdemo.Utils;

import android.content.Context;

import com.example.recruitdemo.CustomWidget.MyApplication;

/**
 * Created by 张金瑞 on 2017/3/22.
 */
public class HttpMode {

    //前段地址
    public static final String HTTPURL="http://223.72.255.100/MOA";
    //员工登录验证
    public static final String LOGIN="/app/employee/login";
    //部门列表查询
    public static final String ORG="/app/org";
    //员工列表查询
    public static final String EMPLOYEE="/app/employee";
    //员工领导查询
    public static final String LEADER="/app/employee/leader";
    //员工信息查询
    public static final String INFO="/app/employee/info";
    //客户订单录入、订单列表查询、订单详情查询、订单修改
    public static final String CUSTOMER="/app/indent/";
    //订单变更审批发起、订单变更列表查询、订单变更详细查询、订单流程审批
    public static final String AUDIT="/app/audit";
    //版本更新
    public static final String LASTEST="/app/apk/lastest/";
    //版本更新
    public static final String UPLOAD="http://223.72.255.100/MOA/static/apk/moa.apk ";
    //修改密码
    public static final String PASSWD="/app/employee/passwd";

    //获取token的方法
    public static String getTOKEN(Context context){

        return MyApplication.sharedUtils.getShared("token",context);
    }
}
