package beijingnews.njj.com.beijingnews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class NewsDetailActivity extends Activity implements View.OnClickListener {

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
    private WebSettings mWebSettings;
    private int temPosition = 2; // 缓存的位置
    private int selectPosition = 2; // 选中的字号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        x.view().inject(this);

        initView();
        setWebView();
    }

    private void initView() {
        tv_title.setVisibility(View.GONE);
        ib_back.setVisibility(View.VISIBLE);
        ib_textsize.setVisibility(View.VISIBLE);
        ib_share.setVisibility(View.VISIBLE);

        ib_back.setOnClickListener(this);
        ib_textsize.setOnClickListener(this);
        ib_share.setOnClickListener(this);

    }

    private void setWebView() {
        url = getIntent().getStringExtra("url")
                .replaceAll("http://10.0.2.2:8080/zhbj", ConstantUtils.base_url);
        Log.i("niejianjian", " -> mWebView -> " + url);

        mWebSettings = mWebView.getSettings();
        // 设置支持JavaScript
        // 设置了，就可以分页加载，如果没设置，网页内容不管多少，会全部加载
        mWebSettings.setJavaScriptEnabled(true);
        // 设置双击变大变小的支持
        mWebSettings.setUseWideViewPort(true);
        // 设置支持按钮
        mWebSettings.setBuiltInZoomControls(true);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                this.finish();
                break;
            case R.id.ib_share:

                break;
            case R.id.ib_textsize:
                showChangeTextSizeDialog();
                break;
        }
    }

    private void showChangeTextSizeDialog() {
        String[] items = {"超大字体", "大号字体", "正常字体", "小号字体", "超小字体"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("设置文字大小");
        dialog.setSingleChoiceItems(items, temPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectPosition = which;
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                temPosition = selectPosition;
                changeTextSize(temPosition);
            }
        });
        dialog.setNegativeButton("取消", null);

        dialog.show();
    }

    private void changeTextSize(int temPosition) {
        switch (temPosition) {
            case 0:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 1:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2:
                mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
        }
    }
}
