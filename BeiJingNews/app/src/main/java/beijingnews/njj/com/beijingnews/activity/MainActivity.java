package beijingnews.njj.com.beijingnews.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.utils.DensityUtil;


/**
 * 1.导入SlidingMenu库
 * 2.工程关联SlidingMenu库
 * 3.把Activity继承SlidingFragmentActivity
 * 4.把onCreate改成public修饰
 * 5.设置SlideMenu细节
 * 5.1 设置左侧页面
 * 5.2 设置右侧页面
 * 5.3 设置主页面
 * 5.4 设置模式：主页面 + 左侧页面 ; 主页面 + 右侧页面 ; 左侧页面 + 主页面 + 右侧页面
 * 5.5 设置滑动模式：全屏，边缘，不可滑动
 * 5.6 设置页面占比宽度
 */
public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置主页面
        setContentView(R.layout.activity_main);
        // 设置左侧页面
        setBehindContentView(R.layout.left_menu);
        // 得到SlidingMenu
        SlidingMenu slidingMenu = getSlidingMenu();
        // 设置右侧菜单
//        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        // 设置模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置滑动模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置页面占比宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this, 200));

    }
}
