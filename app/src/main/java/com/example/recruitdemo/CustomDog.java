package com.example.recruitdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recruitdemo.R;

/**
 * Created by 我 on 2017/3/22.
 * 自定义Dialog   性别
 */
public class CustomDog extends Dialog {
    private TextView ok,cancel,shezhi;
    private Context context;
    public CustomDog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog);

        ok = (TextView) findViewById(R.id.nan);
        cancel = (TextView) findViewById(R.id.nv);
        shezhi = (TextView) findViewById(R.id.shezhi);

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
        params.width=(int) (display.getWidth()*0.8);;
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.white);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close custom dialog
                dismiss();
                String inputString = ok.getText().toString().trim();

                if (mListener != null) {
                    if (TextUtils.isEmpty(inputString)) {
                        mListener.onInputIllegal();
                    } else {
                        mListener.onInputLegitimacy(inputString);
                    }
                }
            }
        });

        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //close dialog
                String inputStringss = cancel.getText().toString().trim();
                if (mListener != null) {
                    if (TextUtils.isEmpty(inputStringss)) {
                        mListener.onInputIllegal();
                    } else {
                        mListener.onInputLegitimacy(inputStringss);
                    }
                }
            }
        });
    }

    private OnDialogClickListeners mListener;

    public void setOnDialogClickListeners(OnDialogClickListeners listener) {
        mListener = listener;
    }

    public interface OnDialogClickListeners {

        //legitimacy input
        void onInputLegitimacy(String inputString);

        //illegal input
        void onInputIllegal();
    }
}
