package com.happy.shoppingmall.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.happy.shoppingmall.R;
import com.happy.shoppingmall.home.bean.GoodsDetailBean;
import com.happy.shoppingmall.shoppingcart.utils.CartStorage;
import com.happy.shoppingmall.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsInfoActivity extends AppCompatActivity {

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @BindView(R.id.tv_more_home)
    TextView tvMoreHome;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.activity_start_goods_info)
    LinearLayout activityStartGoodsInfo;
    private GoodsDetailBean goodsDetail;
    private boolean showCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_goods_info);
        //https://gd3.alicdn.com/imgextra/i2/1766746689/TB2BrLmiypnpuFjSZFIXXXh2VXa_!!1766746689.jpg
        //https://item.taobao.com/item.htm?spm=a230r.1.14.16.4gpXm5&id=541129381376&ns=1&abbucket=4#detail
        //http://192.168.1.103:8080/atguigu/img/oper/1478169868app.html
        ButterKnife.bind(this);
        getDataFromIntent();
        setData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //默认为false

    }

    /**
     * 从intent中获取数据
     */
    private void getDataFromIntent() {
        goodsDetail = (GoodsDetailBean)
                getIntent().getSerializableExtra("goods_detail");
        ToastUtil.show(this,goodsDetail.getName()
                +goodsDetail.getConvertPrice()+goodsDetail.getImgUrl()
                +goodsDetail.getHtmlUrl());
    }

    /**
     * 设置数据方法
     */
    private void setData() {
        setFromIntentData();
    }

    private void setFromIntentData() {
        Glide.with(this)
                .load(goodsDetail.getImgUrl())
                .into(ivGoodInfoImage);
        tvGoodInfoName.setText(goodsDetail.getName());
        tvGoodInfoPrice.setText(" ￥"+goodsDetail.getConvertPrice());
        wbGoodInfoMore.loadUrl(goodsDetail.getHtmlUrl());
        WebSettings settings = wbGoodInfoMore.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        wbGoodInfoMore.setWebViewClient(new WebViewClient());
        // 优先使用缓存
        wbGoodInfoMore.getSettings().setCacheMode
                (WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.iv_good_info_image, R.id.tv_good_info_name, R.id.tv_good_info_desc, R.id.tv_good_info_price, R.id.tv_good_info_store, R.id.tv_good_info_style, R.id.wb_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.ll_goods_root, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more, R.id.ll_root, R.id.activity_start_goods_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                ToastUtil.show(this,"显示更多");
                break;
            /*case R.id.iv_good_info_image:
                break;
            case R.id.tv_good_info_name:
                break;
            case R.id.tv_good_info_desc:
                break;
            case R.id.tv_good_info_price:
                break;
            case R.id.tv_good_info_store:
                break;
            case R.id.tv_good_info_style:
                break;
            case R.id.wb_good_info_more:
                break;*/
            case R.id.tv_good_info_callcenter:
                ToastUtil.show(this,"呼叫客服");
                break;
            case R.id.tv_good_info_collection:
                ToastUtil.show(this,"收藏");
                break;
            case R.id.tv_good_info_cart:
                ToastUtil.show(this,"跳转到购物车");
                Intent intent =new Intent(this,MainActivity.class);
                showCart=true;
                intent.putExtra("show_cart",showCart);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_good_info_addcart:
                CartStorage.getInstance().addData(goodsDetail);
                ToastUtil.show(this,"添加到购物车");
                break;
            /*case R.id.ll_goods_root:
                break;*/
            case R.id.tv_more_share:
                ToastUtil.show(this,"分享");
                break;
            case R.id.tv_more_search:
                ToastUtil.show(this,"搜索");
                break;
            case R.id.tv_more_home:
                ToastUtil.show(this,"主页面");
                break;
           /* case R.id.btn_more:
                break;
            case R.id.ll_root:
                break;
            case R.id.activity_start_goods_info:
                break;*/
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showCart=false;
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("show_cart",showCart);
        startActivity(intent);
        finish();
    }
}
