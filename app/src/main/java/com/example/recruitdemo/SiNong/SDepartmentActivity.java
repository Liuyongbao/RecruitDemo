package com.example.recruitdemo.SiNong;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.recruitdemo.Adapter.DepartmentAdapter;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.College;
import com.example.recruitdemo.UserBean.Department;
import com.example.recruitdemo.UserBean.Division;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 市场部→选择部门   市场部  实现主要功能：将解析到的数据通过ListView显示，
 *    并将每个item（Division）通过Intent传到下一级部门
 */
public class SDepartmentActivity extends AppCompatActivity implements Callback{
    private ListView sLv;
    private ImageView sFinish;
    private College college;
    private SharedUtils  sharedUtils;
    private List<Department> list;
    private Department department;
    private List<Division> list3;
    private Intent intent;
    private DepartmentAdapter<Department> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdepartment);
        initView();
    }
    private void initView(){
        sLv= (ListView) findViewById(R.id.sLv);
        sFinish=(ImageView)findViewById(R.id.eFinish_dq);
        sFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedUtils = MyApplication.sharedUtils;
        String s=sharedUtils.getShared("data",this);
        List<College> clist=JsonUtils.JsonOrg(s);
        for(int i=0;i<clist.size();i++){
            college=clist.get(i);
            list=college.getCollegeList();
        }
        adapter=new DepartmentAdapter(list,this);
        sLv.setAdapter(adapter);

        sLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(SDepartmentActivity.this,SDivision.class);
                department= (Department) adapter.getItem(position);
                intent.putExtra("Department",department);

                list3=department.getDepartmentList();
                Log.e("name",""+list3.size());

//                if (list3.size()!=0){
//                    startActivity(intent);
//                }else{
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("token", HttpMode.getTOKEN(SDepartmentActivity.this));
                    map.put("_method","GET");
                    map.put("org_id",department.getId());
                Log.e("org_id----------",department.getId());
                    OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.EMPLOYEE,map,SDepartmentActivity.this);
//                }
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
                List<Employee> list=JsonUtils.JsonEmp(s);
                if (list3.size()!=0){
                    intent.putExtra("list",(Serializable) list);
                    startActivity(intent);
                }else {
                    Intent intent1=new Intent(SDepartmentActivity.this,SApproveTeacherList.class);
                    intent1.putExtra("list",(Serializable) list);
                    startActivity(intent1);
                }

            }

        }
    };
}
