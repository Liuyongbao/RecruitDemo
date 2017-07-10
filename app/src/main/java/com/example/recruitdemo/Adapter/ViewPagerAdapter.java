package com.example.recruitdemo.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/21.    首页 自动轮播ViewPager适配器
 */
public class ViewPagerAdapter extends PagerAdapter{
    private List<ImageView> imgList;
    private Context context;
    public  ViewPagerAdapter(List<ImageView> imgList,Context context){
        this.imgList=imgList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int p = position%imgList.size();
        ImageView img = imgList.get(p);
        ViewParent vp = img.getParent();
        if(vp!=null){
            ViewGroup vg = (ViewGroup) vp;
            vg.removeView(img);
        }
        container.addView(imgList.get(p));
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int p = position%imgList.size();
        container.removeView(imgList.get(p));
    }
}
