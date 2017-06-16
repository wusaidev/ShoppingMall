package com.happy.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.happy.shoppingmall.home.bean.GoodsDetailBean;
import com.happy.shoppingmall.utils.CacheUtil;
import com.happy.shoppingmall.utils.ShoppingMallApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/15 14:28
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private  static  CartStorage instance;
    private final Context mContext;
    //SparseArray的性能优于HashMap
    private SparseArray<GoodsDetailBean> sparseArray;

    private CartStorage(Context context){
        this.mContext = context;
        //把之前存储的数据读取
        sparseArray = new SparseArray<>(100);
        //若在此处不获取  以后增删改查原来数据会被清除  替换
        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsDetailBean> GoodsDetailBeanList = getAllData();
        //把List数据转换成SparseArray
        for (int i= 0; i<GoodsDetailBeanList.size();i++){
            GoodsDetailBean goodsDetailBean = GoodsDetailBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsDetailBean.getProductId()),goodsDetailBean);
        }

    }

    /**
     * 获取本地所有的数据
     * @return
     */
    public List<GoodsDetailBean> getAllData() {
        List<GoodsDetailBean> GoodsDetailBeanList = new ArrayList<>();
        //1.从本地获取
        String json = CacheUtil.getString(mContext, JSON_CART,"");
        //2.使用Gson转换成列表
        //判断不为空的时候执行
        if(!TextUtils.isEmpty(json)){
            //把String转换成List
            GoodsDetailBeanList = new Gson().fromJson(json,new TypeToken<List<GoodsDetailBean>>(){}.getType());
        }
        return GoodsDetailBeanList;
    }


    /**
     * 得到购车实例
     * @return
     */
    public static CartStorage getInstance(){
        if(instance == null){
            instance = new CartStorage(ShoppingMallApplication.getContext());
        }
        return instance;
    }

    /**
     * 添加数据
     * @param GoodsDetailBean
     */
    public void addData(GoodsDetailBean GoodsDetailBean){

        //1.添加到内存中SparseArray
        //如果当前数据已经存在，就修改成number递增
        GoodsDetailBean tempData = sparseArray.get(Integer.parseInt(GoodsDetailBean.getProductId()));
        if(tempData != null){
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber()+1);
        }else{
            tempData = GoodsDetailBean;
            tempData.setNumber(1);
        }

        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProductId()),tempData);


        //2.同步到本地
        saveLocal();

    }

    /**
     * 删除数据
     * @param GoodsDetailBean
     */
    public void deleteData(GoodsDetailBean GoodsDetailBean){
        //1.内存中删除
        sparseArray.delete(Integer.parseInt(GoodsDetailBean.getProductId()));

        //2.把内存的保持到本地
        saveLocal();
    }


    /**
     * 更新数据
     * @param GoodsDetailBean
     */
    public void updateData(GoodsDetailBean GoodsDetailBean){
        //1.内存中更新
        sparseArray.put(Integer.parseInt(GoodsDetailBean.getProductId()),GoodsDetailBean);

        //2.同步到本地
        saveLocal();
    }

    /**
     * 保持数据到本地
     */
    private void saveLocal() {
        //1.SparseArray转换成List
       List<GoodsDetailBean>  GoodsDetailBeanList = sparseToList();
        //2.使用Gson把列表转换成String类型
        String json = new Gson().toJson(GoodsDetailBeanList);
        //3.把String数据保存
        CacheUtil.putString(mContext,JSON_CART,json);

    }

    private List<GoodsDetailBean> sparseToList() {
        List<GoodsDetailBean> GoodsDetailBeanList = new ArrayList<>();
        for (int i=0;i<sparseArray.size();i++){
            GoodsDetailBean GoodsDetailBean = sparseArray.valueAt(i);
            GoodsDetailBeanList.add(GoodsDetailBean);
        }
        return GoodsDetailBeanList;
    }


}
