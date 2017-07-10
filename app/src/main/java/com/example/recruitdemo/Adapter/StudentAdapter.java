package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Customer;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/21.    审批→我发起的审批 假数据 ListView适配器
 */
public class StudentAdapter extends BaseAdapter{
    private Context context;
    private List<Customer> list;

    public StudentAdapter(Context context,List<Customer> list){
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
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.student_item,null);
            holder.avatar=(TextView)convertView.findViewById(R.id.avatar);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.id=(TextView)convertView.findViewById(R.id.Id);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Customer c=list.get(position);
        holder.avatar.setText(c.getAvatar());
        holder.name.setText(c.getName());
        holder.id.setText(c.getId());


        return convertView;
    }
    class  ViewHolder{
        private TextView avatar,name;
        public TextView id;
    }
}
