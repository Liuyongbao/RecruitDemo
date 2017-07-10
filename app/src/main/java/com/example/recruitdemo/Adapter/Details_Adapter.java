package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.ApproveBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.  审批→我发起的→请假    适配器
 */
public class Details_Adapter extends BaseAdapter{

    private List<ApproveBean> list;
    private Context context;

    public Details_Adapter(Context context, List<ApproveBean> list){
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



        try {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView=View.inflate(context, R.layout.details_item,null);
                viewHolder=new ViewHolder();
                viewHolder.avatar=(TextView)convertView.findViewById(R.id.tx);
                viewHolder.name=(TextView)convertView.findViewById(R.id.name);
                viewHolder.time1=(TextView)convertView.findViewById(R.id.time);
                viewHolder.state=(TextView)convertView.findViewById(R.id.state);
                viewHolder.img=(ImageView)convertView.findViewById(R.id.img);
                viewHolder.tm=(TextView)convertView.findViewById(R.id.tm);
                viewHolder.tv_pinglun= (TextView) convertView.findViewById(R.id.tv_pinglun);
                viewHolder.tv_xian= (TextView) convertView.findViewById(R.id.tv_xian);
                convertView.setTag(viewHolder);

            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.img.setImageResource(list.get(position).getImg());
            viewHolder.avatar.setText(list.get(position).getAvatar());
            viewHolder.state.setText(list.get(position).getState());
            viewHolder.time1.setText(list.get(position).getTime1());
            viewHolder.name.setText(list.get(position).getName());
            if (position!=0){
                if (list.get(position).getNotes()!=null){
                    viewHolder.tv_pinglun.setText("“"+list.get(position).getNotes()+"”");
                    viewHolder.tv_pinglun.setVisibility(View.VISIBLE);
                    viewHolder.tv_xian.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.tv_xian.setVisibility(View.GONE);
                }
            }else {
                viewHolder.tv_xian.setVisibility(View.GONE);
                viewHolder.tv_pinglun.setVisibility(View.GONE);
            }


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if(list.get(position).getState().equals("发起申请")){
                viewHolder.state.setTextColor(Color.GREEN);
                viewHolder.tm.setVisibility(View.GONE);
            }else if(list.get(position).getState().equals("已同意")){
                viewHolder.state.setTextColor(Color.GREEN);

                Date d1 = df.parse(list.get(position).getTm());
                Date d2 = df.parse(list.get(position).getTime1());
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

                if((int)days==0){
                    if((int)hours==0){
                        if((int)minutes==0){
                            viewHolder.tm.setText("1分钟");
                        }else{
                            viewHolder.tm.setText(minutes+"分钟");
                        }

                    }else{
                        viewHolder.tm.setText(hours+"小时"+minutes+"分钟");
                    }
                }else{
                    viewHolder.tm.setText(days+"天"+hours+"小时"+minutes+"分钟");
                }

            }else if(list.get(position).getState().equals("已拒绝")){
                viewHolder.state.setTextColor(Color.RED);

                Date d1 = df.parse(list.get(position).getTm());
                Date d2 = df.parse(list.get(position).getTime1());
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

                if((int)days==0){
                    if((int)hours==0){
                        if((int)minutes==0){
                            viewHolder.tm.setText("1分钟");
                        }else{
                            viewHolder.tm.setText(minutes+"分钟");
                        }
                    }else{
                        viewHolder.tm.setText(hours+"小时"+minutes+"分钟");
                    }
                }else{
                    viewHolder.tm.setText(days+"天"+hours+"小时"+minutes+"分钟");
                }
            }
            return convertView;
        } catch (ParseException e) {
            e.printStackTrace();
        }

       return  null;
    }


    static class ViewHolder{
        private TextView avatar,name,time1,state,tm,tv_pinglun,tv_xian;
        private ImageView img;
    }

}
