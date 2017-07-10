package com.example.recruitdemo.CustomWidget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.recruitdemo.R;

/**
 * Created by Administrator on 2017/4/17. 二维码视图
 */
public class ErweimaDialog extends Dialog {

    private Context context;

    public ErweimaDialog(Context context) {
        super(context, R.style.ErweimaDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erweima);
        //设置dialog大小
        Window window=getWindow();
        //WindowManager主要用来管理窗口的一些状态、属性
        WindowManager manager=((Activity)context).getWindowManager();
        //获取当前对话框的 参数值
        WindowManager.LayoutParams params=window.getAttributes();
        //设置窗口显示的位置
        window.setGravity(Gravity.CENTER);
        //获取屏幕的宽高
        Display display=manager.getDefaultDisplay();
        //把屏幕的宽度的0.8赋值给当前对话框
        params.width=(int) (display.getWidth()*0.8);
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.white);
    }
}
