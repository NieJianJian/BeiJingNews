package beijingnews.njj.com.beijingnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MainFragment extends BaseFragment {

    @ViewInject(R.id.main_fragment_viewpager)
    private ViewPager mViewPager;
    @ViewInject(R.id.rg_bottom_tag)
    private RadioGroup mRadioGroup;

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
    }
}
