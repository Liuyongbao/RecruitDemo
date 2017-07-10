package com.example.recruitdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recruitdemo.R;

/**
 * Created by 张金瑞 on 2017/3/20. 统计页面  暂未开发
 */
public class StatisticsFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.statistical,container,false);
        return view;
    }
}
