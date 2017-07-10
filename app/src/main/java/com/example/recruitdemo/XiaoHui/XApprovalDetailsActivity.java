package com.example.recruitdemo.XiaoHui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.Details_Adapter;
import com.example.recruitdemo.CustomWidget.ListViewForScrollView;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.FinishAll.BaseActivity;
import com.example.recruitdemo.FinishAll.CollectorActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.ApproveBean;
import com.example.recruitdemo.UserBean.Flows;
import com.example.recruitdemo.UserBean.ShenPi;
import com.example.recruitdemo.UserBean.ShenPi1;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 审批→我发起的审批→
 */
public class XApprovalDetailsActivity extends BaseActivity implements View.OnClickListener, Callback{

    private ListViewForScrollView listViewForScrollView;
    private ApproveBean approveBean;
    private List<ApproveBean> list;
    private Details_Adapter adapter;
    private TextView tx,name,id,org_name,time,details,details1,tv_sp;
    private ShenPi shenPi;
    private ImageView sFinish;
    private ShenPi1 shenPi1;
    private List<Flows> flowsList;
    private LinearLayout ll;
    private RelativeLayout agree,zhuanjiao,jujue;
    private SharedUtils sharedUtils;
    private String flow_id;
    private String status_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xapproval_details);
        CollectorActivity.addActivity(this);
        initView();
    }

    public void initView(){
        listViewForScrollView=(ListViewForScrollView)findViewById(R.id.lv);
        tx=(TextView)findViewById(R.id.tx);
        name=(TextView)findViewById(R.id.name);
        id=(TextView)findViewById(R.id.id);
        org_name=(TextView)findViewById(R.id.org_name);
        time=(TextView)findViewById(R.id.time);
        details=(TextView)findViewById(R.id.details);
        details1=(TextView)findViewById(R.id.details1);

        sFinish=(ImageView)findViewById(R.id.sFinish);
        ll=(LinearLayout)findViewById(R.id.ll);
        tv_sp=(TextView)findViewById(R.id.tv1sp) ;
        agree=(RelativeLayout) findViewById(R.id.agree);
        zhuanjiao=(RelativeLayout) findViewById(R.id.zhuanjiao);
        jujue=(RelativeLayout) findViewById(R.id.jujue);

        agree.setOnClickListener(this);
        zhuanjiao.setOnClickListener(this);
        jujue.setOnClickListener(this);

        sharedUtils= MyApplication.sharedUtils;

        if(sharedUtils.getShared("type",XApprovalDetailsActivity.this).equals("2")){
            ll.setVisibility(View.VISIBLE);
            tv_sp.setText("待我审批");
        }else if(sharedUtils.getShared("type",XApprovalDetailsActivity.this).equals("1")){
            ll.setVisibility(View.GONE);
            tv_sp.setText("我发起的审批");
        }else if(sharedUtils.getShared("type",XApprovalDetailsActivity.this).equals("3")){
            ll.setVisibility(View.GONE);
            tv_sp.setText("我已审批");
        }

        sFinish.setOnClickListener(this);

        shenPi= (ShenPi) getIntent().getSerializableExtra("shenpi");
        id.setText(shenPi.getId());
        tx.setText(shenPi.getEmployee_name());
        name.setText(shenPi.getEmployee_name());
        org_name.setText(shenPi.getOrg_name());
        time.setText(shenPi.getStart_time());
        details.setText(shenPi.getIn_status_name());
        details1.setText(shenPi.getNew_status_name());

        initListView(shenPi.getId());
    }

    public void initListView(String id){
        Map<String,String> map=new HashMap<>();
        map.put("_method","GET");
        map.put("token", HttpMode.getTOKEN(this));
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.AUDIT+"/"+id,map,this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sFinish:
                 finish();
                 break;
            case R.id.agree:
                Intent intent=new Intent(this,XApprovalDetailsEditActivity.class);
                intent.putExtra("status","2");
                intent.putExtra("shenpiClass",shenPi);
                startActivity(intent);
                break;
            case R.id.zhuanjiao:
                status_id="3";
                break;
            case R.id.jujue:
                Intent intent1=new Intent(this,XApprovalDetailsEditActivity.class);
                intent1.putExtra("status","4");
                intent1.putExtra("shenpiClass",shenPi);
                startActivity(intent1);
                break;
        }
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
            super.handleMessage(msg);
            if(msg.what==0){
                String s= (String) msg.obj;
                shenPi1= JsonUtils.jsonShenPi1(s);
                Log.e("ssssisisis-------",s);

                list=new ArrayList<ApproveBean>();
                if (shenPi1!=null){
                    flowsList=shenPi1.getFlowsList();
                    for(int i=0;i<shenPi1.getFlowsList().size()+1;i++){
                        approveBean=new ApproveBean();
                        if(i==0){
                            approveBean.setAvatar(shenPi1.getEmployee_name());
                            approveBean.setImg(R.mipmap.all_completed);
                            approveBean.setName(shenPi1.getEmployee_name());
                            approveBean.setState("发起申请");
                            approveBean.setTime1(shenPi1.getFlowsList().get(0).getFlows_start_time());
                        }else{
                            approveBean.setAvatar(shenPi1.getFlowsList().get(i-1).getFl_employee_name());
                            approveBean.setName(shenPi1.getFlowsList().get(i-1).getFl_employee_name());

                            if (shenPi1.getFlowsList().get(i-1).getFlows_notes()==null){

                            }else {
                                approveBean.setNotes(shenPi1.getFlowsList().get(i-1).getFlows_notes());
                                Log.e("aaaaaa-------",shenPi1.getFlowsList().get(i-1).getFlows_notes());
                            }

                            if(shenPi1.getFlowsList().get(i-1).getFl_status_name().equals("审批通过")){
                                approveBean.setTm(shenPi1.getFlowsList().get(i-1).getFlows_end_time());
                                approveBean.setTime1(shenPi1.getFlowsList().get(i-1).getFlows_start_time());
                                approveBean.setImg(R.mipmap.all_completed);
                                approveBean.setState(shenPi1.getFlowsList().get(i-1).getFl_status_name());
                            }else if(shenPi1.getFlowsList().get(i-1).getFl_status_name().equals("已同意")){
                                approveBean.setTm(shenPi1.getFlowsList().get(i-1).getFlows_end_time());
                                approveBean.setTime1(shenPi1.getFlowsList().get(i-1).getFlows_start_time());
                                approveBean.setImg(R.mipmap.all_completed);
                                approveBean.setState(shenPi1.getFlowsList().get(i-1).getFl_status_name());
                            }else if(shenPi1.getFlowsList().get(i-1).getFl_status_name().equals("已拒绝")){
                                approveBean.setTm(shenPi1.getFlowsList().get(i-1).getFlows_end_time());
                                approveBean.setTime1(shenPi1.getFlowsList().get(i-1).getFlows_start_time());
                                approveBean.setImg(R.mipmap.jujue1);
                                approveBean.setState(shenPi1.getFlowsList().get(i-1).getFl_status_name());
                            }else{
                                approveBean.setImg(R.mipmap.dengdai);
                                if(shenPi1.getFlowsList().get(i-1).getFlows_start_time()==null){
                                    approveBean.setTime1("未开始审核");
                                }else{
                                    approveBean.setTime1(shenPi1.getFlowsList().get(i-1).getFlows_start_time());
                                }
                                approveBean.setState("待审核");
                            }

                        }
                        list.add(approveBean);
                    }
                }
                    adapter=new Details_Adapter(XApprovalDetailsActivity.this,list);
                    listViewForScrollView.setAdapter(adapter);


                }
        }
    };

//    private void  setstatus(String status){
//        Map<String,String> map=new HashMap<>();
//        map.put("token",HttpMode.getTOKEN(this));
//        sharedUtils.saveShared_int("listAll",1,this);
//        map.put("_method","PUT");
//        for(int i=0;i<shenPi1.getFlowsList().size();i++){
//            if(shenPi1.getFlowsList().get(i).getFl_employee_name().equals(sharedUtils.getShared("name",this))){
//                flow_id=shenPi1.getFlowsList().get(i).getFlows_id();
//            }
//        }
//        Log.e("flow_id",flow_id);
//
//        map.put("status_id",status);
//        Log.e("status",status);
//
//        if(status.equals("3")){
//            map.put("employee_id","");
//        }
//
//        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.AUDIT+"/"+shenPi1.getId()+"/"+flow_id, map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s=response.body().string();
//            }
//        });
//
//        Toast.makeText(this,"审批成功,可在我已审批中查看数据",Toast.LENGTH_LONG).show();
//        finish();
//    }

}
