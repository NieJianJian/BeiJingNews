package beijingnews.njj.com.beijingnews.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.R;

/**
 * Created by Administrator on 2016/10/10.
 * 首页，新闻，智慧服务，正要指南，设置中心页面的公共页面
 */
public class BasePager {

    public final Activity mActivity;

    public View mRootView;
    public FrameLayout fl_child_content;
    public ImageButton ib_menu;
    public TextView tv_title;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        mRootView = initView();
    }

    private View initView() {
        View view = View.inflate(mActivity, R.layout.basepager, null);
        fl_child_content = (FrameLayout) view.findViewById(R.id.fl_child_content);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    public void initData() {

    }

}
