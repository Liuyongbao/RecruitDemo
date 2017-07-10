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
import android.widget.Toast;

import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.FinishAll.BaseActivity;
import com.example.recruitdemo.FinishAll.CollectorActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.ShenPi;
import com.example.recruitdemo.UserBean.ShenPi1;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class XApprovalDetailsEditActivity extends BaseActivity implements View.OnClickListener, Callback{
    private Button xbtn_qd;
    private ImageView ximg_finish;
    private EditText xedt_note;

    private ShenPi1 shenPi1;
    private SharedUtils sharedUtils;
    private String flow_id;
    private String status;
    private ShenPi shenPi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xapproval_details_edit);
        initView();
    }
    private void initView(){
        xbtn_qd= (Button) findViewById(R.id.btn_qd);
        ximg_finish= (ImageView) findViewById(R.id.img_back);
        xedt_note= (EditText) findViewById(R.id.et_note);
        sharedUtils= MyApplication.sharedUtils;
        xbtn_qd.setOnClickListener(this);
        ximg_finish.setOnClickListener(this);
        Intent intent=getIntent();
        status=intent.getStringExtra("status");
        shenPi= (ShenPi) intent.getSerializableExtra("shenpiClass");
        initListView(shenPi.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_qd:
                String note=xedt_note.getText().toString();
                setstatus(status,note);
                CollectorActivity.finishActivity();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }


    /*
    上传数据，获得审批详情
     */
    public void initListView(String id){
        Map<String,String> map=new HashMap<>();
        map.put("_method","GET");
        map.put("token", HttpMode.getTOKEN(this));
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.AUDIT+"/"+id,map,this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s=response.body().string();
        Message message=handler.obtainMessage();
        message.what=0;
        message.obj=s;
        handler.sendMessage(message);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                String data= (String) msg.obj;
                Log.e("data-----------",data);
                 shenPi1=JsonUtils.jsonShenPi1(data);
                Log.e("shenPi1-------",shenPi1.getId());
            }
        }
    };

    private void  setstatus(String status,String edit){
        Map<String,String> map=new HashMap<>();
        map.put("token",HttpMode.getTOKEN(this));
        sharedUtils.saveShared_int("listAll",1,this);
        map.put("_method","PUT");
        Log.e("flowsList_size-------",shenPi1.getFlowsList().size()+"");
        for(int i=0;i<shenPi1.getFlowsList().size();i++){
            if(shenPi1.getFlowsList().get(i).getFl_employee_name().equals(sharedUtils.getShared("name",this))){
                flow_id=shenPi1.getFlowsList().get(i).getFlows_id();
            }
        }
        Log.e("flow_id",flow_id);

        map.put("status_id",status);
        Log.e("status",status);

        if(status.equals("3")){
            map.put("employee_id","");
        }

        if (edit.equals("")){

        }else{
            map.put("notes",edit);
        }
        Log.e("notes--------",edit);
        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.AUDIT+"/"+shenPi1.getId()+"/"+flow_id, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.e("res--------------",s);
            }
        });

        Toast.makeText(this,"审批成功,可在我已审批中查看数据",Toast.LENGTH_LONG).show();
        finish();
    }
}
