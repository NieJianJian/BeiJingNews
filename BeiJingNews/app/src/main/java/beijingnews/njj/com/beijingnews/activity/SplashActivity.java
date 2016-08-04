package beijingnews.njj.com.beijingnews.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import beijingnews.njj.com.beijingnews.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.rl_splash_view)
    RelativeLayout rl;
    @Bind(R.id.iv)
    ImageView mImageView;

    //    http://blog.csdn.net/departure_/article/details/51595286
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        mImageView.setImageResource(R.drawable.splash_bg_newyear);

    }
}
