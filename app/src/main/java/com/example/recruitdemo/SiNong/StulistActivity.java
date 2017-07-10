package com.example.recruitdemo.SiNong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.StudentAdapter;
import com.example.recruitdemo.BaoGe.BStudent_ShowActivity;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.FinishAll.BaseActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Customer;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 我的学生→学生列表展示    实现主要功能：将从服务器解析到的数据经过简化通过ListView展示出来，并将所获得的
 * item总数反馈给上一级；并将item(Customer)数据通过Intent传到下一级
 */
public class StulistActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, Callback{

    private ListView lv;
    private StudentAdapter adapter;
    private Map<String,String>map;
    private List<Customer> list;
    private SharedUtils sharedUtils;
    private ImageView sFinish;
    private TextView tv;
    private String namenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stulist);
        initView();
    }

    private void initView(){
        lv=(ListView)findViewById(R.id.lv);
        sFinish= (ImageView) findViewById(R.id.sfinish);
        sFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv=(TextView)findViewById(R.id.tv);
        sharedUtils= MyApplication.sharedUtils;
        String sn=sharedUtils.getShared("stringname",StulistActivity.this);
        if(sn.equals("0")){
            tv.setText("录入列表");
            namenumber="0";
            sharedUtils.saveShared_int("namenumber",0,StulistActivity.this);
        }
        if(sn.equals("1")){
            tv.setText("沟通列表");
            namenumber="1";
            sharedUtils.saveShared_int("namenumber",1,StulistActivity.this);
        }
        if(sn.equals("2")){
            tv.setText("到校列表");
            namenumber="2";
            sharedUtils.saveShared_int("namenumber",2,StulistActivity.this);
        }
        if(sn.equals("3")){
            tv.setText("预报名列表");
            namenumber="3";
            sharedUtils.saveShared_int("namenumber",3,StulistActivity.this);
        }
        if(sn.equals("4")){
            tv.setText("入学列表");
            namenumber="4";
            sharedUtils.saveShared_int("namenumber",4,StulistActivity.this);
        }


        map=new HashMap<String, String>();
        String token= HttpMode.getTOKEN(this);
        map.put("token",token);
        map.put("_method","GET");
        map.put("status_id",namenumber);
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.CUSTOMER,map,this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Customer c=list.get(position);
        Intent intent=new Intent(this, BStudent_ShowActivity.class);
        intent.putExtra("Customer",c);
        startActivity(intent);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1){
                String s2= (String) msg.obj;
                list= JsonUtils.jsonKe(s2);
                adapter=new StudentAdapter(StulistActivity.this,list);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(StulistActivity.this);
                if (list!=null){
                    if (list.size()!=0){
                        sharedUtils.saveShared("EnterNumber",list.size()+"",StulistActivity.this);
                        Intent in=new Intent();
                        in.putExtra("number",list.size()+"");
                        setResult(99,in);
                    }else {
                        sharedUtils.saveShared("EnterNumber","",StulistActivity.this);

                    }
                }

            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        String s1=response.body().string();

        Message msg=handler.obtainMessage();
        msg.what=1;
        msg.obj=s1;
        handler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
