package com.example.recruitdemo.SiNong;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.DepartmentAdapter;
import com.example.recruitdemo.Adapter.ListAdapter;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Branch;
import com.example.recruitdemo.UserBean.Department;
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
 * 市场部→选择部门   第三层 分部     实现主要功能：获得上一级传过来的item数据 并进行分解，
 * 将它在ListView显示，并通过Intent将此ListView的item传到下一级
 */
public class SDivision extends AppCompatActivity implements Callback{

    private ListView sLv,StuLv;
    private ImageView sFinish;
    private Department department;
    private List<Employee> employeeList;
    private List<Employee> list1;
    private Intent intent;
    private List<Branch> list2;
    private DepartmentAdapter<Division> divisionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdivision);
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


        Intent intent1=getIntent();
        department = (Department) intent1.getSerializableExtra("Department");

        /*
        市场部分部 员工列表进行注释
         */
//        employeeList= (List<Employee>) intent1.getSerializableExtra("list");
//        Log.e("employeeList----------",employeeList.size()+"");
//        ListAdapter listAdapter=new ListAdapter(employeeList,this);
//        StuLv.setAdapter(listAdapter);
//
//        StuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+employeeList.get(position).getId()));
//                startActivity(intent);
//            }
//        });


        Log.e("","aaaaa:"+department.getName());
        List<Division> list= department.getDepartmentList();

        if (divisionAdapter==null){
            divisionAdapter=new DepartmentAdapter<>(list,this);
            sLv.setAdapter(divisionAdapter);
        }else {
            divisionAdapter.refresh(list);
        }
        sLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(SDivision.this,SBranchActivity.class);
                Division division= (Division) divisionAdapter.getItem(position);
                intent.putExtra("Division",division);
                list2=division.getDivisionList();
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("token", HttpMode.getTOKEN(SDivision.this));
                    map.put("_method","GET");
                    map.put("org_id",division.getId());
                    OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.EMPLOYEE,map,SDivision.this);
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
                if (list2.size()!=0){
                    intent.putExtra("list",(Serializable) list1);
                    startActivity(intent);
                }else {
                    Intent intent1=new Intent(SDivision.this,SApproveTeacherList.class);
                    intent1.putExtra("list",(Serializable)list1);
                    startActivity(intent1);
                }

            }
        }
    };
}
