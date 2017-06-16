package com.happy.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.happy.shoppingmall.R;
import com.happy.shoppingmall.home.bean.GoodsDetailBean;
import com.happy.shoppingmall.shoppingcart.utils.CartStorage;
import com.happy.shoppingmall.shoppingcart.view.AddSubView;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：购物车RecycleView  的Adapter类
 * Created by happy on 2017/6/13.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsDetailBean> goodsDetailList;
    private CheckBox checkboxAll;
    private CheckBox cbAll;
    private TextView tvShopcartTotal;

    /**
     * @param context
     * @param goodsDetailList
     * @param checkboxAll
     * @param tvShopcartTotal
     * @param cbAll
     */
    public ShoppingCartAdapter(Context context, final List<GoodsDetailBean> goodsDetailList,
                               CheckBox checkboxAll, TextView tvShopcartTotal, CheckBox cbAll) {
        this.context = context;
        this.goodsDetailList=goodsDetailList;
        this.tvShopcartTotal=tvShopcartTotal;
        this.checkboxAll=checkboxAll;
        //编辑状态下的 全选非全选
        this.cbAll=cbAll;
        setListener();
        isAllChecked();

    }

    private void setListener() {

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=checkboxAll.isChecked();
                check_none(isChecked);
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=cbAll.isChecked();
                check_none(isChecked);
            }
        });
    }

    public void check_none(boolean isChecked) {
        if(goodsDetailList != null && goodsDetailList.size() >0){
            for (int i=0;i<goodsDetailList.size();i++){
                GoodsDetailBean goodsBean = goodsDetailList.get(i);
                goodsBean.setSelected(isChecked);
                CartStorage.getInstance().updateData(goodsBean);
                notifyItemChanged(i);
            }
        }
    }

    public void delete() {
        for(int i=0;i<goodsDetailList.size();i++){
            //删除选中的
            GoodsDetailBean goodsDetailBean=goodsDetailList.get(i);
            if (goodsDetailBean.isSelected()){
                //移除
                //内存中移除
                goodsDetailList.remove(i);
                //保存到本地
                CartStorage.getInstance().deleteData(goodsDetailBean);
                //刷新
                notifyItemRemoved(i);
                //全选删不完  原因
                i--;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cbGov;
        private final ImageView ivGov;
        private final TextView tvDescGov;
        private final TextView tvPriceGov;
        private final AddSubView ddSubView;
        private View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            ddSubView = (AddSubView) itemView.findViewById(R.id.ddSubView);
            this.itemView=itemView;
        }

        public void setData(final int position) {
            //在传进来前已经判断是否为空  这里直接使用
            final GoodsDetailBean goodsDetail=goodsDetailList.get(position);
            cbGov.setChecked(goodsDetail.isSelected());
            Glide.with(context)
                    .load(goodsDetail.getImgUrl())
                    .into(ivGov);
            tvDescGov.setText(goodsDetail.getName());
            tvPriceGov.setText(" ￥"+goodsDetail.getConvertPrice());
            ddSubView.setValue(goodsDetail.getNumber());
            //可以根据json数据设置最大值  最小值
            ddSubView.setOnNumberChanagerListener(new AddSubView.OnNumberChanagerListener() {
                @Override
                public void onNumberChange(int value) {
                    goodsDetail.setNumber(value);
                    CartStorage.getInstance().updateData(goodsDetail);
                    showTotalPrice();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsDetail.setSelected(!(goodsDetail.isSelected()));
                    cbGov.setChecked(goodsDetail.isSelected());
                    CartStorage.getInstance().updateData(goodsDetail);
                    showTotalPrice();
                    isAllChecked();
                }
            });
            isAllChecked();
            showTotalPrice();
        }




    }
    /**
     * 判断全选CheckBox  状态方法
     */
    public void isAllChecked() {
        checkboxAll.setChecked(true);
        cbAll.setChecked(true);
        for (int i=0;i<goodsDetailList.size();i++){
            GoodsDetailBean goodsDetailBean= goodsDetailList.get(i);
            if(!goodsDetailBean.isSelected()){
                checkboxAll.setChecked(false);
                cbAll.setChecked(false);
                return;
            }
            //或者写个number  else number++
            //循环结束后  如果number==size（） 那就是全选
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shop_cart, null);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return goodsDetailList.size();
    }

    //点击事件回调借口
    public interface OnItemClickListener {
        public void OnItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener
            (OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }



    /**
     * 显示价格方法
     */
    public void showTotalPrice() {
        tvShopcartTotal.setText("合计:" + getTotalPrice());
    }

    /**
     * 获取价格方法
     * for循环 依次判断isChecked值  true  就取出number与price相乘
     * @return
     */
    public Double getTotalPrice() {
        double totalPrice=0.0;
        for (int i=0;i<goodsDetailList.size();i++){
            GoodsDetailBean goodsDetail=goodsDetailList.get(i);
            if(goodsDetail.isSelected()){
                totalPrice=totalPrice+Double.valueOf(goodsDetail.getConvertPrice())*
                        Double.valueOf(goodsDetail.getNumber());
            }
        }
        return totalPrice;
    }
}
