package com.atguigu.testvolley;

import android.app.Application;

import com.atguigu.testvolley.volley.VolleyManager;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);
    }
}
