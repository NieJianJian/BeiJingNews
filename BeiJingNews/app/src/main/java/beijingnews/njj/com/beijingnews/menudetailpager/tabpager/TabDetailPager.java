package beijingnews.njj.com.beijingnews.menudetailpager.tabpager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.activity.NewsDetailActivity;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;
import beijingnews.njj.com.beijingnews.domain.TabDetailPagerBean;
import beijingnews.njj.com.beijingnews.utils.CacheUtils;
import beijingnews.njj.com.beijingnews.utils.ConstantUtils;
import beijingnews.njj.com.beijingnews.view.HorizontalViewPager;
import beijingnews.njj.com.beijingnews.view.RefreshListView;

/**
 * 代表页签页面
 * Created by Administrator on 2016/10/19.
 */
public class TabDetailPager extends MenuDetailBasePager {

    public static final String READ_ARRAY_ID = "read_array_id";
    @ViewInject(R.id.viewpager_tabdetail)
    private HorizontalViewPager mViewPager_TabDetail;
    @ViewInject(R.id.tv_tabledetail)
    private TextView mTextView_TabDetail;
    @ViewInject(R.id.listview_tabdetail)
    private RefreshListView mListView_TabDetail;
    @ViewInject(R.id.ll_poing_tabledetail)
    private LinearLayout mLl_Poing_TabDetail;

    private String url;
    private final NewsCenterPagerBean.DataBean.ChildrenBean mChildrenBean;
    private List<TabDetailPagerBean.News> news;
    //    TextView textView;
    private List<TabDetailPagerBean.Topnews> topnews;
    private ImageOptions imageOptions;
    private int widthDpi;
    private int preSelectPosition = 0;
    private boolean isPullDownRefresh = false;
    private String moreUrl;
    private boolean isLoadMore = false;
    private NewsListAdapter mNewsListAdapter;
    private InternalHandler internalHandler;

    public TabDetailPager(Activity activity, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(activity);
        this.mChildrenBean = childrenBean;

        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
                .setRadius(DensityUtil.dip2px(5))
                .setCrop(false) // 如果iamgeview的大小不是定义的wrap_content,不要crop
//                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)// 加载中或错误图片的scaletype，
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.home_scroll_default)
                .setFailureDrawableId(R.drawable.home_scroll_default)
                .build();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tabdetail_pager, null);
        x.view().inject(this, view);

        View topnews_view = View.inflate(mActivity, R.layout.topnews_part, null);
        x.view().inject(this, topnews_view);

//        mListView_TabDetail.addHeaderView(topnews_view); // 调用自定义的添加view
        mListView_TabDetail.addTopNewsView(topnews_view);
        mListView_TabDetail.setOnRefreshListener(new MyOnRefreshListener());

        mListView_TabDetail.setOnItemClickListener(new MyOnItemClickListener());

        return view;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 1;
            TabDetailPagerBean.News newsItem = TabDetailPager.this.news.get(realPosition);
            Log.i("niejianjian", "realPosition -> " + realPosition
                    + " ; newsItem.Id -> " + newsItem.getId()
                    + " ; newsItem.title -> " + newsItem.getTitle());
            // 首先读取id是否被保存，如果没有被保存，就去保存，并且发送消息更新
            String saveIds = CacheUtils.getString(mActivity, READ_ARRAY_ID);
            if (!saveIds.contains(newsItem.getId() + "")) {
                CacheUtils.putString(mActivity, READ_ARRAY_ID, saveIds + newsItem.getId() + ",");
                mNewsListAdapter.notifyDataSetChanged(); // getCount() -> getView()
            }

            Intent intent = new Intent(mActivity, NewsDetailActivity.class);
            intent.putExtra("url", newsItem.getUrl());
            mActivity.startActivity(intent);

        }
    }

    @Override
    public void initData() {
        super.initData();
//        textView.setText(mChildrenBean.getTitle());
        url = ConstantUtils.base_url + mChildrenBean.getUrl();
        Log.i("niejianjian", " -> TabDetailPager -> initData -> url = " + url);

        String json = CacheUtils.getString(mActivity, url);
        if (!TextUtils.isEmpty(json)) {
            processData(json);
        }
        mNewsListAdapter = new NewsListAdapter();
        // 联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("niejianjian", " -> ************** onResponse -> s = " + s);
                if (!isPullDownRefresh) {
                    isPullDownRefresh = true;
                    mListView_TabDetail.onRefreshFinish(true);
                }
                CacheUtils.putString(mActivity, url, s);
                processData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (!isPullDownRefresh) {
                    isPullDownRefresh = true;
                    mListView_TabDetail.onRefreshFinish(false);
                }
                Log.i("niejianjian", " -> ************** onErrorResponse -> volleyError = " + volleyError);
            }
        }) { // code -> override,实现下面的方法，解决乱码问题
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonData = new String(response.data, "UTF-8");
                    return Response.success(jsonData, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    private void processData(String json) {
        TabDetailPagerBean detailPagerBean = getTabDetailPagerBean(json);
        Log.i("niejianjian", "TabDetailPager -> processData -> detailPagerBean = " + detailPagerBean.getData().getNews().get(0).getTitle());

        if (!isLoadMore) {
            // 原来的处理
            topnews = detailPagerBean.getData().getTopnews();

            mViewPager_TabDetail.setAdapter(new TopnewsAdapter());

            // 把所有的点移除（因为添加了缓存，这个方法会执行两边，防止重复，所以，第二次执行前，先把之前的清空）
            mLl_Poing_TabDetail.removeAllViews();
            widthDpi = beijingnews.njj.com.beijingnews.utils.DensityUtil.dip2px(mActivity, 5);
            // 设置红点
            for (int i = 0; i < topnews.size(); i++) {
                ImageView point = new ImageView(mActivity);
                point.setBackgroundResource(R.drawable.tabdetail_point_selector);

                // 添加到线性布局中
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi, widthDpi);
                if (i != 0) {
                    params.leftMargin = widthDpi;
                }
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                point.setLayoutParams(params);
                // 把点添加到线性布局中去
                mLl_Poing_TabDetail.addView(point);
            }

            // 设置页面的监听
            mViewPager_TabDetail.setOnPageChangeListener(new TopNewsOnPageChangeListener());
            // 默认设置第0个高亮显示
            mLl_Poing_TabDetail.getChildAt(0).setEnabled(true);
            mTextView_TabDetail.setText(topnews.get(preSelectPosition).getTitle());

            // 设置ListView的适配器
            news = detailPagerBean.getData().getNews();
            mListView_TabDetail.setAdapter(mNewsListAdapter);

        } else {
            // 加载更多的处理
            // 1.得到加载更多的数据
            List<TabDetailPagerBean.News> moreNews = detailPagerBean.getData().getNews();
            // 2.把加载更多的数据放到原来的集合中
            news.addAll(moreNews);
            // 3.刷新数据
            mNewsListAdapter.notifyDataSetChanged();
        }

        // 每隔4s让图片滑动一次
        if (internalHandler == null) {
            internalHandler = new InternalHandler();
        } else {
            // 把消息移除把任务移除
            internalHandler.removeCallbacksAndMessages(null);
        }

        internalHandler.postDelayed(new MyRunable(), 3000);

    }

    class InternalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            int item = (mViewPager_TabDetail.getCurrentItem() == (mViewPager_TabDetail.getChildCount() - 1)) ? 0 : mViewPager_TabDetail.getCurrentItem() + 1;
            int item = (mViewPager_TabDetail.getCurrentItem() + 1) % topnews.size();
            mViewPager_TabDetail.setCurrentItem(item);
            internalHandler.postDelayed(new MyRunable(), 3000);
        }
    }

    class MyRunable implements Runnable {

        @Override
        public void run() {
            internalHandler.sendEmptyMessage(0);
        }
    }

    private TabDetailPagerBean getTabDetailPagerBean(String json) {
        Gson gson = new Gson();
        TabDetailPagerBean detailPagerBean = gson.fromJson(json, TabDetailPagerBean.class);
        if (TextUtils.isEmpty(detailPagerBean.getData().getMore())) {
            moreUrl = "";
        } else {
            moreUrl = ConstantUtils.base_url + detailPagerBean.getData().getMore();
        }
        return detailPagerBean;
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener {

        @Override
        public void onPullDownRefresh() {
            isPullDownRefresh = false;
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            if (TextUtils.isEmpty(moreUrl)) {
                // 隐藏加载更多布局
                mListView_TabDetail.onRefreshFinish(false);
                // 不需要加载更多
                Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
            } else {
                getMoreDataFromNet();
            }
        }
    }

    private void getMoreDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        StringRequest request = new StringRequest(Request.Method.GET, moreUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                isLoadMore = true;
                // 隐藏加载更多布局
                mListView_TabDetail.onRefreshFinish(false);

                processData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonData = new String(response.data, "UTF-8");
                    return Response.success(jsonData, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    class NewsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.table_detail_item, null);
                holder = new ViewHolder();
                holder.iv_icon_tab_detail = (ImageView) convertView.findViewById(R.id.iv_icon_tab_detail);
                holder.tv_title_tab_detail = (TextView) convertView.findViewById(R.id.tv_title_tab_detail);
                holder.tv_time_table_detail = (TextView) convertView.findViewById(R.id.tv_time_table_detail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 根据位置得到数据
            TabDetailPagerBean.News newsItem = TabDetailPager.this.news.get(position);
            holder.tv_title_tab_detail.setText(newsItem.getTitle());
            holder.tv_time_table_detail.setText(newsItem.getPubdate());

            String saveIds = CacheUtils.getString(mActivity, READ_ARRAY_ID);
            if (saveIds.contains(newsItem.getId() + "")) {
                // 灰色
                holder.tv_title_tab_detail.setTextColor(Color.GRAY);
            } else {
                // 黑色
                holder.tv_title_tab_detail.setTextColor(Color.BLACK);
            }

            x.image().bind(holder.iv_icon_tab_detail,
                    newsItem.getListimage().
                            replaceAll("http://10.0.2.2:8080/zhbj", ConstantUtils.base_url),
                    imageOptions);

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    static class ViewHolder {
        ImageView iv_icon_tab_detail;
        TextView tv_title_tab_detail;
        TextView tv_time_table_detail;
    }

    class TopNewsOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 取消上一个点的高亮显示
            mLl_Poing_TabDetail.getChildAt(preSelectPosition).setEnabled(false);
            // 设置当前选中点的高亮显示
            mLl_Poing_TabDetail.getChildAt(position).setEnabled(true);
            mTextView_TabDetail.setText(topnews.get(position).getTitle());
            // 更新记录高亮点
            preSelectPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TopnewsAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setImageResource(R.drawable.home_scroll_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            String imageUrl = topnews.get(position).getTopimage();
            imageUrl = imageUrl.replaceAll("http://10.0.2.2:8080/zhbj", ConstantUtils.base_url);
            Log.i("niejianjian", " -> imageUrl -> " + imageUrl);
            // 联网请求图片
            x.image().bind(imageView, imageUrl, imageOptions);

            // 设置图片的触摸事件监听，点击时，取消图片自动轮播效果
            imageView.setOnTouchListener(new MyOnTouchListener());

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (internalHandler != null) {
                        internalHandler.removeCallbacksAndMessages(null);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (internalHandler != null) {
                        internalHandler.postDelayed(new MyRunable(), 3000);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (internalHandler != null) {
                        internalHandler.postDelayed(new MyRunable(), 3000);
                    }
                    break;
                default:
                    break;
            }
            return true; // 要返回true，否则不执行up和cancel方法，因为false代表不处理
        }
    }
}
