package beijingnews.njj.com.beijingnews;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BeiJingNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }
}
