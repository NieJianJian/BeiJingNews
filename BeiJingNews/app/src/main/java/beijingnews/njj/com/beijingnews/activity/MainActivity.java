package beijingnews.njj.com.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.fragment.LeftMenuFragment;
import beijingnews.njj.com.beijingnews.fragment.MainFragment;
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

    public static final String LEFTMENU_TAG = "leftmenu_tag";
    public static final String Main_TAG = "main_tag";
    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        fm = getSupportFragmentManager();
        initFragment();

    }

    private void initFragment() {
        // 开启事务
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_leftmenu, new LeftMenuFragment(), LEFTMENU_TAG);
        ft.replace(R.id.fl_main, new MainFragment(), Main_TAG);
        // 事务的提交
        ft.commit();
    }

    /**
     * 得到左侧Framgnet实例
     */
    public LeftMenuFragment getLeftMenuFragment() {
        LeftMenuFragment leftMenuFragment =
                (LeftMenuFragment) fm.findFragmentByTag(LEFTMENU_TAG);
        return leftMenuFragment;
    }

    /**
     * 得到右侧fragment实例
     */
    public MainFragment getMainFragment() {
        MainFragment mainFragment = (MainFragment) fm.findFragmentByTag(Main_TAG);
        return mainFragment;
    }

}
