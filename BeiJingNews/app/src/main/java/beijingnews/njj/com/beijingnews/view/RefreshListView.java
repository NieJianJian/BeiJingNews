package beijingnews.njj.com.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import beijingnews.njj.com.beijingnews.R;

/**
 * Created by jian on 2016/10/25.
 */
public class RefreshListView extends ListView {

    private LinearLayout mHeaderView;
    private View ll_pull_down_refresh; // 下拉刷新控件
    private int pull_down_refresh_height, footerViewHeight;
    private float startY;
    private View topnews_view; // 顶部轮播图
    private float listViewOnScreenY = -1; // 在屏幕上listview的y轴的坐标
    private Animation upAnimation, downAnimation;
    private View mFooterView;

    private ImageView iv_red_arrow;
    private ProgressBar pb_refresh_header;
    private TextView tv_state_refresh_header;
    private TextView tv_time_refresh_header;

    public static final int PULL_DOWN_REFRESH = 1;
    public static final int RELEASE_REFRESH = 2;
    public static final int REFRESHING = 3;

    private int currentState = PULL_DOWN_REFRESH;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView(context);
        initFooterView(context);
        initAnimation(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView(context);
        initFooterView(context);
        initAnimation(context);
    }

    private void initAnimation(Context context) {
        upAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initFooterView(Context context) {
        mFooterView = View.inflate(context, R.layout.footerview, null);

        mFooterView.measure(0, 0);
        footerViewHeight = mFooterView.getMeasuredHeight();
        // footerview有10个的padding
        mFooterView.setPadding(10, -footerViewHeight + 10, 10, 10);

        this.addFooterView(mFooterView);
    }

    private void initHeaderView(Context context) {
        mHeaderView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);

        iv_red_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_red_arrow);
        pb_refresh_header = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh_header);
        tv_state_refresh_header = (TextView) mHeaderView.findViewById(R.id.tv_state_refresh_header);
        tv_time_refresh_header = (TextView) mHeaderView.findViewById(R.id.tv_time_refresh_header);

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

                if (currentState == REFRESHING) break;

                // 轮播图不完全显示，就没必要做任何处理（减少代码处理量）
                boolean isDisplayTopNewsView = isDisplayTopNewsView();
                if (!isDisplayTopNewsView) break;

                if (distanceY > 0) {
                    float topPadding = -pull_down_refresh_height + distanceY;
                    if (topPadding > 0 && currentState != RELEASE_REFRESH) {
                        currentState = RELEASE_REFRESH;
                        refreshHeaderViewState();
                    } else if (topPadding < 0 && currentState != PULL_DOWN_REFRESH) {
                        currentState = PULL_DOWN_REFRESH;
                        refreshHeaderViewState();
                    }
                    ll_pull_down_refresh.setPadding(0, (int) topPadding, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentState == PULL_DOWN_REFRESH) {
                    ll_pull_down_refresh.setPadding(0, -pull_down_refresh_height, 0, 0); // 直接隐藏
                } else if (currentState == RELEASE_REFRESH) {
                    currentState = REFRESHING;
                    refreshHeaderViewState();
                    ll_pull_down_refresh.setPadding(0, 0, 0, 0); // 完全显示

                    // 联网请求
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
            default:
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void refreshHeaderViewState() {
        switch (currentState) {
            case PULL_DOWN_REFRESH:
                tv_state_refresh_header.setText("下拉刷新");
                iv_red_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state_refresh_header.setText("释放刷新");
                iv_red_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                tv_state_refresh_header.setText("正在刷新...");
                iv_red_arrow.clearAnimation();
                iv_red_arrow.setVisibility(View.GONE);
                pb_refresh_header.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
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

    /**
     * @param isPullDownRefresh 更新成功与否
     */
    public void onRefreshFinish(boolean isPullDownRefresh) {
        ll_pull_down_refresh.setPadding(0, -pull_down_refresh_height, 0, 0);
        currentState = PULL_DOWN_REFRESH;
        iv_red_arrow.setVisibility(View.VISIBLE);
        pb_refresh_header.setVisibility(View.GONE);
        iv_red_arrow.clearAnimation();

        if (isPullDownRefresh) {
            // 更新时间
            tv_time_refresh_header.setText(getSystemTime());
        }
    }

    /**
     * 得到系统时间
     */
    public CharSequence getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public interface OnRefreshListener {
        public void onPullDownRefresh();
    }

    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

}
