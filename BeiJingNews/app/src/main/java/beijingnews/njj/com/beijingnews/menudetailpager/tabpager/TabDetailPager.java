package beijingnews.njj.com.beijingnews.menudetailpager.tabpager;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;
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

    public TabDetailPager(Activity activity, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(activity);
        this.mChildrenBean = childrenBean;
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

        // 联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("niejianjian", " -> ************** onResponse -> s = " + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("niejianjian", " -> ************** onErrorResponse -> volleyError = " + volleyError);
            }
        }) { // code->override,实现下面的方法，解决乱码问题
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
}
