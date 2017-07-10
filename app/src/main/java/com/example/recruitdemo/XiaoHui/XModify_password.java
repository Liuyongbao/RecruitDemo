package com.example.recruitdemo.XiaoHui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.updateversion.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class XModify_password extends AppCompatActivity implements View.OnClickListener, Callback{
    private ImageView img_back;
    private Button btn_qd;
    private TextView tv_account;
    private EditText edt_passwd1,edt_passwd2,edt_passwd3;
    private SharedUtils sharedUtils;
    private String LoginNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmodify_password);
        initView();
    }
    private void initView(){
        img_back= (ImageView) findViewById(R.id.back);
        btn_qd= (Button) findViewById(R.id.btn_qd);
        tv_account= (TextView) findViewById(R.id.account);
        edt_passwd1= (EditText) findViewById(R.id.password1);
        edt_passwd2= (EditText) findViewById(R.id.password2);
        edt_passwd3= (EditText) findViewById(R.id.password3);

        sharedUtils= MyApplication.sharedUtils;
        LoginNumber=sharedUtils.getShared("LoginNumber",this);
        tv_account.setText(LoginNumber);

        img_back.setOnClickListener(this);
        btn_qd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_qd:
                String passwd1=edt_passwd1.getText().toString();
                String passwd2=edt_passwd2.getText().toString();
                String passwd3=edt_passwd3.getText().toString();
                if (passwd1.equals("")||passwd2.equals("")){
                    Toast.makeText(XModify_password.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!passwd1.equals(passwd2)) {
                    Toast.makeText(XModify_password.this,"两次密码输入不同",Toast.LENGTH_SHORT).show();
                }else{

                    Map<String,String> map=new HashMap<>();
                    map.put("token", HttpMode.getTOKEN(this));
                    map.put("_method","PUT");
                    map.put("pwd1",passwd3);
                    map.put("pwd2",passwd2);
                    OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.PASSWD,map,this);
                }
                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String data=response.body().string();
        Log.e("data---------Login",data);
        Message msg=handler.obtainMessage();
        msg.what=0;
        msg.obj=data;
        handler.sendMessage(msg);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                String data= (String) msg.obj;
                String passwd_status= JsonUtils.JsonPasswd(data);
                if (passwd_status.equals("200")){
                    Toast.makeText(XModify_password.this,"密码修改成功,请重新登录",Toast.LENGTH_SHORT).show();
                    sharedUtils.saveShared_int("state",0,XModify_password.this);
                    Intent intent=new Intent(XModify_password.this,XLoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(XModify_password.this,"密码修改失败,请检查原密码",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
