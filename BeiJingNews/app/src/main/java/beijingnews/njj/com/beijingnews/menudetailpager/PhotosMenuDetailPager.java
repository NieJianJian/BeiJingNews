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
public class PhotosMenuDetailPager extends MenuDetailBasePager {

    public PhotosMenuDetailPager(Activity activity) {
        super(activity);
    }

    TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mActivity);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("菜单——组图详情页面");
        Log.i("niejianjian", " -> 菜单——组图-详情页面数据被初始化了 ->");
    }
}
