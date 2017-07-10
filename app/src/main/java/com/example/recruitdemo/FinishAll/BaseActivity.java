package com.example.recruitdemo.FinishAll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 张金瑞 on 2017/3/28. 一键销毁
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在执行activity的onCreate生命周期   调用一键销毁类的添加方法，将activty添加到list集合中去
        CollectorActivity.addActivity(this);
    }
    /*
    在执行onDestroy生命周期时，调用一键销毁类中的删除方法，将此activity从list集合中删除
     */
    @Override
    protected void onDestroy() {
        CollectorActivity.removeActivity(this);
        super.onDestroy();
    }
}
