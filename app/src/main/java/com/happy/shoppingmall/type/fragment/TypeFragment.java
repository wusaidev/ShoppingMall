package com.happy.shoppingmall.type.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.utils.LogUtil;


/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：分类页面Fragment
 * Created by happy on 2017/6/10.
 */

public class TypeFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("我是分类页面");
        LogUtil.e("分类页面被创建了");
    }
}
