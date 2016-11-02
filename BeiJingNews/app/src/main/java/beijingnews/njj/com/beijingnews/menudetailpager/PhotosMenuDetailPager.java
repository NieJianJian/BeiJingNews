package beijingnews.njj.com.beijingnews.menudetailpager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.PhotosMenuDetailPagerBean;
import beijingnews.njj.com.beijingnews.utils.CacheUtils;
import beijingnews.njj.com.beijingnews.utils.ConstantUtils;

/**
 * Created by Administrator on 2016/10/17.
 */
public class PhotosMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.photos_listview)
    ListView mListView;
    @ViewInject(R.id.photos_gridview)
    GridView mGridView;
    private List<PhotosMenuDetailPagerBean.PhotosMenuDetailData.News> news;
    private PhotosAdapter mPhotosAdapter;

    public PhotosMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photos, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i("niejianjian", " -> 菜单——组图-详情页面数据被初始化了 ->");

        String json = CacheUtils.getString(mActivity, ConstantUtils.photos_url);
        if (!TextUtils.isEmpty(json)) {
            processData(json);
        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        StringRequest request = new StringRequest(Request.Method.GET, ConstantUtils.photos_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("niejianjian", "PhotosMenuDetailPager -> 联网成功 -> s = " + s);
                CacheUtils.putString(mActivity, ConstantUtils.photos_url, s);
                processData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("niejianjian", "PhotosMenuDetailPager -> 联网成功 -> volleyError = " + volleyError);
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonData = new String(response.data, "utf-8");
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
        PhotosMenuDetailPagerBean photosBean = gson.fromJson(json, PhotosMenuDetailPagerBean.class);
        Log.i("niejianjian", "PhotosMenuDetailPager ->  ->" + photosBean.getData().getNews().get(0).getTitle());
        // 得到数据
        news = photosBean.getData().getNews();
        // 设置适配器
        mPhotosAdapter = new PhotosAdapter();
        mListView.setAdapter(mPhotosAdapter);
        // 设置适配器item的布局


    }

    class PhotosAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.photos_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mPhotosImageView = (ImageView) convertView.findViewById(R.id.photos_imageview);
                viewHolder.mPhotosTextView = (TextView) convertView.findViewById(R.id.photos_textview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 绑定数据
            PhotosMenuDetailPagerBean.PhotosMenuDetailData.News newsItem = PhotosMenuDetailPager.this.news.get(position);

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView mPhotosImageView;
        TextView mPhotosTextView;
    }
}
