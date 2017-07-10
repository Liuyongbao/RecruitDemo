package com.example.recruitdemo.SiNong;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.recruitdemo.Adapter.DepartmentAdapter;
import com.example.recruitdemo.Adapter.ListAdapter;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Branch;
import com.example.recruitdemo.UserBean.Division;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 市场部→选择部门到最后一个分部→分部  实现主要功能：将获取到的Branch类通过listView展示出来
 */
public class SBranchActivity extends AppCompatActivity implements Callback{
    private ListView sLv,StuLv;
    private ImageView sFinish;
    private Division division;
    private List<Employee> employeeList;
    private List<Employee> list1;
    private DepartmentAdapter<Branch> branchAdapter;
    private  ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbranch);
        initView();
    }
    private void initView(){
        sLv= (ListView) findViewById(R.id.sLv);
//        StuLv= (ListView) findViewById(R.id.StuLv);
        sFinish=(ImageView)findViewById(R.id.eFinish_dq);
        sFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent=getIntent();
        division= (Division) intent.getSerializableExtra("Division");

        /*
        市场部分部 员工列表
         */
//        employeeList= (List<Employee>) intent.getSerializableExtra("list");
//        listAdapter=new ListAdapter(employeeList,this);
//        StuLv.setAdapter(listAdapter);
//
//
//        StuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+employeeList.get(position).getId()));
//                    startActivity(intent);
//                }
//        });
        List<Branch> list=division.getDivisionList();
        if (branchAdapter==null){
            branchAdapter=new DepartmentAdapter<>(list,this);
            sLv.setAdapter(branchAdapter);
        }else{
            branchAdapter.refresh(list);
        }

        sLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch= (Branch) branchAdapter.getItem(position);
                Map<String,String> map=new HashMap<String, String>();
                map.put("token", HttpMode.getTOKEN(SBranchActivity.this));
                map.put("_method","GET");
                map.put("org_id",branch.getId());
                OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.EMPLOYEE,map,SBranchActivity.this);
            }
        });
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
                String s= (String) msg.obj;
                list1= JsonUtils.JsonEmp(s);
                Intent intent1=new Intent(SBranchActivity.this,SApproveTeacherList.class);
                intent1.putExtra("list",(Serializable) list1);
                startActivity(intent1);
            }
        }
    };
}
