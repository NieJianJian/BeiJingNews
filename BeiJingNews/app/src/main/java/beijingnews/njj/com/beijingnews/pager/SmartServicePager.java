package beijingnews.njj.com.beijingnews.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import beijingnews.njj.com.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2016/10/10.
 * 智慧服务
 */
public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        // 设置标题
        tv_title.setText("智慧服务");

        // 设置内容
        TextView textView = new TextView(mActivity);
        textView.setText("这是智慧服务的内容");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_child_content.addView(textView);

    }
}
