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
import android.widget.TextView;

import com.example.recruitdemo.R;

/**
 * Created by Administrator on 2017/3/15.
 */
public class MyDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String content;
    private String yes;
    private String no;
    private TextView tv_content;
    private TextView btn_yes;
    private TextView btn_no;
    private MyClickListener listener;

    public MyDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将自定义Diaglog对话框的布局引入
        setContentView(R.layout.myidalog);
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_yes = (TextView) findViewById(R.id.tv_yes);
        btn_no = (TextView) findViewById(R.id.tv_no);


        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);


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

    public void setOnClickListener(MyClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yes:
                listener.confirm();
                dismiss();
                break;
            case R.id.tv_no:
                listener.cancel();
                dismiss();
                break;
        }


    }
    public interface MyClickListener {
        void confirm();
        void cancel();
    }
}

