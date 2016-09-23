package beijingnews.njj.com.beijingnews.fragment;

import android.view.View;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MainFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.main_fragment, null);
        return view;
    }
}
