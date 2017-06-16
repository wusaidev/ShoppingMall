package com.happy.shoppingmall.community.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.utils.LogUtil;


/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：发现页面Fragment
 * Created by happy on 2017/6/10.
 */

public class CommunityFragment extends BaseFragment {

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
        textView.setText("我是发现页面");
        LogUtil.e("发现页面页面被创建了");
    }
}
