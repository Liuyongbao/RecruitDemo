package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Area;
import com.example.recruitdemo.UserBean.City;
import com.example.recruitdemo.UserBean.Province;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/24.   录入信息→行政区划 省市区三级联动
 */
public class RegionAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;

    public RegionAdapter(Context context,List<T> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }
    public void refresh(List<T> list){
        this.list=list;
        notifyDataSetChanged();
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
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_department,null);
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        T o=list.get(position);
        if(o instanceof Province){
            Province p= (Province) o;
            holder.tv.setText(p.getpName());
        }
        if(o instanceof City){
            City city= (City) o;
            holder.tv.setText(city.getcName());
        }
        if(o instanceof Area){
            Area area= (Area) o;
            holder.tv.setText(area.getaName());
        }


        return convertView;
    }
    static class ViewHolder{
        TextView tv;
    }
}
