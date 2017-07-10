package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.recruitdemo.R;
import com.example.recruitdemo.UserBean.College;
import com.example.recruitdemo.UserBean.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.    市场部ExpandableListView 适配器
 */
public class MyAdapter extends BaseExpandableListAdapter {

    private List<Group> grlist;
    private Context context;

    public MyAdapter(Context context,List<Group>grlist){
        this.grlist=grlist;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return grlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return grlist.get(groupPosition).getFlist().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return grlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return grlist.get(childPosition).getFlist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        grViewHolder grholder;
        if(convertView==null){
            grholder=new grViewHolder();
            convertView=View.inflate(context, R.layout.group, null);
            grholder.giv_name=(TextView)convertView.findViewById(R.id.gtv_name);
            grholder.giv_avatar=(ImageView)convertView.findViewById(R.id.giv_avatar);
            convertView.setTag(grholder);
        }else{
            grholder=(grViewHolder) convertView.getTag();
        }
        grholder.giv_name.setText(grlist.get(groupPosition).getGrname());

        if(isExpanded){
            grholder.giv_avatar.setImageResource(R.mipmap.sj);
        }else{
            grholder.giv_avatar.setImageResource(R.mipmap.sj1);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        fViewHolder fholder;
        if(convertView==null){
            fholder=new fViewHolder();
            convertView=View.inflate(context, R.layout.group1, null);
            fholder.f_name=(TextView)convertView.findViewById(R.id.f_name);
            //fholder.f_id=(TextView)convertView.findViewById(R.id.f_id);
            convertView.setTag(fholder);
        }else{
            fholder=(fViewHolder) convertView.getTag();
        }

        College f=(College) grlist.get(groupPosition).getFlist().get(childPosition);
        fholder.f_name.setText(f.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    static class grViewHolder{

        public ImageView giv_avatar;
        public TextView giv_name;

    }
    static class fViewHolder{

        public TextView f_name,f_id;

    }

}

