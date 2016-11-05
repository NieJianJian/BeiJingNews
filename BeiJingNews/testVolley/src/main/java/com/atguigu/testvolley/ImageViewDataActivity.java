package com.atguigu.testvolley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

/**
 * Volley请求图片
 * @author Administrator
 * 没有任何封装的方式
 *
 */
public class ImageViewDataActivity extends Activity {

	protected static final String TAG = ImageViewDataActivity.class
			.getSimpleName();
	private ImageView iv_result;
	private LruCache<String, Bitmap> mImageCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageviewdata);
		iv_result = (ImageView) findViewById(R.id.iv_result);
		mImageCache = new LruCache<String, Bitmap>(
				20);

	}

	public void getImageViewDataFromVolley(View view) {

		RequestQueue mRequestQueue = Volley.newRequestQueue(this);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				mImageCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return mImageCache.get(key);
			}
		};
		ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCache);
		// imageView是一个ImageView实例
		// ImageLoader.getImageListener的第二个参数是默认的图片resource id
		// 第三个参数是请求失败时候的资源id，可以指定为0
		ImageListener listener = ImageLoader
				.getImageListener(iv_result, android.R.drawable.ic_menu_rotate,
						android.R.drawable.ic_delete);
		mImageLoader
				.get("http://a.hiphotos.baidu.com/album/h%3D800%3Bcrop%3D0%2C0%2C1280%2C800/sign=5f024b518326cffc762ab2b2893a29e2/72f082025aafa40fa3bcf315aa64034f79f019fb.jpg",
						listener);
	}

}
