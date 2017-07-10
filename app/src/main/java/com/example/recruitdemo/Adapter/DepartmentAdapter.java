package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Branch;
import com.example.recruitdemo.UserBean.Department;
import com.example.recruitdemo.UserBean.Division;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/23.     市场部四级联动ListView 适配器
 */
public class DepartmentAdapter<T> extends BaseAdapter {
    private List<T>list;
    private Context context;
    public  DepartmentAdapter(List<T> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        if (list==null){
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
        DeViewHolder holder;
        if (convertView==null){
            holder=new DeViewHolder();
            convertView=View.inflate(context, R.layout.item_department,null);
            holder.tv=(TextView)convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{
            holder= (DeViewHolder) convertView.getTag();
        }
        T o=list.get(position);
        if (o instanceof Department){
            Department department= (Department) o;
            holder.tv.setText(department.getName());
        }
        if (o instanceof Division){
            Division division= (Division) o;
            holder.tv.setText(division.getName());
        }
        if(o instanceof Branch){
            Branch branch= (Branch) o;
            holder.tv.setText(branch.getName());
        }


        return convertView;
    }
    static class DeViewHolder {
        private TextView tv;
    }
}
