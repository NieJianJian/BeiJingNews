package beijingnews.njj.com.beijingnews.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import beijingnews.njj.com.beijingnews.base.BaseFragment;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;

/**
 * Created by Administrator on 2016/9/23.
 */
public class LeftMenuFragment extends BaseFragment {

    private static final String TAG = LeftMenuFragment.class.getSimpleName();
    private List<NewsCenterPagerBean.DataBean> mLeftMenuData;

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("这是左侧菜单");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    /**
     * 接受数据
     *
     * @param leftMenuData
     */
    public void setLeftMenuData(List<NewsCenterPagerBean.DataBean> leftMenuData) {
        this.mLeftMenuData = leftMenuData;
        for (int i = 0; i < leftMenuData.size(); i++) {
            Log.i("niejianjian", " -> setLeftMenuData -> " + leftMenuData.get(i).getTitle());
        }

    }
}
