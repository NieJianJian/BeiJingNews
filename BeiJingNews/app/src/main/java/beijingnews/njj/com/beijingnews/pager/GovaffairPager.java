package beijingnews.njj.com.beijingnews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2016/10/10.
 * 政要指南
 */
public class GovaffairPager extends BasePager {

    public GovaffairPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        // 设置标题
        tv_title.setText("政要指南");

        // 设置内容
        TextView textView = new TextView(mActivity);
        textView.setText("这是政要指南的内容");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_child_content.addView(textView);

    }
}
