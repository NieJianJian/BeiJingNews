package beijingnews.njj.com.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.utils.CacheUtils;
import beijingnews.njj.com.beijingnews.utils.DensityUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.guide_viewPager)
    ViewPager mViewPager;
    @Bind(R.id.guide_btn_start)
    Button mStartBtn;
    @Bind(R.id.guide_rl_indicator)
    LinearLayout mIndicatorLayout;
    @Bind(R.id.red_point)
    ImageView mRedPoint;

    private ArrayList<ImageView> images;
    private int pointMargin;
    private int widthDpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        images = new ArrayList<ImageView>();
        widthDpi = DensityUtil.dip2px(this, 10);
        int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            images.add(imageView);

            ImageView normal_point = new ImageView(this);
            normal_point.setBackgroundResource(R.drawable.normal_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi, widthDpi);
            if (i != 0) {
                params.leftMargin = widthDpi;
            }
            normal_point.setLayoutParams(params);

            mIndicatorLayout.addView(normal_point);
        }
        mViewPager.setAdapter(new GuidePagerAdapter());
        mRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存软件参数，标识进入过引导界面，之后跳过直接到主页面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            float maxLeft = positionOffsetPixels * pointMargin;
            float marginLefts = (position + positionOffset) * pointMargin;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthDpi, widthDpi);
            params.leftMargin = (int) marginLefts;
            mRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == images.size() - 1) {
                mStartBtn.setVisibility(View.VISIBLE);
            } else {
                mStartBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            mRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            pointMargin = mIndicatorLayout.getChildAt(1).getLeft() - mIndicatorLayout.getChildAt(0).getLeft();
        }
    }

    class GuidePagerAdapter extends PagerAdapter {

        /**
         * 返回我们的总条数
         */
        @Override
        public int getCount() {
            return images.size();
        }

        /**
         * 实例化具体的某个页面
         *
         * @param container ViewPgaer(extend ViewGroup)
         * @param position  页面的下标index
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = images.get(position);
            container.addView(imageView);
            return imageView;
//            return position;
        }

        /**
         * @param view   当前页面的视图view
         * @param object 是instantiateItem()方法的返回值
         * @return 上个方法实例化返回的页面，和当前页面是不是同一个页面
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
//            return view == images.get(Integer.valueOf(object.toString()));
        }

        /**
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
