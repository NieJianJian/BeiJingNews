package beijingnews.njj.com.beijingnews.menudetailpager.tabpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;
import beijingnews.njj.com.beijingnews.domain.TabDetailPagerBean;
import beijingnews.njj.com.beijingnews.utils.CacheUtils;
import beijingnews.njj.com.beijingnews.utils.ConstantUtils;

/**
 * 代表页签页面
 * Created by Administrator on 2016/10/19.
 */
public class TabDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.viewpager_tabdetail)
    private ViewPager mViewPager_TabDetail;
    @ViewInject(R.id.tv_tabledetail)
    private TextView mTextView_TabDetail;
    @ViewInject(R.id.listview_tabdetail)
    private ListView mListView_TabDetail;
    @ViewInject(R.id.ll_poing_tabledetail)
    private LinearLayout mLl_Poing_TabDetail;

    private String url;
    private final NewsCenterPagerBean.DataBean.ChildrenBean mChildrenBean;
    //    TextView textView;
    private List<TabDetailPagerBean.Topnews> topnews;
    private ImageOptions imageOptions;
    private int widthDpi;
    private int preSelectPosition = 0;

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
//        textView = new TextView(mActivity);
//        textView.setTextSize(30);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);
        return view;
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

        // 联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("niejianjian", " -> ************** onResponse -> s = " + s);
                CacheUtils.putString(mActivity, url, s);
                processData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
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
        Gson gson = new Gson();
        TabDetailPagerBean detailPagerBean = gson.fromJson(json, TabDetailPagerBean.class);
        Log.i("niejianjian", "TabDetailPager -> processData -> detailPagerBean = " + detailPagerBean.getData().getNews().get(0).getTitle());

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
}
