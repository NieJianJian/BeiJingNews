package beijingnews.njj.com.beijingnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import beijingnews.njj.com.beijingnews.R;
import beijingnews.njj.com.beijingnews.utils.ConstantUtils;

/**
 * Created by Administrator on 2016/10/26.
 */
public class NewsDetailActivity extends Activity {

    @ViewInject(R.id.ib_back)
    private ImageButton ib_back;
    @ViewInject(R.id.ib_textsize)
    private ImageButton ib_textsize;
    @ViewInject(R.id.ib_share)
    private ImageButton ib_share;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.webview)
    private WebView mWebView;
    @ViewInject(R.id.progressbar)
    private ProgressBar mProgressBar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        x.view().inject(this);

        tv_title.setVisibility(View.GONE);
        ib_back.setVisibility(View.VISIBLE);
        ib_textsize.setVisibility(View.VISIBLE);
        ib_share.setVisibility(View.VISIBLE);

        url = getIntent().getStringExtra("url")
                .replaceAll("http://10.0.2.2:8080/zhbj", ConstantUtils.base_url);
        Log.i("niejianjian", " -> mWebView -> " + url);
        setWebView();
    }

    private void setWebView() {
        WebSettings webSettings = mWebView.getSettings();
        // 设置支持JavaScript
        // 设置了，就可以分页加载，如果没设置，网页内容不管多少，会全部加载
        webSettings.setJavaScriptEnabled(true);
        // 设置双击变大变小的支持
        webSettings.setUseWideViewPort(true);
        // 设置支持按钮
        webSettings.setBuiltInZoomControls(true);

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("niejianjian", " -> onPageFinished -> ***********************************");
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }
}
