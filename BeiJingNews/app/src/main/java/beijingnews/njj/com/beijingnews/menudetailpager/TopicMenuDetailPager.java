package beijingnews.njj.com.beijingnews.menudetailpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;

/**
 * Created by Administrator on 2016/10/17.
 */
public class TopicMenuDetailPager extends MenuDetailBasePager {

    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("菜单——专题详情页面");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return null;
    }

    @Override
    public void initData() {
        super.initData();
        Log.i("niejianjian", " -> 菜单——专题-详情页面数据被初始化了 ->");
    }
}
