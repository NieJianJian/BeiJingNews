package beijingnews.njj.com.beijingnews.utils;

import android.graphics.Bitmap;

import org.xutils.cache.LruCache;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MemoryCacheUtils {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCacheUtils() {
        int maxSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);

        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
//                return super.sizeOf(key, value);
//                return value.getByteCount(); // 低版本有问题
                return (value.getRowBytes() * value.getHeight()) / 1024;
            }
        };
    }

    public Bitmap getBitmap(String listImage) {
        return mLruCache.get(listImage);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
    }
}
