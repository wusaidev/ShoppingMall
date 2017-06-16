package com.happy.shoppingmall.shoppingcart.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.happy.shoppingmall.R;
import com.happy.shoppingmall.app.MainActivity;
import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.home.bean.GoodsDetailBean;
import com.happy.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.happy.shoppingmall.shoppingcart.utils.CartStorage;
import com.happy.shoppingmall.utils.LogUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：购物车页面Fragment
 * Created by happy on 2017/6/10.
 */

public class ShoppingCartFragment extends BaseFragment {


    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    Unbinder unbinder;
    //编辑状态
    private static final int ACTION_EDIT=1;
    //完整状态
    private static final int ACTION_COMPLETE=2;
    //当前状态
    private static int ACTION_CURRENT=2;
    private ShoppingCartAdapter adapter;
    private List<GoodsDetailBean> goodsDetailList;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, String> result = (Map<String, String>) msg.obj;
            Toast.makeText(mContext, result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void initData() {
        LogUtil.e("购物车页面被创建了");

    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
        //校验是否显示列表为空时的状态
        emptyShoppingCart();
    }

    /**
     * 展示数据方法
     */
    private void showData() {
        goodsDetailList = CartStorage.getInstance().getAllData();
        for (int i = 0; i < goodsDetailList.size(); i++) {
            GoodsDetailBean goodsDetail = goodsDetailList.get(i);
            LogUtil.e("购物车缓存数据" + goodsDetail.toString());
        }

        if(goodsDetailList !=null&& goodsDetailList.size()>0){
            //有数据
            //把当没有数据时的布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            recyclerview.setLayoutManager(manager);
            adapter = new ShoppingCartAdapter(mContext,
                    goodsDetailList, checkboxAll, tvShopcartTotal,cbAll);
            recyclerview.setAdapter(adapter);
        }
       /* else {
            //显示数据为空的布局
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }*/
    }



    @OnClick({R.id.tv_shopcart_edit, R.id.recyclerview, R.id.checkbox_all, R.id.tv_shopcart_total, R.id.btn_check_out, R.id.ll_check_all, R.id.cb_all, R.id.btn_delete, R.id.btn_collection, R.id.ll_delete, R.id.iv_empty, R.id.tv_empty_cart_tobuy, R.id.ll_empty_shopcart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                if(goodsDetailList!=null){
                    if (ACTION_CURRENT==ACTION_EDIT){
                        //切换为完成状态
                        showComplete();
                    }else{
                        //切换为编辑状态
                        showEdit();
                    }
                }

                break;
            case R.id.recyclerview:
                break;
            case R.id.checkbox_all:
                break;
            case R.id.tv_shopcart_total:
                break;
            case R.id.btn_check_out:
                //结算
                checkOut();
                break;
            case R.id.ll_check_all:
                break;
            case R.id.cb_all:
                break;
            case R.id.btn_delete:
                if(adapter!=null){
                    adapter.delete();
                    adapter.isAllChecked();
                    goodsDetailList=CartStorage.getInstance().getAllData();
                    emptyShoppingCart();
                }
                break;
            case R.id.btn_collection:
                break;
            case R.id.ll_delete:
                break;
            case R.id.iv_empty:
                break;
            case R.id.tv_empty_cart_tobuy:
                break;
            case R.id.ll_empty_shopcart:
                break;
        }
    }

    private void showComplete() {
        //1 设置状态和文本
        ACTION_CURRENT=ACTION_COMPLETE;
        tvShopcartEdit.setText("编辑");
        //2 完成非勾选
        if(adapter!=null){
            adapter.check_none(true);
            adapter.showTotalPrice();
        }
        //3 删除视图显示
        llDelete.setVisibility(View.GONE);
        //4 结算视图  隐藏
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showEdit() {
        //1 设置状态和文本
        ACTION_CURRENT=ACTION_EDIT;
        tvShopcartEdit.setText("完成");
        //2 完成非勾选
        if(adapter!=null){
            adapter.check_none(false);
        }
        //3 删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        //4 结算视图  隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    private void emptyShoppingCart(){
        if (goodsDetailList.size()==0){
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
            recyclerview.setVisibility(View.GONE);
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }else {
            llEmptyShopcart.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
        }
    }

    private void checkOut(){
        final String orderInfo = goodsDetailList.toString();   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /*public void jumpToShoppingCart(){
        MainActivity mainActivity= (MainActivity) mContext;
        mainActivity.showShoppingCart(data.getBooleanExtra("show_cart", false));
    }*/

}
