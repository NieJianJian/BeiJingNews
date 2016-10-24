package beijingnews.njj.com.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jian on 2016/10/24.
 */
public class HorizontalViewPager extends ViewPager {

    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startX, startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1.记录其实位置
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 2.获得最新坐标
                float endX = ev.getX();
                float endY = ev.getY();
                // 3.计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                // 4.判断方向
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    // 水平方向滑动
                    if (getCurrentItem() == 0 && distanceX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if ((getCurrentItem() == getAdapter().getCount() - 1) && distanceX < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    // 竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;
            default:

                break;
        }
        // 要父层view把事件传递给自己（让父亲的拦截事件不生效）
//        getParent().requestDisallowInterceptTouchEvent(true); // 禁用拦截
        return super.dispatchTouchEvent(ev);
    }
}
