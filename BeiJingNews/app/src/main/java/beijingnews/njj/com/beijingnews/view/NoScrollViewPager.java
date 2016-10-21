package beijingnews.njj.com.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/10/11.
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 直接返回true，就拦截了事件，但是什么也不做，就不会有滑动事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
//        return super.onTouchEvent(ev);
    }

    /**
     * 返回false，表示当前不拦截，传递到下一层，也就是子view去处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 无法控制slidemenu，就取消掉slidemenu的滑动，当滑动到第一个viewpager页面的时候，再出现slidemenu
     */

}
