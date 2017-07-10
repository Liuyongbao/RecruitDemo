package com.example.recruitdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.ViewPagerAdapter;
import com.example.recruitdemo.ApprovalActivity;
import com.example.recruitdemo.BaoGe.BSearchActivity;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.EnterActivity;
import com.example.recruitdemo.MyStudentActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Number;
import com.example.recruitdemo.UserBean.ShenPi;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.JsonUtils;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.Utils.ViewPagerScroller;
import com.example.recruitdemo.XiaoHui.XLoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 张金瑞 on 2017/3/20.   第一个页面  首页
 * 实现主要功能：1、实现广告轮播；
 *               2、实现消息提示
 *               3、实现Activity跳转
 */
public class HomenFragment extends Fragment implements View.OnClickListener, Callback{
    private LinearLayout hEnter,hApprove,hStudent;
    private ImageView hSearch;
    private ViewPager vp;
    private TextView tv_sp,tv_xs;
    /*
    viewpager所用到的
     */
    private ImageView[] bottomImg;
    private List<ImageView> imgList;
    private LinearLayout layout;
    private int[] imgs = {R.mipmap.t1,R.mipmap.t2,R.mipmap.t4,R.mipmap.t5};
    private int currentItem = 0;
    private boolean issAuto = true;
    private boolean isTouch = false;
    private SharedUtils sharedUtils;
    private int state;
    private Thread thread;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        return view;
    }

    /*
     *初始化ViewPager,广告轮播功能
     */
    private void initViewPager(){
        imgList = new ArrayList<ImageView>();

        /*
        获得广告条目数量，进行循环遍历
         */
        for(int i= 0;i < imgs.length;i++){
            //声明一个ImageView
            ImageView img = new ImageView(getActivity());
            /**
             * 对ImageView进行自定义属性配置，
             * 并添加到list集合中去
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT
            );
            img.setLayoutParams(params);
            img.setImageResource(imgs[i]);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            imgList.add(img);
        }
        //设置适配器
        ViewPagerAdapter adapter = new ViewPagerAdapter(imgList,getActivity());
        vp.setAdapter(adapter);
    }
    /*
    初始化控件
     */
    private void initView(View view){
        hEnter=(LinearLayout)view.findViewById(R.id.hEnter);
        hApprove=(LinearLayout)view.findViewById(R.id.hApprove);
        hStudent=(LinearLayout)view.findViewById(R.id.hStudent);
        hSearch=(ImageView)view.findViewById(R.id.hSearch);
        vp=(ViewPager)view.findViewById(R.id.hViewPager);
        layout=(LinearLayout)view.findViewById(R.id.layout);
        tv_sp= (TextView) view.findViewById(R.id.tv_sp);
        tv_xs= (TextView) view.findViewById(R.id.tv_xs);
        ViewPagerScroller scroller=new ViewPagerScroller(getActivity());
        scroller.initViewPagerScroll(vp);
        sharedUtils= MyApplication.sharedUtils;
        state=sharedUtils.getShared_int("state",getActivity());
        //判断状态是不是登录状态，如果是就上传数据到服务器来获取消息提醒所需要的消息数量
        Map<String,String> map=new HashMap<>();
        if (state!=0){
            map.put("token",HttpMode.getTOKEN(getActivity()));
            map.put("_method","GET");
            OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.INFO,map,this);
        }else{
            tv_sp.setVisibility(View.GONE);
            tv_xs.setVisibility(View.GONE);
        }

        isTouch=false;
        initViewPager();
        initBottom();
        issAuto=true;
        hEnter.setOnClickListener(this);
        hApprove.setOnClickListener(this);
        hStudent.setOnClickListener(this);

    }
    /*
    点击跳转Activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hEnter:
                if (state!=0){
                    Intent intent1=new Intent(getActivity(), EnterActivity.class);
                    startActivity(intent1);
                }else{
                    Intent in=new Intent(getActivity(), XLoginActivity.class);
                    startActivity(in);
                }
                break;
            case R.id.hApprove:
                Intent intent2=new Intent(getActivity(), ApprovalActivity.class);
                startActivity(intent2);
                break;
            case R.id.hStudent:
                sharedUtils.saveShared_int("status",0,getActivity());
                Intent intent3=new Intent(getActivity(), MyStudentActivity.class);
                startActivity(intent3);
                break;
            case R.id.hSearch:
                Intent intent4=new Intent(getActivity(), BSearchActivity.class);
                startActivity(intent4);
                break;

        }
    }
    /*
    初始化广告轮播ViewPager下面小圆点的属性及轮播状态
     */
    public void initBottom(){
        bottomImg = new ImageView[imgList.size()];
        for(int j = 0;j<bottomImg.length; j++){
            ImageView bottom = new ImageView(getActivity());
            /*
            设置ImageView的属性配置
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50,50);
            params.setMargins(5,0,5,0);
            bottom.setLayoutParams(params);
            if(j==0){
                bottom.setImageResource(R.mipmap.yd);
            }else{
                bottom.setImageResource(R.mipmap.unyd);
            }
            bottomImg[j] = bottom;
            layout.addView(bottom);
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                for (int i = 0; i < bottomImg.length; i++) {
                    int p = arg0 % imgList.size();
                    if (i == p) {
                        bottomImg[i].setImageResource(R.mipmap.yd);
                    } else {
                        bottomImg[i].setImageResource(R.mipmap.unyd);
                    }
                }
                // 当前显示的position赋值给成员变量，用于手动滑动中
                currentItem = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
				/*
				 * 判断是否存在手动滑动 挡手动滑动时将inTouch的编制设为true
				 */
                if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                    isTouch = false;
                } else {
                    isTouch = true;
                }
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
        msg.what=1;
        msg.obj=s;
        handler.sendMessage(msg);
    }
    /**
     * 特别重要，必须得写一个线程类，否则会出现线程安全问题
     */
    class MyThread implements Runnable{
        @Override
        public void run() {
            while(issAuto){
                try {
                    Thread.sleep(5000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==0){
                if (isTouch) {
                    return;
                }
                currentItem++;
                vp.setCurrentItem(currentItem);
            }
            /*
             * 将服务器返回到的数据进行解析，并进行判断
             */
            if (msg.what==1){
                String s= (String) msg.obj;
                Number number=JsonUtils.JsonNumber(s);
                if (!number.getStatus_id().equals("200")){
                    Toast.makeText(getActivity(),"登录超时,请重新登录",Toast.LENGTH_SHORT).show();
                    sharedUtils.saveShared_int("state",0,getActivity());
                }
                if (number!=null){
                    /*
                    对获取到的数据进行判断
                    如果不是0的话，就将textView显示、赋值，否则就让它隐藏
                     */
                    int customer_number=number.getCustomer_number();
                    if (customer_number!=0){
                        tv_xs.setVisibility(View.VISIBLE);
                        tv_xs.setText(customer_number+"");
                    }else {
                        tv_xs.setVisibility(View.GONE);
                    }
                    int doing_audit_number=number.getDoing_audit_number();
                    if (doing_audit_number!=0){
                        tv_sp.setVisibility(View.VISIBLE);
                        tv_sp.setText(doing_audit_number+"");
                    }else {
                        tv_sp.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        issAuto=true;
        thread=new Thread(new MyThread());
        thread.start();
        state=sharedUtils.getShared_int("state",getActivity());

        Log.e("ssss","onResume");
        if (state!=0){
            Map<String,String> map=new HashMap<>();
            map.put("token",HttpMode.getTOKEN(getActivity()));
            map.put("_method","GET");
            OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.INFO,map,this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        issAuto=false;
        thread.interrupt();
        Log.e("ssss","onRPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ssss","onDes");
    }

}
