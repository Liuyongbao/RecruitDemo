package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Employee;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/4/3.
 */
public class ListAdapter extends BaseAdapter {
    private List<Employee> list;
    private Context context;
    public ListAdapter( List<Employee> list,Context context){
        this.list=list;
        this.context=context;
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
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=View.inflate(context, R.layout.item, null);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_number=(TextView)convertView.findViewById(R.id.tv_number);
            //fholder.f_id=(TextView)convertView.findViewById(R.id.f_id);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }
        holder.tv_number.setText(list.get(position).getId());
        holder.tv_name.setText(list.get(position).getName());
        return convertView;
    }

    static class Holder{
        public TextView tv_name;
        public TextView tv_number;
    }
}
