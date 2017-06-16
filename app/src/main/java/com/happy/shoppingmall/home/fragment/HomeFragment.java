package com.happy.shoppingmall.home.fragment;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.happy.shoppingmall.R;
import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.happy.shoppingmall.home.bean.ResultBeanData;
import com.happy.shoppingmall.utils.CacheUtil;
import com.happy.shoppingmall.utils.ConstantsValue;
import com.happy.shoppingmall.utils.LogUtil;
import com.happy.shoppingmall.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：主页面Fragment
 * Created by happy on 2017/6/10.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG =
            HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private EditText et_search_home;
    private ResultBeanData.ResultBean result;
    private HomeFragmentAdapter homeFragmentAdapter;

    @Override
    public View initView() {
        View view=View.inflate(mContext,R.layout.fragment_home,null);
        LogUtil.e(" 主页视图被初始化了");
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView)view.findViewById(R.id.tv_search_home);
        et_search_home = (EditText) view.findViewById(R.id.et_search_home);

        tv_message_home = (TextView)
                view.findViewById(R.id.tv_message_home);
        initListener();
        return view;
    }

    private void initListener() {
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_search_home.setVisibility(View.GONE);
                et_search_home.setVisibility(View.VISIBLE);
                ToastUtil.show(mContext,"搜索");
            }
        });
        // 消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(mContext,"进入消息中心");
            }
        });
    }

    @Override
    public void initData() {
        LogUtil.e("主页面被创建了");
        String result=CacheUtil.getString(mContext,ConstantsValue.RESULT,"");
        if (!TextUtils.isEmpty(result)){
            processData(result);
        }
        getDataFromNet();
    }

    /**
     * 从网络请求数据方法
     */
    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(ConstantsValue.HOME_URL)
                //.addParams("username", "hyman")
                //.addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("首页请求失败："+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.e("首页请求成功："+response);
                        CacheUtil.putString(mContext,ConstantsValue.RESULT,response);
                        processData(response);
                    }
                });
    }

    private void processData(String response) {
        ResultBeanData resultBean= JSON.parseObject(response,ResultBeanData.class);
        result = resultBean.getResult();
        if(response!=null){
            homeFragmentAdapter = new HomeFragmentAdapter(mContext, result);
            rvHome.setAdapter(homeFragmentAdapter);

            //设置滚动监听
            GridLayoutManager manager=new GridLayoutManager(mContext,1);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position) {
                    if (position>=5){
                        ib_top.setVisibility(View.VISIBLE);
                    }else {
                        ib_top.setVisibility(View.GONE);
                    }
                    return 1;
                }
            });
            //设置布局管理者  指定recyclerview格式
            rvHome.setLayoutManager(manager);
        }
        LogUtil.e("数据解析成功："+result.getHot_info().get(0).getName());
    }
}
