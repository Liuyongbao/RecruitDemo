package com.example.recruitdemo.SiNong;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.ApproveListAdapter;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Number;
import com.example.recruitdemo.UserBean.ShenPi;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.XiaoHui.XApprovalDetailsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 审批→我发起的审批   实现主要功能：通过ListView展示数据
 */
public class SMylaunchActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener, Callback{

    private ImageView sfinish;
    private ListView slv;
    private List<ShenPi> list;
    private SharedUtils sharedUtils;
    private ApproveListAdapter adapter;
    private TextView tv;

    private View pro;
    private ProgressBar pb;
    private int LastItem;//总条目数
    private boolean flag = true;
    private int i = 1;//页码数
    private List<ShenPi> listAll = new ArrayList<>();
    private List<ShenPi> listAll1 = new ArrayList<>();
    private Number number;
    private int my_audit_number;//发起审批数量
    private int done_audit_number;//我的审批数量
    private TextView tv_shu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylaunch);
        initView();
    }

    private void initView(){
        sfinish=(ImageView)findViewById(R.id.sfinish);
        slv = (ListView)findViewById(R.id.sLv);
        tv=(TextView)findViewById(R.id.tv);
        sharedUtils= MyApplication.sharedUtils;
        pro = View.inflate(SMylaunchActivity.this,R.layout.pro,null);
        pb = (ProgressBar) pro.findViewById(R.id.pb);
        tv_shu= (TextView) findViewById(R.id.tv_shu);

        downNumber();

        Map<String ,String> map = new HashMap<String ,String >();
        map.put("token", HttpMode.getTOKEN(this));
        map.put("_method","GET");
        map.put("type",sharedUtils.getShared("type",this));

        map.put("curPage","1");
        Log.e("token", HttpMode.getTOKEN(this));
        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.AUDIT, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = ss;
                handler.sendMessage(message);
            }
        });

        sfinish.setOnClickListener(this);
        slv.setOnItemClickListener(this);

        if(sharedUtils.getShared("type",this).equals("1")){
            tv.setText("我发起的审批");
        }else if(sharedUtils.getShared("type",this).equals("2")){
            tv.setText("待我审批");
        }else if(sharedUtils.getShared("type",this).equals("3")){
            tv.setText("我的审批");
        }


        //给适配器设置滑动监听
        slv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("item======",LastItem+"");
                if (SCROLL_STATE_IDLE==scrollState&&LastItem==adapter.getCount()&&flag==true){
                    pro.setVisibility(View.VISIBLE);
                    flag = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            moreitem();
                            flag=true;
                            pro.setVisibility(View.GONE);
                        }
                    },3000);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(sharedUtils.getShared("type",SMylaunchActivity.this).equals("1")){
                    if (my_audit_number==0){
                        LastItem=1;
                    }else {
                        LastItem = firstVisibleItem+visibleItemCount-1;
                    }

                    if(my_audit_number==LastItem&&my_audit_number>=10) {
                        Toast.makeText(SMylaunchActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
                        slv.removeFooterView(pro);
                    }
                }else if(sharedUtils.getShared("type",SMylaunchActivity.this).equals("2")){
                    slv.removeFooterView(pro);
                }else if(sharedUtils.getShared("type",SMylaunchActivity.this).equals("3")){
                    if (done_audit_number==0){
                        LastItem=1;
                    }else {
                        LastItem = firstVisibleItem+visibleItemCount-1;
                    }
                        if (done_audit_number==LastItem&&done_audit_number>=10){
                            Toast.makeText(SMylaunchActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
                            slv.removeFooterView(pro);
                        }

                    }
                }
        });
    }

    //加载更多数据
    private void moreitem() {
        i++;
       downLoad();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sfinish:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShenPi shenPi=listAll.get(position);
        Intent intent=new Intent(this, XApprovalDetailsActivity.class);
        intent.putExtra("shenpi",shenPi);
        startActivity(intent);
        sharedUtils.saveShared_int("listAll",0,this);
        sharedUtils.saveShared_int("int",position,this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s = response.body().string();
        Message msg = handler.obtainMessage();
        msg.what = 0;
        msg.obj = s;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){   String s = (String) msg.obj;
                list = JsonUtils.getDaiShenPi(s);

                listAll.addAll(list);
                if(sharedUtils.getShared_int("listAll",SMylaunchActivity.this)==1){
                    listAll.removeAll(list);
                    listAll.remove(sharedUtils.getShared_int("int",SMylaunchActivity.this));
                    sharedUtils.saveShared_int("listAll",0,SMylaunchActivity.this);
                }
                Log.e("ListAll-------",listAll.size()+"");
                    if(adapter==null){
                        adapter = new ApproveListAdapter(SMylaunchActivity.this,listAll);
                        slv.setAdapter(adapter);
                    }else{
                        adapter.Refresh(listAll);
                    }
                adapter.notifyDataSetChanged();

                if(adapter.getCount()==0){
                    tv_shu.setVisibility(View.VISIBLE);
                    slv.setDividerHeight(0);
                }else{
                    tv_shu.setVisibility(View.GONE);
                }
            }
            if (msg.what == 1){
                String ss = (String) msg.obj;
                Log.e("数字请求------------",ss+"");
                number = JsonUtils.JsonNumber(ss);
                my_audit_number = number.getMy_audit_number();
                done_audit_number = number.getTotal_audit_number();


                if(sharedUtils.getShared("type",SMylaunchActivity.this).equals("1")){
                    if(my_audit_number<10){
                        slv.removeFooterView(pro);
                    }else{
                        slv.addFooterView(pro);
                    }
                }

                if(sharedUtils.getShared("type",SMylaunchActivity.this).equals("3")){
                    if(done_audit_number<10){
                        slv.removeFooterView(pro);
                    }else{
                        slv.addFooterView(pro);
                    }
                }

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedUtils.getShared_int("listAll",SMylaunchActivity.this)==0){

        }else{

            Map<String ,String> map = new HashMap<String ,String >();
            map.put("token", HttpMode.getTOKEN(this));
            map.put("_method","GET");
            map.put("type",sharedUtils.getShared("type",this));

            map.put("curPage",String.valueOf(i));
            Log.e("token", HttpMode.getTOKEN(this));
            OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.AUDIT, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ss = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    message.obj = ss;
                    handler.sendMessage(message);
                }
            });
        }


    }
    private void downLoad(){

        Map<String ,String> map = new HashMap<String ,String >();
        map.put("token", HttpMode.getTOKEN(this));
        map.put("_method","GET");
        map.put("type",sharedUtils.getShared("type",this));

        map.put("curPage",String.valueOf(i));
        Log.e("token", HttpMode.getTOKEN(this));
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.AUDIT,map,this);
    }

    private void downNumber(){
        Map<String ,String > numberMap = new HashMap<String ,String>();
        numberMap.put("token", HttpMode.getTOKEN(this));
        numberMap.put("_method","GET");
        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.INFO, numberMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = ss;
                handler.sendMessage(message);
            }
        });
    }
}
