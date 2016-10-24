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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 要父层view把事件传递给自己（让父亲的拦截事件不生效）
        getParent().requestDisallowInterceptTouchEvent(true); // 禁用拦截
        return super.dispatchTouchEvent(ev);
    }
}
