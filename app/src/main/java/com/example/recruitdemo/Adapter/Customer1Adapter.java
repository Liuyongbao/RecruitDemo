package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.Customer1;

import java.util.List;


/**
 * Created by Administrator on 2017/3/27.  学生信息展示适配器
 */
public class Customer1Adapter extends BaseAdapter{
    private Context context;
    private List<Customer1>list;
    public Customer1Adapter(Context context,List<Customer1> list){
        this.context=context;
        this.list=list;
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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_studentbjxq,null);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_sex=(TextView)convertView.findViewById(R.id.tv_sex);
            holder.tv_id=(TextView)convertView.findViewById(R.id.tv_id);

            holder.tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_status=(TextView)convertView.findViewById(R.id.tv_status);

            holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
            holder.tv_parents=(TextView)convertView.findViewById(R.id.tv_parents);
            holder.tv_parentsPhone=(TextView)convertView.findViewById(R.id.tv_parentsPhone);

            holder.tv_guanxi=(TextView)convertView.findViewById(R.id.tv_guanxi);
            holder.tv_bumen=(TextView)convertView.findViewById(R.id.tv_bumen);
            holder.tv_fuzeren=(TextView)convertView.findViewById(R.id.tv_fuzeren);

            holder.tv_zone=(TextView)convertView.findViewById(R.id.tv_zone);
            holder.tv_f_phone=(TextView)convertView.findViewById(R.id.tv_f_phone);
            holder.tv_notes=(TextView)convertView.findViewById(R.id.tv_notes);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        Customer1 c=list.get(position);
        holder.tv_name.setText(c.getName());
        holder.tv_sex.setText(c.getSex());
        holder.tv_id.setText(c.getId());

        holder.tv_phone.setText(c.getPhone());
        holder.tv_time.setText(c.getTime());
        holder.tv_status.setText(c.getStatus());

        holder.tv_address.setText(c.getAddress());
        holder.tv_parents.setText(c.getParents());
        holder.tv_parentsPhone.setText(c.getParents_phone());

        holder.tv_guanxi.setText(c.getGuanxi());
        holder.tv_bumen.setText(c.getBumen());
        holder.tv_fuzeren.setText(c.getFuzeren());

        holder.tv_zone.setText(c.getZone());
        holder.tv_f_phone.setText(c.getF_phone());
        holder.tv_notes.setText(c.getNotes());


        return convertView;
    }


    class ViewHolder{
        private TextView tv_name,tv_sex,tv_id,tv_phone,tv_time,tv_status,tv_address,tv_parents,
                tv_parentsPhone,tv_guanxi,tv_bumen,tv_fuzeren,tv_notes,tv_zone,tv_f_phone;
    }
}
