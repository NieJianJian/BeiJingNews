package beijingnews.njj.com.beijingnews.menudetailpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.MenuDetailBasePager;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;
import beijingnews.njj.com.beijingnews.menudetailpager.tabpager.TabDetailPager;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {

    List<NewsCenterPagerBean.DataBean.ChildrenBean> mChildrenBeen;
    private List<MenuDetailBasePager> mDetailPagers;

    @ViewInject(R.id.viewpager_news_menu_detail)
    private ViewPager mViewPager;

    public NewsMenuDetailPager(Activity activity, NewsCenterPagerBean.DataBean dataBean) {
        super(activity);
        this.mChildrenBeen = dataBean.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail_pager, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
//        textView.setText("菜单——新闻详情页面");
        Log.i("niejianjian", " -> 菜单——新闻-详情页面数据被初始化了 ->");
        mDetailPagers = new ArrayList<MenuDetailBasePager>();
        for (int i = 0; i < mChildrenBeen.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity, mChildrenBeen.get(i));
            mDetailPagers.add(tabDetailPager);
        }

        // 设置适配器
        mViewPager.setAdapter(new NewsMenuDetailPagerAdapter());
    }

    class NewsMenuDetailPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            MenuDetailBasePager tabDetailPager = mDetailPagers.get(position);
            View rootView = tabDetailPager.mRootView;
            container.addView(rootView);
            tabDetailPager.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 如果返回false，任何view，都无法添加到viewpager界面上
//            return false;
            return view == object;
        }
    }

}
