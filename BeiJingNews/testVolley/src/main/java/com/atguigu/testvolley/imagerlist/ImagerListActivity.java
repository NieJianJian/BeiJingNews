package com.atguigu.testvolley.imagerlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.atguigu.testvolley.R;

/**
 * Created by Administrator on 2016/1/6.
 * Volley对图片的请求做了处理
 *缓存在sdcard/abc目录下
 * 开发者以后可以完全使用Volley请求网络图片和网络文本信息了
 *
 */
public class ImagerListActivity extends Activity {
    private static final String TAG = ImagerListActivity.class.getSimpleName();
    private ListView listview;
    private  ImagerListAdapter imagerListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        VolleyManager.init(this);
        setContentView(R.layout.actvity_imager_list);
        listview = (ListView) findViewById(R.id.listview);
        //假设这是联网请求的图片
        String[] imageThumbUrls = Images.imageThumbUrls;
        imagerListAdapter = new ImagerListAdapter(this,imageThumbUrls,listview);
        listview.setAdapter(imagerListAdapter);
    }





}
