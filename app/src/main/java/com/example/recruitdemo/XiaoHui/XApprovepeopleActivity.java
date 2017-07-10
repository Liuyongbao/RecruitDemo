package com.example.recruitdemo.XiaoHui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.GridAdapter;
import com.example.recruitdemo.BaoGe.XAdd_speopleActivity;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.UserBean.Leadership;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class XApprovepeopleActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener, Callback{
    private GridView gv;
    private List<String> list = new ArrayList<String>();
    private GridAdapter gridAdapter;
    private int a;
    private Button btn_complete;
    private ImageView back;
    private List<String> list2=new ArrayList<>();
    private SharedUtils sharedUtils;
    private Leadership leadership;
    private List<Leadership> leaderships=new ArrayList<>();
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xapprovepeople);
        sharedUtils= MyApplication.sharedUtils;
        initView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position==parent.getChildCount()-2){
            Intent in = new Intent(XApprovepeopleActivity.this,XAdd_speopleActivity.class);
            startActivityForResult(in,0);
        }
        if(position==parent.getChildCount()-2 || position==parent.getChildCount()-1){

        }else{
            a--;
            list.remove(position);
            list2.remove(position);
            leaderships.remove(position);
            gridAdapter.Refresh(list);
        }
    }
    private void initView(){
        gv = (GridView)findViewById(R.id.gv);
        String staff_id=sharedUtils.getShared("staff_id",this);
        Map<String,String> map=new HashMap<>();
        map.put("token", HttpMode.getTOKEN(this));
        map.put("_method","GET");
        map.put("id",staff_id);
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.LEADER,map,this);

        btn_complete=(Button)findViewById(R.id.btn_complete);
        back= (ImageView) findViewById(R.id.mBark);


        btn_complete.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==99){
                leadership= (Leadership) data.getSerializableExtra("string");
//            if(list.size()==0){
//                list.add(a,leadership.getName());
//                list2.add(leadership.getName());
//                a++;
//                leaderships.add(leadership);
//                gridAdapter.Refresh(list);
//            }else
// {
              if(leadership!= null){
                  if(list.size()>=2){
                  name=leadership.getName();
                      Log.e("name------",name);
                          if(list2.contains(name)){
                              Toast.makeText(XApprovepeopleActivity.this,"审批人已添加",Toast.LENGTH_SHORT).show();
                          }else{
                              list.add(a,name);
                              a++;
                              list2.add(name);
                              leaderships.add(leadership);
                              gridAdapter.Refresh(list);
                          }
                      }
                  }

            }
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_complete:
                Intent intent=new Intent();
                intent.putExtra("list", (Serializable) list2);
                intent.putExtra("list2", (Serializable) leaderships);
                setResult(3,intent);
                finish();
                break;
            case R.id.mBark:
                finish();
                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s=response.body().string();
        Log.e("AUDIT-----",s);
        Message msg=handler.obtainMessage();
        msg.obj=s;
        msg.what=1;
        handler.sendMessage(msg);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                String ss= (String) msg.obj;
                List<Employee> employee= JsonUtils.JsonEmp(ss);
                for(int i=0;i<2;i++){
                    if(i==1){
                        list.add(employee.get(0).getName());
                    }else{
                        list.add("");
                    }
                }
                gridAdapter = new GridAdapter(XApprovepeopleActivity.this,list);
                gv.setAdapter(gridAdapter);
                gv.setOnItemClickListener(XApprovepeopleActivity.this);
            }
        }
    };
}
