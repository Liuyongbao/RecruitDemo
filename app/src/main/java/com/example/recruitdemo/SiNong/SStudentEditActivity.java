package com.example.recruitdemo.SiNong;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitdemo.ApprovalActivity;
import com.example.recruitdemo.CustomDialog;
import com.example.recruitdemo.CustomDog;
import com.example.recruitdemo.CustomWidget.MyApplication;
import com.example.recruitdemo.FinishAll.BaseActivity;
import com.example.recruitdemo.FinishAll.CollectorActivity;
import com.example.recruitdemo.R;
import com.example.recruitdemo.RegionActivity;
import com.example.recruitdemo.UserBean.Customer;
import com.example.recruitdemo.UserBean.Customer1;
import com.example.recruitdemo.Utils.HttpMode;
import com.example.recruitdemo.Utils.OkUtils;
import com.example.recruitdemo.Utils.SharedUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 学生信息修改、编辑页面；实现主要功能：将需要修改的学生信息进行修改、编辑，并再次提交给服务器进行保存
 */
public class SStudentEditActivity extends BaseActivity implements View.OnClickListener,Callback{
    private TextView tv_name, tv_sex, tv_id, tv_contact,
            tv_address, tv_zone_id, tv_relative,
            tv_relation, tv_relative_contact, tv_notes;
    private Button btn_bc;
    private TextView tv_detail;
    private ArrayAdapter adapter;
    private  AlertDialog.Builder builder;
    private SharedUtils shareUtils;
    private String str;
    private Customer1 c;
    private Map<String, String> map = new HashMap<String, String>();
    private String uri = "http://192.168.4.188/MOA/app/indent/";
    private Customer cc;
    private int area_id;
    private ImageView sFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sstudent_edit);
        initView();
    }
    private void initView(){
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_zone_id = (TextView) findViewById(R.id.tv_zone_id);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_relative = (TextView) findViewById(R.id.tv_relative);
        tv_relation = (TextView) findViewById(R.id.tv_relation);
        tv_relative_contact = (TextView) findViewById(R.id.tv_relative_contact);
        tv_notes = (TextView) findViewById(R.id.tv_notes);
        sFinish=(ImageView)findViewById(R.id.sFinish);

        sFinish.setOnClickListener(this);

        shareUtils= MyApplication.sharedUtils;
        c = (Customer1) getIntent().getSerializableExtra("bianji");
        tv_name.setText(c.getName());
        tv_sex.setText(c.getSex());
        tv_id.setText(c.getId());
        tv_zone_id.setText(c.getZone());
        tv_contact.setText(c.getPhone());
        tv_address.setText(c.getAddress());
        tv_relative.setText(c.getParents());
        tv_relation.setText(c.getGuanxi());
        tv_relative_contact.setText(c.getParents_phone());
        tv_notes.setText(c.getNotes());

        LinearLayout mL1 = (LinearLayout) findViewById(R.id.mL1);
        LinearLayout mL2 = (LinearLayout) findViewById(R.id.mL2);
        LinearLayout ml3= (LinearLayout) findViewById(R.id.mL3);
        LinearLayout mL4 = (LinearLayout) findViewById(R.id.mL4);
        LinearLayout mL5 = (LinearLayout) findViewById(R.id.mL5);
        LinearLayout mL6 = (LinearLayout) findViewById(R.id.mL6);
        LinearLayout mL7 = (LinearLayout) findViewById(R.id.mL7);
        LinearLayout mL8 = (LinearLayout) findViewById(R.id.mL8);
        LinearLayout mL9 = (LinearLayout) findViewById(R.id.mL9);
        LinearLayout mL10 = (LinearLayout) findViewById(R.id.mL10);

        btn_bc = (Button) findViewById(R.id.btn_bcx);
        mL1.setOnClickListener(this);
        mL2.setOnClickListener(this);
        ml3.setOnClickListener(this);
        mL4.setOnClickListener(this);
        mL5.setOnClickListener(this);
        mL6.setOnClickListener(this);
        mL7.setOnClickListener(this);
        mL8.setOnClickListener(this);
        mL9.setOnClickListener(this);
        mL10.setOnClickListener(this);
        btn_bc.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sFinish:
                finish();
                break;
            case R.id.btn_bcx:
                String mName = tv_name.getText().toString();
                String mSex = tv_sex.getText().toString();
                String mId = tv_id.getText().toString();
                String mAddress = tv_address.getText().toString();
                String mContact = tv_contact.getText().toString();
                String mRelative = tv_relative.getText().toString();
                String mRelation = tv_relation.getText().toString();
                String mRelative_contact = tv_relative_contact.getText().toString();
                String mNotes = tv_notes.getText().toString();
                String areaId=String.valueOf(area_id);
                Log.e("mId----------",mId);
                map.put("token", HttpMode.getTOKEN(this));
                map.put("_method","PUT");
                map.put("indent_id",c.getIdd());
                map.put("id",mId);
                map.put("name",mName);

                if (!mSex.equals("")){
                    map.put("sex",mSex);
                }
                if (!areaId.equals("0")){
                map.put("zone_id",areaId);
            }

                if (!"".equals(mAddress)){
                    map.put("address",mAddress);
                }
                if (!"".equals(mContact)){
                    map.put("contact",mContact);
                }
                if (!"".equals(mRelative)){
                    map.put("relative",mRelative);
                }
                if (!"".equals(mRelation)){
                    map.put("relation",mRelation);
                }
                if (!"".equals(mRelative_contact)){
                    map.put("relative_contact",mRelative_contact);
                }
                if (!"".equals(mNotes)){
                    map.put("notes",mNotes);
                }
                Log.e("indent_id-------",c.getIdd());
//                map.put("status_id",shareUtils.getShared("which",SStudentEditActivity.this));
                OkUtils.UploadSJ(HttpMode.HTTPURL+HttpMode.CUSTOMER+c.getIdd(),map,this);
                CollectorActivity.finishActivity();
                break;

            case R.id.mL1:
                CustomDialog dialog = new CustomDialog(this,"姓名",c.getName(),false);
                dialog.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {
                        tv_name.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SStudentEditActivity.this);
                        alertDialog.setTitle("提示:");
                        alertDialog.setMessage(" 姓名不能为空");
                        alertDialog.setPositiveButton("ok", null);
                        alertDialog.show();
                    }
                });
                dialog.show();

                break;
            case R.id.mL2:

                CustomDog dialogs = new CustomDog(this);
                dialogs.setOnDialogClickListeners(new CustomDog.OnDialogClickListeners() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {
                        Toast.makeText(SStudentEditActivity.this,
                                inputString, Toast.LENGTH_LONG).show();
                        tv_sex.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {

                    }
                });
                dialogs.show();
                break;
            case R.id.mL3:
                CustomDialog dialog2 = new CustomDialog(this,"身份证号",c.getIdd(),false);
                dialog2.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_id.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SStudentEditActivity.this);
                        alertDialog.setTitle("提示:");
                        alertDialog.setMessage(" 身份证号不能为空");
                        alertDialog.setPositiveButton("ok", null);
                        alertDialog.show();
                    }
                });
                dialog2.show();
                break;

            case R.id.mL4:
                CustomDialog dialog3 = new CustomDialog(this,"联系方式",c.getPhone(),true);
                dialog3.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_contact.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_contact.setText("");
                    }
                });
                dialog3.show();
                break;
            case R.id.mL5:
                Intent intent=new Intent(this, RegionActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.mL6:
                CustomDialog dialog5 = new CustomDialog(this,"家庭住址",c.getAddress(),false);
                dialog5.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_address.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_address.setText("");
                    }
                });
                dialog5.show();
                break;
            case R.id.mL7:
                CustomDialog dialog7 = new CustomDialog(this,"紧急联系人",c.getParents(),false);
                dialog7.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_relative.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_relative.setText("");
                    }
                });
                dialog7.show();
                break;
            case R.id.mL8:
                CustomDialog dialog8 = new CustomDialog(this,"与学生关系",c.getGuanxi(),false);
                dialog8.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_relation.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_relation.setText("");
                    }
                });
                dialog8.show();
                break;
            case R.id.mL9:
                CustomDialog dialog9 = new CustomDialog(this,"联系人电话",c.getParents_phone(),true);
                dialog9.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_relative_contact.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_relative_contact.setText("");
                    }
                });
                dialog9.show();
                break;
            case R.id.mL10:
                CustomDialog dialog10 = new CustomDialog(this,"客户背景",c.getNotes(),false);
                dialog10.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onInputLegitimacy(final String inputString) {

                        tv_notes.setText(inputString);
                    }

                    @Override
                    public void onInputIllegal() {
                        tv_notes.setText("");
                    }
                });
                dialog10.show();
                break;

        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("上传失败---------",e+"");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        String s = response.body().string();
        Log.e("上传成功-------------", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==0&&resultCode==99){
            String s=data.getStringExtra("area");
            area_id=data.getIntExtra("area_id",1);
            tv_zone_id.setText(s);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
