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

import java.util.ArrayList;
import java.util.List;

import beijingnews.njj.com.beijingnews.activity.MainActivity;
import beijingnews.njj.com.beijingnews.base.BasePager;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;
import beijingnews.njj.com.beijingnews.fragment.LeftMenuFragment;
import beijingnews.njj.com.beijingnews.menudetailpager.InteracMenuDetailPager;
import beijingnews.njj.com.beijingnews.menudetailpager.NewsMenuDetailPager;
import beijingnews.njj.com.beijingnews.menudetailpager.PhotosMenuDetailPager;
import beijingnews.njj.com.beijingnews.menudetailpager.TopicMenuDetailPager;
import beijingnews.njj.com.beijingnews.utils.ConstantUtils;

/**
 * Created by Administrator on 2016/10/10.
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {

    // 左侧菜单对应的数据
    private List<NewsCenterPagerBean.DataBean> leftMenuData;
    // 左侧菜单对应的详情页面的集合
    private List<MenuDetailBasePager> mMenuDetailBasePagers;

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
        fl_child_content.removeAllViews();
        fl_child_content.addView(textView);

        getDataFromNet();

    }

    private void getDataFromNet() {
        Log.i("niejianjian", " -> getDataFromNet -> result = ");
        // 在子线程
        RequestParams params = new RequestParams(ConstantUtils.newscenter_url);
        // 使用xUitls之前，在application中进行初始化
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
        // 解析数据- gson解析 和 手动
        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(json, NewsCenterPagerBean.class);
//        String title = bean.getData().get(0).getChildren().get(2).getTitle();
        leftMenuData = bean.getData();
        // MainActivity实例传递给MainFragment，再给了NewsCenterPager。
        // 所以，当前的mActivity对象，就是MainActivity实例，直接强转就可以
        MainActivity activity = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = activity.getLeftMenuFragment();
        leftMenuFragment.setLeftMenuData(leftMenuData);

        // 添加新闻详情页面，专题详情页面，组图详情页面，互动详情页面
        mMenuDetailBasePagers = new ArrayList<MenuDetailBasePager>();
        mMenuDetailBasePagers.add(new NewsMenuDetailPager(mActivity));
        mMenuDetailBasePagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailBasePagers.add(new PhotosMenuDetailPager(mActivity));
        mMenuDetailBasePagers.add(new InteracMenuDetailPager(mActivity));


    }

    /**
     *
     */
    public void switchMenuDetailPager(int position) {
        // 设置标题
        tv_title.setText(leftMenuData.get(position).getTitle());

        MenuDetailBasePager detailBasePager = mMenuDetailBasePagers.get(position);
        View rootView = detailBasePager.mRootView;
        fl_child_content.removeAllViews();
        fl_child_content.addView(rootView);
        // 还没有掉detailBasePager.initData()方法
        detailBasePager.initData();
    }
}
