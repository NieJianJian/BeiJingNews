package beijingnews.njj.com.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import beijingnews.njj.com.beijingnews.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        images = new ArrayList<ImageView>();
        int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            images.add(imageView);

            ImageView normal_point = new ImageView(this);
            normal_point.setBackgroundResource(R.drawable.normal_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                params.leftMargin = 20;
            }
            normal_point.setLayoutParams(params);

            mIndicatorLayout.addView(normal_point);
        }
        mViewPager.setAdapter(new GuidePagerAdapter());
        mRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i("niejianjian", "position = " + position
                    + "; positionOffset = " + positionOffset
                    + "; positionOffsetPixels = " + positionOffsetPixels);
//            float maxLeft = positionOffsetPixels * pointMargin;
            float marginLefts = (position + positionOffset) * pointMargin;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(20, 20);
            params.leftMargin = (int) marginLefts;
            mRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {

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

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = images.get(position);
            container.addView(imageView);
//            if (position == 2) {
//                mStartBtn.setVisibility(View.VISIBLE);
//            } else {
//                mStartBtn.setVisibility(View.GONE);
//            }
            return imageView;
//            return position;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
//            return view == images.get(Integer.valueOf(object.toString()));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
