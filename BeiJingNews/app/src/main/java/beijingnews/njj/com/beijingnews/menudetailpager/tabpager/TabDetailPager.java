package beijingnews.njj.com.beijingnews.menudetailpager.tabpager;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;

/**
 * 代表页签页面
 * Created by Administrator on 2016/10/19.
 */
public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataBean.ChildrenBean mChildrenBean;
    TextView textView;

    public TabDetailPager(Activity activity, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(activity);
        this.mChildrenBean = childrenBean;
    }

    @Override
    public View initView() {
        Log.i("niejianjian"," -> TabDetailPager -> initView");
        textView = new TextView(mActivity);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText(mChildrenBean.getTitle());
        Log.i("niejianjian", " -> TabDetailPager -> initData() -> " + mChildrenBean.getTitle());
    }
}
