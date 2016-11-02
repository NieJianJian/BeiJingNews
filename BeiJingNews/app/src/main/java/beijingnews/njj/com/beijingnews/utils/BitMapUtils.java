package beijingnews.njj.com.beijingnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BitmapUtils {

    private NetCacheUtils mNetCacheUtils;

    public BitmapUtils(Handler handler) {
        mNetCacheUtils = new NetCacheUtils(handler);
    }

    /**
     * 从内存取->从本地取->从网络取
     * 网络请求完毕->本地保存一份->内存保存
     *
     * @param s
     * @param position
     * @return
     */
    public Bitmap getBitmapFromNet(String listImage, int position) {
        // 1.从内存取
        // 2.从本地取
        // 3.从网络请求
        if (mNetCacheUtils != null){
            mNetCacheUtils.getBitmap(listImage,position);
        }

        return null;
    }

}
