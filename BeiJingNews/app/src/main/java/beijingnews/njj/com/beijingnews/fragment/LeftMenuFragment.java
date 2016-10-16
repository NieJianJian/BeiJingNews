package beijingnews.njj.com.beijingnews.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.activity.MainActivity;
import beijingnews.njj.com.beijingnews.base.BaseFragment;
import beijingnews.njj.com.beijingnews.domain.NewsCenterPagerBean;

/**
 * Created by Administrator on 2016/9/23.
 */
public class LeftMenuFragment extends BaseFragment {

    private static final String TAG = LeftMenuFragment.class.getSimpleName();
    private List<NewsCenterPagerBean.DataBean> mLeftMenuData;
    private ListView mListView;

    // 上一次被点击的位置
    private int mPrePositon = 0;
    private LeftMenuAdapter mLeftMenuAdapter;

    @Override
    public View initView() {
        mListView = new ListView(mActivity);
        mListView.setPadding(0, 50, 0, 0);
        // 在低版本屏幕按下变色
        mListView.setCacheColorHint(Color.TRANSPARENT);
        // 设置线条高度为0
        mListView.setDividerHeight(0);
        // 在低版本屏幕按下某一条的变色
        mListView.setSelector(android.R.color.transparent);

        // 设置点击事件
        mListView.setOnItemClickListener(new LeftMenuItemClickListener());

        return mListView;
    }

    class LeftMenuItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 1.记录当前点击的某条的位置
            mPrePositon = position;
            // 适配器刷新 getCount -> getView();
            mLeftMenuAdapter.notifyDataSetChanged();
            // 2.在适配器中的getView方法中设置我们TextView是否为enable

            // 3.把菜单收起
            MainActivity activity = (MainActivity) mActivity;
            // 切换页面
            activity.getSlidingMenu().toggle();
            // 4. 切换到具体的页面：新闻、专题、组图、互动
        }
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

        mLeftMenuAdapter = new LeftMenuAdapter();
        mListView.setAdapter(mLeftMenuAdapter);

    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLeftMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(mActivity, R.layout.leftmenu_item, null);
            // 设置数据
            textView.setText(mLeftMenuData.get(position).getTitle());
            textView.setEnabled(mPrePositon == position);

            return textView;
        }
    }
}
