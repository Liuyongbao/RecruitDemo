package com.example.recruitdemo.SiNong;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.recruitdemo.Adapter.ListAdapter;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Employee;

import java.util.ArrayList;
import java.util.List;

public class SApproveTeacherList extends AppCompatActivity {
    private ListView sLv;
    private ImageView sFinish;
    private List<Employee> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sapprove_teacher);
        initView();
    }
    private void initView(){
        sLv= (ListView) findViewById(R.id.sLv);
        sFinish=(ImageView)findViewById(R.id.sFinish);
        Intent intent=getIntent();
        list1= (List<Employee>) intent.getSerializableExtra("list");

        ListAdapter listAdapter=new ListAdapter(list1,this);
        sLv.setAdapter(listAdapter);
        sFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list1.get(position).getId()));
                startActivity(intent);
            }
        });
    }
}
