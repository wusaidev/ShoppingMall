package com.happy.shoppingmall.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.happy.shoppingmall.R;
import com.happy.shoppingmall.base.BaseFragment;
import com.happy.shoppingmall.community.fragment.CommunityFragment;
import com.happy.shoppingmall.home.fragment.HomeFragment;
import com.happy.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.happy.shoppingmall.type.fragment.TypeFragment;
import com.happy.shoppingmall.user.fragment.UserFragment;
import com.happy.shoppingmall.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private List<BaseFragment> fragmentList;
    BaseFragment tempFragment;
    private int position;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isFirst = true;
        initFragment();
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();

        jumpToCart();

    }

    /**
     * 初始化RadioGroup监听方法
     */
    private void initListener() {
        position = 0;
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                    default:
                        break;
                }
                if (fragmentList != null && fragmentList.size() > 0) {
                    BaseFragment baseFragment = fragmentList.get(position);
                    switchFragment(tempFragment, baseFragment);
                }
            }
        });
        rgMain.check(R.id.rb_home);
    }

    /**
     * 切换页面方法   此方法为了避免重复创建浪费资源
     * 若用户点击同一个radioButton即不在新建而是不切换页面
     *
     * @param fromFragment 从这个页面切换  即上文的tempFragment
     * @param nextFragment 到这个页面      即上文的baseFragment
     */
    private void switchFragment(BaseFragment fromFragment, BaseFragment nextFragment) {
        if (fromFragment != nextFragment) {
            //做缓存
            tempFragment = nextFragment;
            if (nextFragment != null) {
                /*因为3.0以下版本  是没有fragment的api  所以必须借助V4包里面的getSupportFragmentManager()方法来间接获取FragmentManager()对象。
            3.0版本之后，有了Fragment的api，就可以直接使用getFragmentManager()这个方法来获取了*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!nextFragment.isAdded()) {
                    if (fromFragment != null) {
                        transaction.remove(fromFragment);
                        //hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    if (fromFragment != null) {
                        transaction.remove(fromFragment);
                        //transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    /**
     * 初始化Fragment集合方法
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new TypeFragment());
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new ShoppingCartFragment());
        fragmentList.add(new UserFragment());
    }


    private void jumpToCart() {
        boolean show_cart = getIntent().getBooleanExtra("show_cart", false);
        LogUtil.e("是否显示购物车页面" + show_cart);
        if (show_cart) {
            rgMain.check(R.id.rb_cart);
        } else {
            rgMain.check(R.id.rb_home);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
