package beijingnews.njj.com.beijingnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/2.
 */
public class NetCacheUtils {

    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    private final Handler mHandler;

    public NetCacheUtils(Handler handler) {
        this.mHandler = handler;
    }

    public void getBitmap(String listImage, int position) {
        new Thread(new MyRunnable(listImage, position)).start();
    }

    class MyRunnable implements Runnable {

        private final String url;
        private final int position;

        public MyRunnable(String url, int position) {
            this.url = url;
            this.position = position;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.connect();
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    // 发消息更新
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    msg.obj = bitmap;
                    msg.arg1 = position;
                    mHandler.sendMessage(msg);

                    // 保存一份在内存汇总
                    // 保存一份到本地中
                }

            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = FAIL;
                mHandler.sendMessage(msg);
            }
        }
    }

}
