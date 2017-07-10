package com.example.recruitdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
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
 * Created by 我 on 2017/3/21.
 * 自定义Dialog
 */
public class CustomDialog extends Dialog {

    private  TextView ok,cancel;
    private EditText input;
    private Context context;
    private String title,content;
    private int size;
    private TextView Tvtitle;
    private boolean isAuto;
    public CustomDialog(Context context,String title,String content,boolean isAuto) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context=context;
        this.title=title;
        this.content=content;
        this.isAuto=isAuto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogs);
        Tvtitle= (TextView) findViewById(R.id.title);
         ok =  (TextView) findViewById(R.id.btn_ok);
         cancel = (TextView) findViewById(R.id.btn_cancel);
         input = (EditText) findViewById(R.id.edt_input);
        //动态设置EditText的输入长度
        input.setText(content);
        if (isAuto==true){
            if (title.equals("手机号")||title.equals("联系方式")||title.equals("联系人电话")){
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            }else {
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            }
        }

        if (title!=null){
            Tvtitle.setText(title);
        }
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
        params.height=(int) (display.getHeight()*0.24);
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.white);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close custom dialog
                dismiss();
                String inputString = input.getText().toString().trim();
                if (mListener != null) {
                    if (TextUtils.isEmpty(inputString)) {
                        mListener.onInputIllegal();
                    } else {
                        mListener.onInputLegitimacy(inputString);
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close dialog
                dismiss();
            }
        });
    }

    private OnDialogClickListener mListener;
    public void setOnDialogClickListener(OnDialogClickListener listener) {
        mListener = listener;
    }
    public interface OnDialogClickListener {

        //legitimacy input
        void onInputLegitimacy(String inputString);

        //illegal input
        void onInputIllegal();
    }
}
