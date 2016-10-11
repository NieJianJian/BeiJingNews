package beijingnews.njj.com.beijingnews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2016/10/10.
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        // 设置标题
        tv_title.setText("新闻中心");
        ib_menu.setVisibility(View.VISIBLE);

        // 设置内容
        TextView textView = new TextView(mActivity);
        textView.setText("这是新闻中心的内容");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_child_content.addView(textView);

    }
}
