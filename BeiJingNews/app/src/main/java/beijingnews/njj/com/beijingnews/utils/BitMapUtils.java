package beijingnews.njj.com.beijingnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BitMapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public BitMapUtils(Handler handler) {
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
        mNetCacheUtils = new NetCacheUtils(handler, mLocalCacheUtils,mMemoryCacheUtils);
    }

    /**
     * 从内存取->从本地取->从网络取
     * 网络请求完毕->本地保存一份->内存保存
     *
     * @param listImage
     * @param position
     * @return
     */
    public Bitmap getBitmapFromNet(String listImage, int position) {
        // 1.从内存取
        if (mMemoryCacheUtils != null) {
            Bitmap bitmap = mMemoryCacheUtils.getBitmap(listImage);
            if (bitmap != null) {
                return bitmap;
            }
        }
        // 2.从本地取
        if (mLocalCacheUtils != null) {
            Bitmap bitmap = mLocalCacheUtils.getBitmap(listImage);
            if (bitmap != null) {
                return bitmap;
            }
        }
        // 3.从网络请求
        if (mNetCacheUtils != null) {
            mNetCacheUtils.getBitmap(listImage, position);
        }

        return null;
    }

}
