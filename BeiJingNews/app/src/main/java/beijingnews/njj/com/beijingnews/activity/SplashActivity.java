package beijingnews.njj.com.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.utils.CacheUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    public static final String START_MAIN = "start_main";
    @Bind(R.id.rl_splash_view)
    RelativeLayout rl;

    //    http://blog.csdn.net/departure_/article/details/51595286
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startAnim();
    }

    private void startAnim() {
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true); // 设置播放后停留的状态

        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(2000);
        aa.setFillAfter(true);

        AnimationSet set = new AnimationSet(false); // false 不设置速率
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.addAnimation(aa);
//        rl.setAnimation(set);
//        set.startNow();
        rl.startAnimation(set);

        set.setAnimationListener(new SplashAnimationListener());
    }

    class SplashAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent;
            if (CacheUtils.getBoolean(SplashActivity.this, START_MAIN)) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
