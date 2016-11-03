package beijingnews.njj.com.beijingnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/11/3.
 */
public class LocalCacheUtils {

    private static final String CACHEDIR = "/mnt/sdcard/beijingnews/";

    public Bitmap getBitmap(String url) {
        String fileName = null;
        try {
            fileName = MD5Encoder.encode(url);
            File file = new File(CACHEDIR, fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        // mnt/sdcard/beijingnews/MD5-name
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHEDIR, fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
