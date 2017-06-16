package com.happy.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：序列化详情页面数据Bean类
 * Created by happy on 2017/6/12.
 */

public class GoodsDetailBean implements Serializable {
    private String imgUrl;
    private String name="";
    private String convertPrice="";
    private String productId="";
    private String htmlUrl="";
    private int number=1;

    /**
     * 是否被选中
     */
    private boolean isSelected=true;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConvertPrice() {
        return convertPrice;
    }

    public void setConvertPrice(String convertPrice) {
        this.convertPrice = convertPrice;
    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", convertPrice='" + convertPrice + '\'' +
                ", productId='" + productId + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", number=" + number +
                ", isSelected=" + isSelected +
                '}';
    }
}
