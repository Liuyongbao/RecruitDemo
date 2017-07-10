package com.example.recruitdemo.SiNong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.CustomDialog;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.MyStudentActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Customer1;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.UserBean.Leadership;
import com.example.recruitdemo.UserBean.Login;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.XiaoHui.XApprovepeopleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 审批→发起审批
 * 实现主要功能：1、对Activity进行跳转，通过EventBus来将值获取回来
 *               2、添加审批人
 *               3、上传数据到服务器
 */
public class SApproveActivity extends AppCompatActivity implements View.OnClickListener, Callback{

    private Button btn_1,btn_2,sSubmit;
    private ImageView mBark;
    private TextView StuName,FzName,t1,t2;
    private AlertDialog.Builder builder;
    private String id;
    private String indent_id;
    private SharedUtils sharedUtils;
    private LinearLayout ll_remark;
    private TextView tv_remark;
    private String leader_id;
    private List<Leadership> list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sapprove);
        initView();
        EventBus.getDefault().register(this);
    }
    public void initView(){
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        sSubmit= (Button) findViewById(R.id.sSubmit);
        mBark = (ImageView)findViewById(R.id.mBark);
        StuName=(TextView)findViewById(R.id.StuName);
        FzName=(TextView)findViewById(R.id.FzName);
        tv_remark= (TextView) findViewById(R.id.tv_remark);
        ll_remark= (LinearLayout) findViewById(R.id.ll_remark);

        t1= (TextView) findViewById(R.id.t1);
        t2= (TextView) findViewById(R.id.t2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        mBark.setOnClickListener(this);
        t2.setOnClickListener(this);
        sSubmit.setOnClickListener(this);
        sharedUtils= MyApplication.sharedUtils;
        ll_remark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                Intent intent1 = new Intent(SApproveActivity.this, MyStudentActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_2:
                Intent intent=new Intent(this,XApprovepeopleActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.mBark:
                finish();
                break;
            case R.id.t2:
                /*
                 *状态按钮
                 */
                builder=new AlertDialog.Builder(this);
                builder.setIcon(null);
                builder.setTitle("请选择");
                final String[]items1={"录入","沟通","到校","预报名","入学"};
                builder.setSingleChoiceItems(items1, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        t2.setText(items1[which]);
                    }
                });
                builder.create().show();
                break;
            case R.id.sSubmit:
                /*
                提交按钮
                对提交的信息进行判断，保证提交的数据不能为空
                 */
                if (TextUtils.isEmpty(StuName.getText().toString())){
                    Toast.makeText(this,"学生信息不能为空",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(FzName.getText().toString())){
                    Toast.makeText(this,"审批人信息不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    /*
                    上传数据到服务器，并对数据进行判断
                     */
                    Map<String,String> map2=new HashMap<String,String>();
                    map2.put("token",HttpMode.getTOKEN(this));
                    map2.put("_method","POST");
                    map2.put("indent_id",id);
                    if(t2.getText().toString().equals("录入")){
                        map2.put("indent_status",String.valueOf(0));
                    }else if (t2.getText().toString().equals("沟通")){
                        map2.put("indent_status",String.valueOf(1));
                    }else if (t2.getText().toString().equals("到校")){
                        map2.put("indent_status",String.valueOf(2));
                    }else if (t2.getText().toString().equals("预报名")){
                        map2.put("indent_status",String.valueOf(3));
                    }else if (t2.getText().toString().equals("入学")){
                        map2.put("indent_status",String.valueOf(4));
                    }
                    if(list2!=null){
                        StringBuffer sb=new StringBuffer();
                        for (int i=0;i<list2.size();i++){
                            sb.append(list2.get(i).getId());
                            Log.e("sssssssssss",list2.get(0).getId());
                            if (i<list2.size()-1){
                                sb.append(",");
                            }
                        }
                        map2.put("audit_employees_string",sb.toString());
                    }else {
                        map2.put("audit_employees_string",sharedUtils.getShared("staff_id",this));
                    }
                    Log.e("sapprove---------",t2.getText().toString());
                    Log.e("wangzhi-------",HttpMode.HTTPURL+HttpMode.AUDIT);
                    OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.AUDIT,map2,this);
                    Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.ll_remark:
                CustomDialog dialog1 = new CustomDialog(this,"备注:",null,false);
                dialog1.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {
                        tv_remark.setText(inputString);
                    }   

                    @Override
                    public void onInputIllegal() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SApproveActivity.this);
                        alertDialog.setTitle("提示:");
                        alertDialog.setMessage(" 内容不能为空");
                        alertDialog.setPositiveButton("ok", null);
                        alertDialog.show();
                    }
                });
                dialog1.show();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEnventMainThread(Customer1 customer1){
        String name=customer1.getName();
        id=customer1.getIdd();
        indent_id=customer1.getId();
        Log.e("indent_id------5",indent_id);
        StuName.setText(name);
//        FzName.setText(customer1.getFuzeren());
        t1.setText(customer1.getStatus());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("AUDIT-----",e+"");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s=response.body().string();
        Log.e("AUDIT-----",s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){

        }else {
            List<String> list= (List<String>) data.getSerializableExtra("list");
            list2= (List<Leadership>) data.getSerializableExtra("list2");

            StringBuffer s=new StringBuffer();
            for (int i=0;i<list.size();i++){
                s.append(list.get(i));
                if (i<list.size()-1){
                    s.append(",");
                }
            }
            FzName.setText(s.toString());
        }
    }
}
