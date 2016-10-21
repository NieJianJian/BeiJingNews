package beijingnews.njj.com.beijingnews.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/10/16.
 * 是新闻、专题、组图和互动的详情页面公共类
 */
public abstract class MenuDetailBasePager {
    public final Activity mActivity;
    public View mRootView;

    public MenuDetailBasePager(Activity activity) {
        this.mActivity = activity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
