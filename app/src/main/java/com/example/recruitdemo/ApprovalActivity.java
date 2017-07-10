package com.example.recruitdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.SiNong.SApproveActivity;
import com.example.recruitdemo.SiNong.SMylaunchActivity;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.UserBean.Number;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.XiaoHui.XLoginActivity;
import com.example.recruitdemo.updateversion.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 审批界面    实现主要功能：跳转
 */
public class ApprovalActivity extends Activity implements View.OnClickListener, Callback{
    private LinearLayout aNoApproval,aMyLaunch,aMyApproval;
    private Button aLaunchApproval;
    private ImageView aFinish;
    private SharedUtils sharedUtils;
    private int state;
    private TextView tv_dai,tv_fa,tv_yi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        initView();
        IntentData();
    }

    private void initView(){
        aNoApproval=(LinearLayout)findViewById(R.id.aNoApproval);
        aMyLaunch=(LinearLayout)findViewById(R.id.aMyLaunch);
        aMyApproval=(LinearLayout)findViewById(R.id.aMyApproval);
        aLaunchApproval=(Button)findViewById(R.id.aLaunchApproval);
        aFinish=(ImageView)findViewById(R.id.afinish);
        tv_dai= (TextView) findViewById(R.id.tv_dai);
        tv_fa= (TextView) findViewById(R.id.tv_fa);
        tv_yi= (TextView) findViewById(R.id.tv_yi);
        aNoApproval.setOnClickListener(this);
        aMyLaunch.setOnClickListener(this);
        aMyApproval.setOnClickListener(this);
        aLaunchApproval.setOnClickListener(this);
        aFinish.setOnClickListener(this);
        sharedUtils= MyApplication.sharedUtils;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aNoApproval:
                if (state != 0) {
                    sharedUtils.saveShared("type","2",this);
                    Intent intent3=new Intent(ApprovalActivity.this, SMylaunchActivity.class);
                    startActivity(intent3);
                }else{
                    Intent in=new Intent(ApprovalActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.aMyLaunch:
                if (state!=0){
                    sharedUtils.saveShared("type","1",this);
                    Intent intent=new Intent(ApprovalActivity.this, SMylaunchActivity.class);
                    sharedUtils.saveShared_int("listAll",0,this);
                    startActivity(intent);
                }else {
                    Intent in=new Intent(ApprovalActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.aMyApproval:
                if (state!=0){
                    sharedUtils.saveShared("type","3",this);
                    Intent intent2=new Intent(ApprovalActivity.this, SMylaunchActivity.class);
                    sharedUtils.saveShared_int("listAll",0,this);
                    startActivity(intent2);
                }else {

                    Intent in=new Intent(ApprovalActivity.this, XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.aLaunchApproval:


                sharedUtils.saveShared_int("status",1,ApprovalActivity.this);
                if (state!=0){
                    Intent intent4=new Intent(ApprovalActivity.this, SApproveActivity.class);
                    sharedUtils.saveShared_int("listAll",0,this);
                    startActivity(intent4);
                }else{
                    Intent in=new Intent(ApprovalActivity.this, XLoginActivity.class);
                    startActivity(in);
                }


                break;

            case R.id.afinish:
                finish();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        state=sharedUtils.getShared_int("state",this);
        if (state!=0){
            Map<String,String> map=new HashMap<>();
            map.put("token", HttpMode.getTOKEN(this));
            map.put("_method","GET");
            OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.INFO,map,this);
        }

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s=response.body().string();
        Message msg=handler.obtainMessage();
        msg.what=0;
        msg.obj=s;
        handler.sendMessage(msg);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                String data= (String) msg.obj;
                Number number= JsonUtils.JsonNumber(data);
                int doing_audit_number=number.getDoing_audit_number();
                int  my_audit_number=number.getMy_audit_number();
                int total_audit_number=number.getTotal_audit_number();
                if (doing_audit_number!=0){
                    tv_dai.setVisibility(View.VISIBLE);
                    tv_dai.setText(doing_audit_number+"");
                    if (doing_audit_number>99){
                        tv_dai.setText("99");
                    }
                }else{
                    tv_dai.setVisibility(View.GONE);
                }
                if (my_audit_number!=0){
                    tv_fa.setVisibility(View.VISIBLE);
                    tv_fa.setText(my_audit_number+"");
                    if (my_audit_number>99){
                        tv_fa.setText("99");
                    }
                }else {
                    tv_fa.setVisibility(View.GONE);
                }
                if (total_audit_number!=0){
                    tv_yi.setVisibility(View.VISIBLE);
                    tv_yi.setText(total_audit_number+"");
                    if (total_audit_number>99){
                        tv_yi.setText("99");
                    }
                }else {
                    tv_yi.setVisibility(View.GONE);
                }
            }

            if (msg.what==1){
                String data= (String) msg.obj;
                List<Employee> list= JsonUtils.JsonEmp(data);
                if (list.size()==0){
                    aLaunchApproval.setVisibility(View.GONE);
                }else{
                    aLaunchApproval.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    private void IntentData(){
        Map<String,String> map2=new HashMap<>();
        map2.put("token",HttpMode.getTOKEN(this));
        map2.put("_method","GET");
        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.LEADER, map2, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.e("---------ss-----",s);
                Message msg=handler.obtainMessage();
                msg.what=1;
                msg.obj=s;
                handler.sendMessage(msg);
            }
        });
    }
}
