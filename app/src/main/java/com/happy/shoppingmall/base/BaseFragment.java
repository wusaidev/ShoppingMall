package com.happy.shoppingmall.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：首页  发现  用户中心  购物车 分类 等等  都要继承改类
 * Created by happy on 2017/6/10.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    /**
     * 当该类被创建的时候调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    /**
     * 当视图被创建的时候调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView();
    }

    /**
     * activity创建的时候  调用方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }


    /**
     * 抽象类由孩子实现
     * @return
     */
    public abstract View initView();

    /**
     * 当子类活动被创建的时候  孩子要添加数据调用此方法
     */
    public abstract void initData();
}
