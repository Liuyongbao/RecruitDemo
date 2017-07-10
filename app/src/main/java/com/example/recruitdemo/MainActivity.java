package com.example.recruitdemo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recruitdemo.Adapter.FragmentAdapter;
import com.example.recruitdemo.UserBean.Edition;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.fragment.HomenFragment;
import com.example.recruitdemo.fragment.InformationFragment;
import com.example.recruitdemo.fragment.LinkmanFragment;
import com.example.recruitdemo.fragment.MineFragment;
import com.example.recruitdemo.fragment.StatisticsFragment;
import com.example.recruitdemo.updateversion.ToastUtils;
import com.example.recruitdemo.updateversion.UpdateStatus;
import com.example.recruitdemo.updateversion.UpdateVersionUtil;
import com.example.recruitdemo.updateversion.VersionInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 主页面    主要实现功能：fragment切换
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, Callback{

    private ViewPager mvp;
    private ImageView mimga,mimgb,mimgc,mimgd,mimge;
    private TextView mtva,mtvb,mtvc,mtvd,mtve;
    private RelativeLayout mcainera,mcainerb,mcainerc,mcainerd,mcainere;
    private HomenFragment ma;
    private InformationFragment mb;
    private LinkmanFragment mc;
    private StatisticsFragment md;
    private MineFragment me;

    private List<Fragment> list;
    private Edition edition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateVersionUtil.checkVersion(MainActivity.this, new UpdateVersionUtil.UpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, final VersionInfo versionInfo) {
                        // 判断回调过来的版本检测状态
                        switch (updateStatus) {
                            case UpdateStatus.YES:
                                //弹出更新提示
                                File file=new File(Environment.getExternalStorageDirectory()+"/updateVersion/moa.apk");
                                if(file.exists()){
                                    file.delete();
                                }
                                UpdateVersionUtil.showDialog(MainActivity.this,versionInfo);
                                break;
                            case UpdateStatus.NO:
                                //没有新版本
                                ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
                                break;
                            case UpdateStatus.NOWIFI:
                                //当前是非wifi网络
                                UpdateVersionUtil.showOnWifiDialog(MainActivity.this,versionInfo);
                                break;
                            case UpdateStatus.ERROR:
                                //检测失败
                                ToastUtils.showToast(getApplicationContext(), "检测失败，请稍后重试！");
                                break;
                            case UpdateStatus.TIMEOUT:
                                //链接超时
                                ToastUtils.showToast(getApplicationContext(), "链接超时，请检查网络设置!");
                                break;
                        }
                    }
                });





        initView();
        ma=new HomenFragment();
        mb=new InformationFragment();
        mc=new LinkmanFragment();
        md=new StatisticsFragment();
        me=new MineFragment();
        list=new ArrayList<Fragment>();
        list.add(mb);
        list.add(mc);
        list.add(ma);
        list.add(md);
        list.add(me);

        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager(),list);
        mvp.setAdapter(adapter);
        mvp.setCurrentItem(2, false);

        Qingqiu();
    }
    //请求版本更新的数据
    private void Qingqiu(){
        Map<String,String>map=new HashMap<>();
        map.put("_method", "GET");
        OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.LASTEST,map,this);

    }


    private void initView(){
        mimga=(ImageView)findViewById(R.id.mimga);
        mimgb=(ImageView)findViewById(R.id.mimgb);
        mimgc=(ImageView)findViewById(R.id.mimgc);
        mimgd=(ImageView)findViewById(R.id.mimgd);
        mimge=(ImageView)findViewById(R.id.mimge);
        mtva=(TextView)findViewById(R.id.mtva);
        mtvb=(TextView)findViewById(R.id.mtvb);
        mtvc=(TextView)findViewById(R.id.mtvc);
        mtvd=(TextView)findViewById(R.id.mtvd);
        mtve=(TextView)findViewById(R.id.mtve);
        mvp=(ViewPager)findViewById(R.id.mvp);
        mcainera=(RelativeLayout)findViewById(R.id.mcainera);
        mcainerb=(RelativeLayout)findViewById(R.id.mcainerb);
        mcainerc=(RelativeLayout)findViewById(R.id.mcainerc);
        mcainerd=(RelativeLayout)findViewById(R.id.mcainerd);
        mcainere=(RelativeLayout)findViewById(R.id.mcainere);
        mvp.setOnPageChangeListener(this);
        mcainera.setOnClickListener(this);
        mcainerb.setOnClickListener(this);
        mcainerc.setOnClickListener(this);
        mcainerd.setOnClickListener(this);
        mcainere.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        clear();
        switch(v.getId()){
            case R.id.mcainera:
                mtva.setTextColor(getResources().getColor(R.color.blues));
                mimga.setImageResource(R.drawable.gongzuos);
                mvp.setCurrentItem(2, false);
                break;
            case R.id.mcainerb:
                mtvb.setTextColor(getResources().getColor(R.color.blues));
                mimgb.setImageResource(R.drawable.xiaoxis);
                mvp.setCurrentItem(0, false);
                break;
            case R.id.mcainerc:
                mtvc.setTextColor(getResources().getColor(R.color.blues));
                mimgc.setImageResource(R.drawable.lianxirens);
                mvp.setCurrentItem(1, false);
                break;
            case R.id.mcainerd:
                mtvd.setTextColor(getResources().getColor(R.color.blues));
                mimgd.setImageResource(R.drawable.dings);
                mvp.setCurrentItem(3, false);
                break;
            case R.id.mcainere:
                mtve.setTextColor(getResources().getColor(R.color.blues));
                mimge.setImageResource(R.drawable.wodes);
                mvp.setCurrentItem(4, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clear();
        switch(position){
            case 2:
                mimga.setImageResource(R.drawable.gongzuos);
                mtva.setTextColor(getResources().getColor(R.color.blues));
                break;
            case 0:
                mimgb.setImageResource(R.drawable.xiaoxis);
                mtvb.setTextColor(getResources().getColor(R.color.blues));
                break;
            case 1:
                mimgc.setImageResource(R.drawable.lianxirens);
                mtvc.setTextColor(getResources().getColor(R.color.blues));
                break;
            case 3:
                mimgd.setImageResource(R.drawable.dings);
                mtvd.setTextColor(getResources().getColor(R.color.blues));
                break;
            case 4:
                mimge.setImageResource(R.drawable.wodes);
                mtve.setTextColor(getResources().getColor(R.color.blues));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void clear(){
        mimga.setImageResource(R.drawable.gongzuoa);
        mimgb.setImageResource(R.drawable.xiaoxia);
        mimgc.setImageResource(R.drawable.lianxirena);
        mimgd.setImageResource(R.drawable.dinga);
        mimge.setImageResource(R.drawable.wodea);
        mtva.setTextColor(getResources().getColor(R.color.greay));
        mtvb.setTextColor(getResources().getColor(R.color.greay));
        mtvc.setTextColor(getResources().getColor(R.color.greay));
        mtvd.setTextColor(getResources().getColor(R.color.greay));
        mtve.setTextColor(getResources().getColor(R.color.greay));
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("-------------",e+"");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

//        String data=response.body().string();
//        Message msg=handler.obtainMessage();
//        Log.e("ssss-------------",data);
//        msg.what=9;
//        msg.obj=data;
//        handler.sendMessage(msg);
    }

//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            if(msg.what==9){
//                String da= (String) msg.obj;
//
//                edition= JsonUtils.jsonEdition(da);
//
//                int a=Integer.parseInt(edition.getId());
//                Log.e("edt-----",edition.getName());
//                //版本更新
////                BaseAndroid.checkUpdate(MainActivity.this,a,HttpMode.UPLOAD
////                        ,edition.getNotes(),false);
//
//            }
//        }
//    };
}
