package beijingnews.njj.com.beijingnews.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import beijingnews.njj.com.beijingnews.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

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
            Toast.makeText(getApplication(),"动画播放完成",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
