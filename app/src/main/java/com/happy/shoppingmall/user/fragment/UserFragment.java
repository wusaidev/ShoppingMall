package com.happy.shoppingmall.user.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.utils.LogUtil;


/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：用户中心页面Fragment
 * Created by happy on 2017/6/10.
 */

public class UserFragment extends BaseFragment {

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
        textView.setText("我是用户中心页面");
        LogUtil.e("用户中心页面被创建了");
    }
}
