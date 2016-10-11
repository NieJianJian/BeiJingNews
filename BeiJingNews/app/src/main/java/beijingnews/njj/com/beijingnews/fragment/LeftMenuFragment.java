package beijingnews.njj.com.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/23.
 */
public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("这是左侧菜单");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}