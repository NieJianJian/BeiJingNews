package beijingnews.njj.com.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import beijingnews.njj.com.beijingnews.R;

/**
 * Created by jian on 2016/10/25.
 */
public class RefreshListView extends ListView {

    private LinearLayout mHeaderView;
    private View ll_pull_down_refresh; // 下拉刷新控件
    private int pull_down_refresh_height;

    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        mHeaderView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ll_pull_down_refresh = mHeaderView.findViewById(R.id.ll_pull_down_refresh);

        ll_pull_down_refresh.measure(0, 0); // 测量
        pull_down_refresh_height = ll_pull_down_refresh.getMeasuredHeight(); // 测量后才有值
        ll_pull_down_refresh.setPadding(0, -pull_down_refresh_height, 0, 0); // 隐藏

        // 将headerview添加到listview的头部分
        this.addHeaderView(mHeaderView);
    }
}
