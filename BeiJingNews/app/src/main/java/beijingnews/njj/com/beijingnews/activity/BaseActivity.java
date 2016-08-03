package beijingnews.njj.com.beijingnews.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/8/4.
 */
public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
