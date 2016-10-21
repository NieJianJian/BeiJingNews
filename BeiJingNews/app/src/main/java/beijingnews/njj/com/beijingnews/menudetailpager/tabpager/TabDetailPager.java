package beijingnews.njj.com.beijingnews.menudetailpager.tabpager;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;

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

    private final NewsCenterPagerBean.DataBean.ChildrenBean mChildrenBean;
//    TextView textView;

    public TabDetailPager(Activity activity, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(activity);
        this.mChildrenBean = childrenBean;
    }

    @Override
    public View initView() {
        Log.i("niejianjian", " -> TabDetailPager -> initView");
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
        Log.i("niejianjian", " -> TabDetailPager -> initData() -> " + mChildrenBean.getTitle());
    }
}
