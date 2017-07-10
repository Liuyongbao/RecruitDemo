package com.example.recruitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.StudentAdapter;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.FinishAll.BaseActivity;
import com.example.recruitdemo.SiNong.StulistActivity;
import com.example.recruitdemo.UserBean.Customer;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.XiaoHui.XLoginActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 我的学生页面  实现主要功能:  跳转 获得下一级item的数量并显示
 */
public class MyStudentActivity extends BaseActivity implements View.OnClickListener{

    private Button mLink,mArrive,mPre,mEnrol,mEnter;
    private ImageView mFinish;
    private SharedUtils sharedUtils;
    private int state;
    private Map<String,String> map;
    private List<Customer> list;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_student);
        initView();
    }

    private void initView(){
        mLink=(Button)findViewById(R.id.mLink);
        mArrive=(Button)findViewById(R.id.mArrive);
        mPre=(Button)findViewById(R.id.mPre);
        mEnrol=(Button)findViewById(R.id.mEnrol);
        mEnter=(Button)findViewById(R.id.mEnter);
        mFinish=(ImageView)findViewById(R.id.mFinish);
        sharedUtils= MyApplication.sharedUtils;

        mLink.setOnClickListener(this);
        mArrive.setOnClickListener(this);
        mPre.setOnClickListener(this);
        mEnrol.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        mEnter.setOnClickListener(this);


    }

    private void initSize() {
       for(int i=0;i<5;i++){
           map=new HashMap<String, String>();
           String token= HttpMode.getTOKEN(this);
           map.put("token",token);
           map.put("_method","GET");
           map.put("status_id",String.valueOf(i));
           OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.CUSTOMER, map, new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {

               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
                   String s=response.body().string();
                   Message msg=handler.obtainMessage();
                   msg.what=1;
                   msg.obj=s;
                   handler.sendMessage(msg);
               }
           });
       }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1){
                String s2= (String) msg.obj;
                list= JsonUtils.jsonKe(s2);
                if (list!=null){
                    if(list.size()==0){
                        return;
                    }else{
                        if(list.get(0).getStatus_name().equals("录入")){
                            mEnter.setText("录入"+" ("+list.size()+")");
                        }else if(list.get(0).getStatus_name().equals("沟通")){
                            mLink.setText("沟通"+" ("+list.size()+")");
                        }else if(list.get(0).getStatus_name().equals("到校")){
                            mArrive.setText("到校"+" ("+list.size()+")");
                        }else if(list.get(0).getStatus_name().equals("预报名")){
                            mPre.setText("预报名"+" ("+list.size()+")");
                        }else if(list.get(0).getStatus_name().equals("入学")){
                            mEnrol.setText("入学"+" ("+list.size()+")");
                        }
                    }
                }

            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.mEnter:
                if (state!=0){
                    Intent intent1=new Intent(MyStudentActivity.this, StulistActivity.class);
                    sharedUtils.saveShared("stringname","0",MyStudentActivity.this);
                    startActivity(intent1);
                }else{
                    Intent in=new Intent(MyStudentActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.mLink:
                if (state!=0){
                    Intent intent=new Intent(MyStudentActivity.this, StulistActivity.class);
                    sharedUtils.saveShared("stringname","1",MyStudentActivity.this);
                    startActivity(intent);
                }else{
                    Intent in=new Intent(MyStudentActivity.this, XLoginActivity.class);
                    startActivity(in);
                }

                break;
            case R.id.mArrive:
                if (state!=0){
                    Intent intent2=new Intent(MyStudentActivity.this, StulistActivity.class);
                    sharedUtils.saveShared("stringname","2",MyStudentActivity.this);
                    startActivity(intent2);
                }else{
                    Intent in=new Intent(MyStudentActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;

            case R.id.mPre:
                if (state!=0){
                    Intent intent3=new Intent(MyStudentActivity.this, StulistActivity.class);
                    sharedUtils.saveShared("stringname","3",MyStudentActivity.this);
                    startActivity(intent3);
                }else{
                    Intent in=new Intent(MyStudentActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;

            case R.id.mEnrol:
                if (state!=0){
                    Intent intent5=new Intent(MyStudentActivity.this, StulistActivity.class);
                    sharedUtils.saveShared("stringname","4",MyStudentActivity.this);
                    startActivity(intent5);
                }else{
                    Intent in=new Intent(MyStudentActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.mFinish:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        state=sharedUtils.getShared_int("state",this);
            if(state==1){
                initSize();
            }
    }

}
