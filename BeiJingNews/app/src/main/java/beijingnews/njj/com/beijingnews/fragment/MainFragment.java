package beijingnews.njj.com.beijingnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.BaseFragment;
import beijingnews.njj.com.beijingnews.base.BasePager;
import beijingnews.njj.com.beijingnews.pager.GovaffairPager;
import beijingnews.njj.com.beijingnews.pager.HomePager;
import beijingnews.njj.com.beijingnews.pager.NewsCenterPager;
import beijingnews.njj.com.beijingnews.pager.SettingPager;
import beijingnews.njj.com.beijingnews.pager.SmartServicePager;
import beijingnews.njj.com.beijingnews.view.NoScrollViewPager;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MainFragment extends BaseFragment {

    /*
     * ViewPager的使用
     * 1.布局的定义
     * 2.实例化
     * 3.准备数据
     * 4.设置适配器
     */
    @ViewInject(R.id.main_fragment_viewpager)
    private NoScrollViewPager mViewPager;
    @ViewInject(R.id.rg_bottom_tag)
    private RadioGroup mRadioGroup;

    private ArrayList<BasePager> mBasePagers;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.main_fragment, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        // 设置默认高亮显示的首页
        mRadioGroup.check(R.id.rb_home);

        mBasePagers = new ArrayList<BasePager>();
        mBasePagers.add(new HomePager(mActivity));
        mBasePagers.add(new NewsCenterPager(mActivity));
        mBasePagers.add(new SmartServicePager(mActivity));
        mBasePagers.add(new GovaffairPager(mActivity));
        mBasePagers.add(new SettingPager(mActivity));

        // 设置适配器
        mViewPager.setAdapter(new ContentFragmentAdapter());

        // 监听RadioGroup的状态
        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.rb_newscenter:
                    mViewPager.setCurrentItem(1, false);
                    break;
                case R.id.rb_smartservice:
                    mViewPager.setCurrentItem(2, false);
                    break;
                case R.id.rb_govaffair:
                    mViewPager.setCurrentItem(3, false);
                    break;
                case R.id.rb_settings:
                    mViewPager.setCurrentItem(4, false);
                    break;
            }
        }
    }

    class ContentFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBasePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public ContentFragmentAdapter() {
            super();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = mBasePagers.get(position);
            View rootView = basePager.mRootView;
            container.addView(rootView);
            // 本应该调用,如果不调用，就没有数据
            basePager.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
