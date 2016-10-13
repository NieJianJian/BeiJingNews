package beijingnews.njj.com.beijingnews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import beijingnews.njj.com.beijingnews.base.BasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;

/**
 * Created by Administrator on 2016/10/10.
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();

        // 设置标题
        tv_title.setText("新闻中心");
        ib_menu.setVisibility(View.VISIBLE);

        // 设置内容
        TextView textView = new TextView(mActivity);
        textView.setText("这是新闻中心的内容");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_child_content.addView(textView);

        getDataFromNet();

    }

    private void getDataFromNet() {
        Log.i("niejianjian", " -> onSuccess -> result = ");
        // 在子线程
        RequestParams params = new RequestParams("http://api.aucauc.cn/api/indexpic/?t=android&token=&versionName=test_1.0.3&checksum=562e378e5f59&clientType=1&version=4");
//        RequestParams params = new RequestParams(ConstantUtils.newscenter_url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("niejianjian", " -> onSuccess -> result = " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("niejianjian", " -> onError -> ex = " + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("niejianjian", " -> onCancelled -> cex = " + cex.toString());
            }

            @Override
            public void onFinished() {
                Log.i("niejianjian", " -> onFinished -> ");
            }
        });

    }

    private void processData(String json) {
        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(json, NewsCenterPagerBean.class);
        String title = bean.getData().get(0).getChildren().get(2).getTitle();
    }


}
