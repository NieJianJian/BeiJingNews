package beijingnews.njj.com.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private float startY;
    private View topnews_view; // 顶部轮播图
    private float listViewOnScreenY = -1; // 在屏幕上listview的y轴的坐标

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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY(); // 必须调用ev.getY,直接调用getY是不行的
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = ev.getY();
                float distanceY = endY - startY;

                // 轮播图不完全显示，就没必要做任何处理（减少代码处理量）
                boolean isDisplayTopNewsView = isDisplayTopNewsView();
                if (!isDisplayTopNewsView) {
                    break;
                }

                if (distanceY > 0) {
                    float topPadding = -pull_down_refresh_height + distanceY;
                    ll_pull_down_refresh.setPadding(0, (int) topPadding, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断顶部轮播图是否完全显示
     * 当listview屏幕上的y坐标小于或者等于顶部轮播图在屏幕上的y坐标的时候，完全显示轮播图
     *
     * @return
     */
    private boolean isDisplayTopNewsView() {
        // 计算listview在屏幕上的坐标
        int[] location = new int[2];
        if (listViewOnScreenY == -1) {
            // this代表listview
            this.getLocationOnScreen(location);
            listViewOnScreenY = location[1];
        }

        // 计算顶部轮播图在屏幕上的坐标
        topnews_view.getLocationOnScreen(location);
        float topNewsViewOnScreenY = location[1];

        return listViewOnScreenY <= topNewsViewOnScreenY;
    }

    /**
     * 将轮播图传递进来，主要用于获得轮播图距离顶部的距离，来处理headerview的显示和隐藏
     *
     * @param topnews_view
     */
    public void addTopNewsView(View topnews_view) {
        this.topnews_view = topnews_view;
        mHeaderView.addView(topnews_view);
    }


}
