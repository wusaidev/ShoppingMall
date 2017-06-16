package com.happy.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.happy.shoppingmall.R;
import com.happy.shoppingmall.app.GoodsInfoActivity;
import com.happy.shoppingmall.app.MainActivity;
import com.happy.shoppingmall.home.bean.GoodsDetailBean;
import com.happy.shoppingmall.home.bean.ResultBeanData;
import com.happy.shoppingmall.utils.ConstantsValue;
import com.happy.shoppingmall.utils.DensityUtil;
import com.happy.shoppingmall.utils.LogUtil;
import com.happy.shoppingmall.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;


/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/11.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /**
     * 条幅类型
     */
    public static final int BANNER = 0;
    /**
     * 频道类型
     */
    public static final int CHANNEL = 1;
    /**
     * 活动类型
     */
    public static final int ACT = 2;
    /**
     * 秒杀类型
     */
    public static final int SECKILL = 3;
    /**
     * 推荐类型
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖类型
     */
    public static final int HOT = 5;
    /**
     * 当前类型
     */
    public int currentType = BANNER;
    private Context mContext;
    private ResultBeanData.ResultBean result;
    private List<ResultBeanData.ResultBean.RecommendInfoBean> recommendInfoList;
    private List<ResultBeanData.ResultBean.HotInfoBean> hotInfoList;
    private final List<ResultBeanData.ResultBean.ActInfoBean> act_info;

    private final LayoutInflater mLayoutInflater;
    //序列化传送对象
    private GoodsDetailBean goodsDetail;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;
    private String defHtmlUrl;

    /**
     * HomeFragmentAdapter构造方法
     *
     * @param mContext
     * @param result
     */
    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean result) {
        this.mContext = mContext;
        this.result = result;
        recommendInfoList =result.getRecommend_info();
        hotInfoList=result.getHot_info();
        mLayoutInflater = LayoutInflater.from(mContext);
        goodsDetail=new GoodsDetailBean();
        act_info = result.getAct_info();
        recommend_info = result.getRecommend_info();
        defHtmlUrl="https://item.taobao.com/item.htm?spm=a230r.1.14.16.4gpXm5" +
                "&id=541129381376&ns=1&abbucket=4#detail";
    }

    /**
     * 创建ViewHolder实例方法
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case BANNER:
                viewHolder = new BannerViewHolder(mContext, result,
                        mLayoutInflater.inflate(R.layout.banner_viewpager, null));
                break;
            case CHANNEL:
                viewHolder = new ChannelViewHolder(mLayoutInflater.inflate
                        (R.layout.channel_item, null));
                break;
            case ACT:
                viewHolder = new ActViewHolder(mLayoutInflater.inflate
                        (R.layout.act_item, null));
                break;
            case SECKILL:
                viewHolder=new SecKillViewHolder(mLayoutInflater.
                        inflate(R.layout.seckill_item,null));
                break;
            case RECOMMEND:
                viewHolder=new RecommendViewHolder(mLayoutInflater.
                        inflate(R.layout.recommend_item,null));
                break;
            case HOT:
                viewHolder=new HotViewHolder(mLayoutInflater.
                        inflate(R.layout.hot_item,null));
                break;
            default:
                viewHolder = null;
                break;
        }
        /*if(viewType==BANNER){
            return new BannerViewHolder(mContext,result,mLayoutInflater.inflate
                    (R.layout.banner_viewpager,null));
        }e*/
        return viewHolder;
    }

    /**
     * 得到类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
            default:
                break;
        }
        return currentType;
    }

    /**
     * 绑定数据方法
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据；
            bannerViewHolder.setData(result.getBanner_info());
            //bannerViewHolder.setBannerData(result.getBanner_info());
        }else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData();
        }else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData();
        }else if (getItemViewType(position) == SECKILL) {
            SecKillViewHolder  secKillViewHolder= (SecKillViewHolder) holder;
            secKillViewHolder.setData();
        }else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData();
        }else if(getItemViewType(position)==HOT){
            HotViewHolder hotViewHolder= (HotViewHolder) holder;
            hotViewHolder.setData();
        }
    }
    /**
     * 显示条目数量
     * 多种类型是时不要全部显示  由1开始  做一个加一个
     *
     * @return
     */
    @Override
    public int getItemCount () {
        return 6;
    }


    /**
     * banner的ViewHolder类
     */
    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Banner banner;
        private List<String> imgUrlList;

        public BannerViewHolder(Context mContext, ResultBeanData.ResultBean result, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            imgUrlList = new ArrayList<>();
            banner.setImages(imgUrlList);
            for (int i = 0; i < banner_info.size(); i++) {
                String imgUrl = ConstantsValue.IMG_BASE_Url + banner_info.get(i).getImage();
                imgUrlList.add(imgUrl);
            }

            banner.setImages(imgUrlList);
            banner.setImageLoader(new ImageLoaderInterface() {
                @Override
                public void displayImage(Context context, Object path, View imageView) {
                    Glide.with(context)
                            .load(path)
                            .into((ImageView) imageView);
                }

                @Override
                public View createImageView(Context context) {
                    return null;
                }
            });
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ToastUtil.show(mContext, "position===" + position);
                    goodsDetail.setImgUrl(ConstantsValue.IMG_BASE_Url + banner_info.get(position).getImage());
                    //数据为空添加默认
                    goodsDetail.setName("超赞的商品");
                    goodsDetail.setConvertPrice("超低的价格");
                    goodsDetail.setHtmlUrl(ConstantsValue.IMG_BASE_Url+banner_info.get(position).getValue().getUrl());
                    startGoodsInfoActivity(goodsDetail);
                }
            });
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setDelayTime(4000);
            banner.setBannerAnimation(Transformer.Accordion);
            banner.start();
        }
    }


    /**
     * 频道部分  类型的ViewHolder
     */
    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_channel;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData() {
            gv_channel.setAdapter(new ChannelGVAdapter());
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastUtil.show(mContext, "position===" + position);
                }
            });
        }

        class ChannelGVAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                int gridCount = 0;
                if (result != null) {
                    gridCount = result.getChannel_info().size();
                }
                return gridCount;
            }

            @Override
            public Object getItem(int position) {
                View view = mLayoutInflater.inflate(R.layout.item_grid_channel, null);
                return view;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder = null;
                if (convertView == null) {
                    convertView = (View) getItem(position);
                    //mLayoutInflater.inflate(R.layout.item_channel,null);
                    viewHolder = new ViewHolder();
                    viewHolder.iv_channel = (ImageView)
                            convertView.findViewById(R.id.iv_channel);
                    viewHolder.tv_channel = (TextView)
                            convertView.findViewById(R.id.tv_channel);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                ResultBeanData.ResultBean.ChannelInfoBean channelInfo = result.getChannel_info().get(position);
                String imgUrl = ConstantsValue.IMG_BASE_Url + channelInfo.getImage();
                Glide.with(mContext)
                        .load(imgUrl)
                        .into(viewHolder.iv_channel);
                viewHolder.tv_channel.setText(channelInfo.getChannel_name());
                return convertView;
            }
        }

        class ViewHolder {
            ImageView iv_channel;
            TextView tv_channel;
        }
    }

    /**
     *活动部分  类型的ViewHolder
     */
    class ActViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public ActViewHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData() {
            viewPager.setAdapter(new ActPagerAdapter());
            int distance= DensityUtil.dip2px(mContext,20);
            //设置 pager  间的间距
            viewPager.setPageMargin(distance);
            viewPager.setPageTransformer(true,
                    new RotateDownPageTransformer(new ScaleInTransformer()));
        }

        class ActPagerAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return result.getAct_info().size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                final String imgUrl = ConstantsValue.IMG_BASE_Url + act_info.get(position).getIcon_url();
                Glide.with(mContext)
                        .load(imgUrl)
                        .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(mContext,"position==="+position);
                        goodsDetail.setImgUrl(imgUrl);
                        goodsDetail.setName(act_info.get(position).getName());
                        goodsDetail.setConvertPrice("超低的价格");
                        goodsDetail.setHtmlUrl(ConstantsValue.IMG_BASE_Url+act_info.get(position).getUrl());

                        startGoodsInfoActivity(goodsDetail);
                    }
                });
                container.addView(imageView);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == (View) object;
            }
        }
    }

    /**
     *秒杀部分  类型的ViewHolder
     */
    class SecKillViewHolder extends RecyclerView.ViewHolder{
        //距离结束时间
        private boolean isFirst=true;
        private long dt=0;
        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LogUtil.e("收到消息");
                switch (msg.what){
                    case 0:
                        //秒杀时间倒计时
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                       // long timeS=dt/1000;
                        String time = simpleDateFormat.format(dt);
                        //LogUtil.e("时间===="+time);
                        tv_time.setText(time);
                        dt=dt-1000;
                        //LogUtil.e("时间===="+dt);
                        if (dt<=0){
                            handler.removeCallbacksAndMessages(0);
                        }else {
                            handler.removeCallbacksAndMessages(0);
                            handler.sendEmptyMessageDelayed(0,1000);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        private TextView tv_time;
        private TextView tv_more;
        private RecyclerView rv_seckill;

        public SecKillViewHolder(View itemView) {
            super(itemView);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more= (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill= (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        public void setData() {
            //第一次就发消息
            if(isFirst){

                dt= Long.valueOf(result.getSeckill_info().getEnd_time()).longValue()
                        *1000-System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                String time = simpleDateFormat.format(dt);
                isFirst=false;
                LogUtil.e("剩余时间===="+dt);
                LogUtil.e("剩余时间===="+time);
                LogUtil.e("结束时间===="+Long.valueOf(result.getSeckill_info().getEnd_time()).longValue());
                LogUtil.e("现在时间===="+System.currentTimeMillis());
            }
            handler.sendEmptyMessageDelayed(0,1000);

            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(mContext,"更多秒杀商品");
                }
            });
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_seckill.setLayoutManager(manager);
            SecAdapter secAdapter=new SecAdapter();
            rv_seckill.setAdapter(new SecAdapter());
            /*//秒杀时间倒计时
            String time = simpleDateFormat.format(dt);
            tv_time.setText(time);
            handler.sendEmptyMessageDelayed(0,1000);*/

        }

        class SecAdapter extends RecyclerView.Adapter{
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_recyclerview_seckill,null)); }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myViewHolder= (MyViewHolder) holder;
                myViewHolder.setData(position);
            }

            @Override
            public int getItemCount() {
                return result.getSeckill_info().getList().size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private ImageView iv_figure;
                private TextView tv_origin_price;
                private TextView tv_cover_price;
                private LinearLayout ll_root;
                public MyViewHolder(View itemView) {
                    super(itemView);
                    iv_figure= (ImageView) itemView.findViewById(R.id.iv_figure);
                    tv_origin_price= (TextView) itemView.findViewById(R.id.tv_origin_price);
                    tv_cover_price= (TextView) itemView.findViewById(R.id.tv_cover_price);
                    ll_root= (LinearLayout) itemView.findViewById(R.id.ll_root);
                }

                public void setData(final int position) {
                    //list是SeckillInfoBean下list集合  中的对象
                    final ResultBeanData.ResultBean.SeckillInfoBean.ListBean list
                            = result.getSeckill_info().getList().get(position);
                    final String imgUrl=ConstantsValue.IMG_BASE_Url+list.getFigure();
                    String origin_price=list.getOrigin_price();
                    String cover_price=list.getCover_price();
                    Glide.with(mContext)
                            .load(imgUrl)
                            .into(iv_figure);
                    tv_origin_price.setText(" ￥" + origin_price);
                    tv_cover_price.setText(" ￥" + cover_price);
                    ll_root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.show(mContext,"position==="+position);
                            goodsDetail.setImgUrl(imgUrl);
                            goodsDetail.setName(list.getName());
                            goodsDetail.setConvertPrice(list.getCover_price());
                            goodsDetail.setHtmlUrl(defHtmlUrl);
                            goodsDetail.setProductId(list.getProduct_id());
                            startGoodsInfoActivity(goodsDetail);
                        }
                    });

                }
            }
        }
    }

    /**
     *推荐部分  类型的ViewHolder
     */
    class RecommendViewHolder extends RecyclerView.ViewHolder{
        TextView tv_more;
        GridView gv_recommend;
        public RecommendViewHolder(View itemView) {
            super(itemView);
            tv_more= (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend= (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData() {
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(mContext,"推荐加载更多");
                }
            });
            gv_recommend.setAdapter(new RecommendAdapter());
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfo = recommend_info.get(position);
                    ToastUtil.show(mContext,"position==="+position);
                    goodsDetail.setImgUrl(ConstantsValue.IMG_BASE_Url+recommendInfo.getFigure());
                    goodsDetail.setName(recommendInfo.getName());
                    goodsDetail.setConvertPrice(recommendInfo.getCover_price());
                    goodsDetail.setHtmlUrl(defHtmlUrl);
                    goodsDetail.setProductId(recommendInfo.getProduct_id());
                    startGoodsInfoActivity(goodsDetail);
                }
            });
        }
        class RecommendAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return recommend_info.size();
            }

            @Override
            public Object getItem(int position) {
                return mLayoutInflater.inflate(R.layout.item_grid_recommend,null);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder=null;
                if(convertView==null){
                    viewHolder=new ViewHolder();
                    convertView= (View) getItem(position);
                    viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
                    viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                ResultBeanData.ResultBean.RecommendInfoBean recommendInfo=recommendInfoList.get(position);

                Glide.with(mContext)
                        .load(ConstantsValue.IMG_BASE_Url+recommendInfo.getFigure())
                        .into(viewHolder.iv_recommend);
                viewHolder.tv_name.setText(recommendInfo.getName());
                viewHolder.tv_price.setText(" ￥"+recommendInfo.getCover_price());
                return convertView;
            }
        }
        private class ViewHolder{
            ImageView iv_recommend;
            TextView tv_name;
            TextView tv_price;
        }
    }

    /**
     *热卖部分  类型的ViewHolder
     */
    class HotViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_more;
        private GridView gv_hot;
        public HotViewHolder(View itemView) {
            super(itemView);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData() {
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(mContext,"热卖加载更多");
                }
            });
            gv_hot.setAdapter(new HotAdapter());
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResultBeanData.ResultBean.HotInfoBean hotInfo = hotInfoList.get(position);
                    ToastUtil.show(mContext,"position==="+position);
                    goodsDetail.setImgUrl(ConstantsValue.IMG_BASE_Url+hotInfo.getFigure());
                    goodsDetail.setName(hotInfo.getName());
                    goodsDetail.setConvertPrice(hotInfo.getCover_price());
                    goodsDetail.setHtmlUrl(defHtmlUrl);
                    goodsDetail.setProductId(hotInfo.getProduct_id());
                    startGoodsInfoActivity(goodsDetail);
                }
            });
        }
        class HotAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return hotInfoList.size();
            }

            @Override
            public Object getItem(int position) {
                return mLayoutInflater.inflate(R.layout.item_hot_grid_view,null);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder=null;
                if(convertView==null){
                    convertView=(View) getItem(position);
                    viewHolder=new ViewHolder();
                    viewHolder.iv_hot= (ImageView) convertView.findViewById(R.id.iv_hot);
                    viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                    viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder= (ViewHolder) convertView.getTag();
                }
                ResultBeanData.ResultBean.HotInfoBean hotInfo = hotInfoList.get(position);
                Glide.with(mContext)
                        .load(ConstantsValue.IMG_BASE_Url+hotInfo.getFigure())
                        .into(viewHolder.iv_hot);
                viewHolder.tv_name.setText(hotInfo.getName());
                viewHolder.tv_price.setText(" ￥"+hotInfo.getCover_price());
                return convertView;
            }
        }
        private class ViewHolder{
            ImageView iv_hot;
            TextView tv_name;
            TextView tv_price;
        }
    }

    /**
     * 监听器
     *//*
    public interface OnSeckillRecyclerView{
        *//**当某条被点击的时候回调
         * @param position
         *//*
        public void OnItemClick(int position);
    }
    private OnSeckillRecyclerView onSeckillRecyclerView;

    *//**
     * 设置监听
     * @param
     *//*
    public void setOnSeckillRecycler(OnSeckillRecyclerView onSeckillRecyclerView){
        this.onSeckillRecyclerView=onSeckillRecyclerView;
    }
*/
    //打开商品详情页面方法
    private void startGoodsInfoActivity(GoodsDetailBean goodsDetail){
        Intent intent=new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra("goods_detail",goodsDetail);
        mContext.startActivity(intent);
        MainActivity mainActivity= (MainActivity) mContext;
        mainActivity.finish();
    }

}

