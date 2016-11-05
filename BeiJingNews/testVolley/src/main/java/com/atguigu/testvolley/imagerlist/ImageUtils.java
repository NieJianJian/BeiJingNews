package com.atguigu.testvolley.imagerlist;

import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.atguigu.testvolley.R;
import com.atguigu.testvolley.volley.VolleyManager;

/**
 * Created by Administrator on 2016/1/6.
 * 真正请求
 */
public class ImageUtils {
    public static void loadImage(final String url, final ListView listView) {
        ImageLoader imageLoader = VolleyManager.getImageLoader();
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            ImageView imageView = (ImageView) listView.findViewWithTag(url);
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if(imageContainer != null){
                    ImageView imageView = (ImageView) listView.findViewWithTag(url);
                    if(imageView != null){
                        if(imageContainer.getBitmap() != null){
                            imageView.setImageBitmap(imageContainer.getBitmap());
                        }else{
                            imageView.setImageResource(R.drawable.empty_photo);
                        }
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //如果出错，则说明都不显示（简单处理），最好准备一张出错图片
                imageView.setImageResource(R.drawable.empty_photo);
            }
        };
        imageLoader.get(url,listener);

    }

    /**
     * 取消图片请求
     */
    public static void cancelAllImageRequests() {
        ImageLoader imageLoader = VolleyManager.getImageLoader();
        RequestQueue requestQueue = VolleyManager.getRequestQueue();
        if (imageLoader != null && requestQueue != null) {
            int num = requestQueue.getSequenceNumber();
//            imageLoader.drain(num);
//            requestQueue.stop();
        }
    }



}
