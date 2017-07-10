package com.example.recruitdemo.XiaoHui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Login;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 登录页面   实现主要功能：将账号、密码上传到服务器，并将服务器反馈给的数据进行解析封装到Login类中；
 * 再回馈给上一级
 */
public class XLoginActivity extends AppCompatActivity implements View.OnClickListener, Callback{
    private ImageView xFinish;
    private EditText et_number,et_word;
    private Button btn_dl;
    private SharedUtils sharedUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlogin);
        initView();
    }

    private void initView(){
        xFinish=(ImageView)findViewById(R.id.xFinish);
        et_number=(EditText)findViewById(R.id.et_number);
        et_word=(EditText)findViewById(R.id.et_word);
        btn_dl=(Button)findViewById(R.id.btn_dl);

        sharedUtils= MyApplication.sharedUtils;
        if(sharedUtils.getShared("LoginNumber",this)==null){

        }else {
            et_number.setText(sharedUtils.getShared("LoginNumber",this));
            et_number.setSelection(et_number.getText().length());
        }
        xFinish.setOnClickListener(this);
        btn_dl.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xFinish:
                finish();
                break;

            case R.id.btn_dl:
                String number=et_number.getText().toString();
                String password=et_word.getText().toString();

               if(number.equals("")|| password.equals("")){
                   Toast.makeText(XLoginActivity.this,"账号密码不能为空",Toast.LENGTH_LONG).show();
               }else{
                   Map<String,String> map=new ArrayMap<String,String>();
                   map.put("mobile",number);
                   map.put("password",password);
                   map.put("_method","GET");
                   sharedUtils.saveShared("LoginNumber",number,this);
                   sharedUtils.saveShared("LoginPassword",password,this);
                   OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.LOGIN,map,this);
               }

                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s=response.body().string();

        Message message=new Message();
        message.what=0;
        message.obj=s;
        handler.sendMessage(message);

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                String s= (String) msg.obj;
                Login login=JsonUtils.JsonLogin(s);
                sharedUtils.saveShared("token",login.getToken(),XLoginActivity.this);
                sharedUtils.saveShared("name",login.getName(),XLoginActivity.this);
                sharedUtils.saveShared("bm_id",login.getDepartment_id(),XLoginActivity.this);
                sharedUtils.saveShared("bm_name",login.getDepartment_name(),XLoginActivity.this);
                sharedUtils.saveShared("mobile",login.getMobile(),XLoginActivity.this);
                if(login.getLeader_id()!=null){
                    sharedUtils.saveShared("leader_id",login.getLeader_id(),XLoginActivity.this);
                    sharedUtils.saveShared("leader_name",login.getLeader_Name(),XLoginActivity.this);
                }
                sharedUtils.saveShared("staff_id",login.getId(),XLoginActivity.this);
                if(sharedUtils.getShared("token",XLoginActivity.this).equals("")){
                    Toast.makeText(XLoginActivity.this,"帐号密码错误",Toast.LENGTH_LONG).show();
                }else{
                    sharedUtils.saveShared_int("state",1,XLoginActivity.this);
                    finish();
                }

            }
        }
    };
}
