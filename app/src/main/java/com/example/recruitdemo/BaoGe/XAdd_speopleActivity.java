package com.example.recruitdemo.BaoGe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.MyAdapter;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Addpeople;
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

/**
 * 添加联系人界面，实现主要功能：从服务器获取各市场部的招生老师，并进行item点击返回传值
 */
public class XAdd_speopleActivity extends Activity implements AdapterView.OnItemClickListener, Callback{

    private GridView gv;
    private SharedUtils sharedUtils;
    private MyAdapter adapter;
    private List<Leadership> list;
    private ImageView back;
    private List<Boolean> booleanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xadd_speople);
        initView();
    }
    /*
    点击item返回传值
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent in=new Intent();
            in.putExtra("string", (Serializable) list.get(position));
            setResult(99,in);
            finish();
    }
    /*
    初始化控件
     */
    private void initView(){
        gv = (GridView)findViewById(R.id.gv);
        sharedUtils= MyApplication.sharedUtils;
        back=(ImageView)findViewById(R.id.mBark);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }
    /*
    初始化数据，上传数据到服务器
     */
    private void initData(){
        String staff_id=sharedUtils.getShared("staff_id",this);
        Map<String,String> map=new HashMap<>();
        map.put("token", HttpMode.getTOKEN(this));
        map.put("_method","GET");
        map.put("id",staff_id);
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.LEADER,map,this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //服务器返回给的数据
        String s=response.body().string();
        Log.e("AUDIT-----",s);
        Message msg=handler.obtainMessage();
        msg.obj=s;
        msg.what=1;
        handler.sendMessage(msg);
    }
    /*
    将服务器返回给的数据进行解析，通过list来将数据存储，并给GridView赋值
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                String s= (String) msg.obj;
                list= JsonUtils.Jsonemp(s);
                adapter=new MyAdapter(XAdd_speopleActivity.this,list);
                gv.setAdapter(adapter);
                gv.setOnItemClickListener(XAdd_speopleActivity.this);
            }
        }
    };

    /*
    GridView适配器
     */

    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Leadership> list;
        private MyAdapter(Context context,List<Leadership> list){
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
                convertView =View.inflate(context,R.layout.griditem,null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(list.get(position).getName());
            return convertView;
        }

    }

    class ViewHolder{
        private TextView tv;
    }
}
