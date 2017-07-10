package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.ShenPi;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/21.     审批ListView 适配器
 */
public class ApproveListAdapter extends BaseAdapter {
    private Context context;
    private List<ShenPi> list;

    public ApproveListAdapter(Context context, List<ShenPi> list) {
        this.context = context;
        this.list = list;
    }

    public void Refresh(List<ShenPi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.list_item, null);
            holder.avatar = (TextView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.time1 = (TextView) convertView.findViewById(R.id.time1);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar.setText(list.get(position).getEmployee_name());
        holder.name.setText(list.get(position).getIn_customer_name() + "的审批单");
        holder.type.setText(list.get(position).getId());
        holder.time1.setText(list.get(position).getStatus_name());
        holder.state.setText(list.get(position).getOrg_name());

        return convertView;
    }

    private class ViewHolder {
        private TextView avatar, name, type, time1, state;
    }
}
