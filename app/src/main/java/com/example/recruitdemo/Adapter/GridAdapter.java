package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recruitdemo.R;

import java.util.List;

/**
 * Created by 花卷 on 2017/3/21.
 */

public class GridAdapter extends BaseAdapter{
    private List<String> list;
    private Context context;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }
    public void Refresh(List<String> list){
        this.list = list;
        notifyDataSetChanged();
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
        convertView = layoutInflater.inflate(R.layout.griditem, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        if (position == list.size()-2) {
            tv.setBackgroundResource(R.mipmap.jiahao1);
        }else if(position==list.size()-1){
            tv.setText(list.get(position));
            tv.setBackgroundResource(R.drawable.tv50_red);
        }else{
            tv.setText(list.get(position));
        }
        return convertView;
    }

}
