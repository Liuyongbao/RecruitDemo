package com.example.recruitdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recruitdemo.CustomWidget.ErweimaDialog;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.CustomWidget.MyDialog;
import com.example.recruitdemo.R;
import com.example.recruitdemo.Utils.SharedUtils;
import com.example.recruitdemo.XiaoHui.XLoginActivity;
import com.example.recruitdemo.XiaoHui.XModify_password;

/**
 * Created by 张金瑞 on 2017/3/20.   我的页面
 * 实现主要功能：1、点击跳转到登录页面
 *               2、登录完成后 对用户信息进行展示
 *               3、对登录状态进行改变，并保存
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private TextView mLogin,avatar,tv_bm;
    private SharedUtils sharedUtils;
    private Button btn_tui;
    private ImageView img_er;
    private RelativeLayout Modify_password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mine,container,false);
        initView(view);
        return view;
    }
    /*
    初始化控件
     */
    private void initView(View view){
        mLogin=(TextView)view.findViewById(R.id.mLogin);
        avatar=(TextView)view.findViewById(R.id.avatar);
        tv_bm=(TextView)view.findViewById(R.id.tv_bm);
        btn_tui=(Button)view.findViewById(R.id.btn_tui);
        img_er= (ImageView) view.findViewById(R.id.img_er);
        Modify_password= (RelativeLayout) view.findViewById(R.id.Modify_password);

        sharedUtils= MyApplication.sharedUtils;

        btn_tui.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        img_er.setOnClickListener(this);
        Modify_password.setOnClickListener(this);
        initLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        initLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mLogin:

                int state=sharedUtils.getShared_int("state",getContext());
                if(state==0){
                    Intent intent=new Intent(getActivity(), XLoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.btn_tui:

                showDialog();
                break;

            case R.id.img_er:
                ErweimaDialog erweimaDialog = new ErweimaDialog(getContext());
                erweimaDialog.show();
                break;
            case R.id.Modify_password:
                Intent intent=new Intent(getActivity(), XModify_password.class);
                startActivity(intent);
                break;
        }
    }

    //登陆后初始化
    public void initLogin(){
        String name=sharedUtils.getShared("name",getContext());
        String bm_name=sharedUtils.getShared("bm_name",getContext());
        int state=sharedUtils.getShared_int("state",getContext());

        if(state==1){
            mLogin.setText(name);
            mLogin.setBackgroundResource(R.color.white);
            mLogin.setTextColor(getResources().getColor(R.color.black));
            mLogin.setGravity(Gravity.LEFT);
            avatar.setText(name);
            avatar.setBackgroundResource(R.drawable.img50_blue);
            tv_bm.setText(bm_name);
            btn_tui.setVisibility(View.VISIBLE);
            Modify_password.setVisibility(View.VISIBLE);

        }else{
            mLogin.setText("马上登录");
            mLogin.setBackgroundResource(R.color.huise);
            mLogin.setTextColor(getResources().getColor(R.color.white));
            mLogin.setGravity(Gravity.CENTER);
            btn_tui.setVisibility(View.GONE);
            Modify_password.setVisibility(View.GONE);
            avatar.setBackgroundResource(R.mipmap.shangchuan);
        }
    }

    public void showDialog(){
        MyDialog dialog=new MyDialog(getContext());
        dialog.setOnClickListener(new MyDialog.MyClickListener() {
            @Override
            public void confirm() {
                mLogin.setText("马上登陆");
                mLogin.setBackgroundResource(R.color.huise);
                mLogin.setTextColor(getResources().getColor(R.color.white));
                mLogin.setGravity(Gravity.CENTER);
                avatar.setBackgroundResource(R.mipmap.shangchuan);
                tv_bm.setText("未加入部门");
                btn_tui.setVisibility(View.GONE);
                Modify_password.setVisibility(View.GONE);
                sharedUtils.saveShared("token","",getContext());
                sharedUtils.saveShared_int("state",0,getContext());
                sharedUtils.saveShared("EnterNumber","",getContext());
                initLogin();
            }

            @Override
            public void cancel() {

            }
        });
        dialog.show();
    }
}
