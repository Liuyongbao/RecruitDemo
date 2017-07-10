package com.example.recruitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.recruitdemo.Adapter.RegionAdapter;
import com.example.recruitdemo.UserBean.Area;
import com.example.recruitdemo.UserBean.City;
import com.example.recruitdemo.UserBean.Province;
import com.example.recruitdemo.Utils.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 省市区  三级联动  实现主要功能：获得解析到的数据，在三个ListView中显示，将拿到的地区名和地区id返回给上一级
 */
public class RegionActivity extends AppCompatActivity {
    private TextView tv_province,tv_city,tv_county;
    private ListView lv_province,lv_city,lv_county;
    private RegionAdapter<Province> provinceAdapter;
    private RegionAdapter<City> cityAdapter;
    private RegionAdapter<Area> areaAdapter;
    private String pro,citys,areas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        initView();
    }
    private void initView(){
        tv_province= (TextView) findViewById(R.id.tv_province);
        tv_city= (TextView) findViewById(R.id.tv_city);
        tv_county= (TextView) findViewById(R.id.tv_county);

        lv_province= (ListView) findViewById(R.id.lv_province);
        lv_city= (ListView) findViewById(R.id.lv_city);
        lv_county= (ListView) findViewById(R.id.lv_county);

        onclick();

    }
    private String getData(){
        Log.e("provice","aaaaa");
        try {
            InputStream in=getResources().getAssets().open("zone.json");
            Log.e("provice","sb");
            InputStreamReader isr=new InputStreamReader(in);
            BufferedReader br=new BufferedReader(isr);

            String line;
            StringBuffer sb=new StringBuffer();
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            Log.e("provice",sb.toString());
            br.close();

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private void onclick(){
        String s=getData();
        List<Province> list= JsonUtils.JsonRegion(s);
        provinceAdapter=new RegionAdapter<>(this,list);
        lv_province.setAdapter(provinceAdapter);
        lv_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Province p= (Province) provinceAdapter.getItem(position);
                pro=p.getpName();
                tv_province.setText(pro);
                tv_city.setText("市");
                tv_county.setText("区");

                List<City> cityList=p.getCityList();
                if(cityAdapter==null){
                    cityAdapter=new RegionAdapter<City>(RegionActivity.this,cityList);
                    lv_city.setAdapter(cityAdapter);
                }else{
                    cityAdapter.refresh(cityList);
                }
                if(areaAdapter!=null){
                    areaAdapter.refresh(null);
                }
            }
        });
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city= (City) cityAdapter.getItem(position);
                citys=city.getcName();
                tv_city.setText(citys);
                List<Area> areaList=city.getAreaList();
                if(areaAdapter==null){
                    areaAdapter=new RegionAdapter<Area>(RegionActivity.this,areaList);
                    lv_county.setAdapter(areaAdapter);
                }else {
                    areaAdapter.refresh(areaList);
                }
            }
        });
        lv_county.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Area area= (Area) areaAdapter.getItem(position);
                areas= area.getaName();
                int area_id=area.getId();

                tv_county.setText(areas);

                Intent in=new Intent();

                String result=pro+citys+areas;
                in.putExtra("area",result);
                in.putExtra("area_id",area_id);
                setResult(99,in);
                finish();
            }
        });
    }
}
