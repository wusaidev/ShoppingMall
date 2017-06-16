package com.happy.addsubview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/13.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {


    private final ImageView iv_sub;
    private final ImageView iv_add;
    private final TextView tv_value;
    private int value=1;
    private int maxValue=10;
    private int minValue=1;
    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //把布局文件实例化，并且加载到AddSubView类中        第三个参数this就是这个作用，将布局放在这个勒种
        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);

        int value=getValue();
        tv_value.setText(value+"");
        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub://减
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;

        }
    }

    /**
     * 加
     */
    private void addNumber() {
        if(value<maxValue){
            value++;
        }
        //Toast.makeText(getContext(),"当前商品数"+value,Toast.LENGTH_SHORT).show();
        setValue(value);
    }

    /**
     * 减
     */
    private void subNumber() {
        if(value!=1){
            value--;
        }
       // Toast.makeText(getContext(),"当前商品数"+value,Toast.LENGTH_SHORT).show();
        setValue(value);
    }


    public int getValue() {
        String valueStr=tv_value.getText().toString().trim();
        if(TextUtils.isEmpty(valueStr)){
            value=Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
        if (onNumberChanagerListener!=null){
            onNumberChanagerListener.onNumberChange(value);
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * 当数量发生变化的时候回调
     */
    public interface OnNumberChanagerListener{
        //当数据发生改变时是回来、掉
        public void onNumberChange(int value);
    }

    private OnNumberChanagerListener onNumberChanagerListener;

    public void setOnNumberChanagerListener(OnNumberChanagerListener onNumberChanagerListener){
        this.onNumberChanagerListener=onNumberChanagerListener;
    }
}
