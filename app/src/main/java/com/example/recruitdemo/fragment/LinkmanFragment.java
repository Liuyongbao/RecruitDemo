package com.example.recruitdemo.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recruitdemo.Adapter.MyAdapter;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.R;
import com.example.recruitdemo.SiNong.SDepartmentActivity;
import com.example.recruitdemo.UserBean.College;
import com.example.recruitdemo.UserBean.Group;
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
 * Created by 张金瑞 on 2017/3/20.   联系人  市场部
 * 实现主要功能：1、获取外部联系人，既通讯录联系人
 *               2、获取服务器返回的市场部 所属部门及各部门领导、员工信息
 */
public class LinkmanFragment extends Fragment implements View.OnClickListener, Callback {

    private ExpandableListView elv;//可扩张的listView
    private List<Group> grlist;
    private LinearLayout lAddress;
    private SharedUtils sharedUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkman, container, false);
        initView(view);
        elv.setGroupIndicator(null);
        return view;
    }


    /*
    初始化控件
    上传数据到服务器
     */
    private void initView(View view) {
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        lAddress = (LinearLayout) view.findViewById(R.id.lAddress);
        sharedUtils= MyApplication.sharedUtils;

        Map<String, String> map = new HashMap<>();
        map.put("token", HttpMode.getTOKEN(getActivity()));
        map.put("_method", "GET");
        OkUtils.UploadSJ(HttpMode.HTTPURL + HttpMode.ORG, map, this);

        lAddress.setOnClickListener(this);
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(getActivity(), SDepartmentActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转到手机联系人界面(隐式跳转)
            case R.id.lAddress:
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        uri);
                startActivityForResult(intent, 0);
                break;
        }
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int state=sharedUtils.getShared_int("state",getContext());
            if (msg.what == 0) {
                /*
                判断现在状态是不是登录状态，如果是，就对服务器返回给的数据进行解析并归类
                并给ExpandableListView赋值
                 */
               if(state==1){
                   String s = (String) msg.obj;
                   sharedUtils.saveShared("data",s,getActivity());
                   grlist = new ArrayList<Group>();
                   List<College> g1list = JsonUtils.JsonOrg(s);
                   Group g = new Group("市场部");
                   g.setFlist(g1list);
                   grlist.add(g);
                   MyAdapter adapter = new MyAdapter(getActivity(), grlist);
                   elv.setAdapter(adapter);
               }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }
                //处理返回的data,获取选择的联系人信息
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                if (contacts[1]!=null)
                    call(contacts[1]);
                else
                    Toast.makeText(getActivity(),"未获取到该联系人",Toast.LENGTH_SHORT).show();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }
    private String[] getPhoneContacts(Uri uri){
        String[] contact=new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                try {
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }catch (Exception e){
                    contact[1]=null;
                }

            }
            phone.close();
            cursor.close();
        }
        else
        {
            return null;
        }
        return contact;
    }

}
