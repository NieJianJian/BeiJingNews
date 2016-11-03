package beijingnews.njj.com.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CacheUtils {

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("niejianjian", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("niejianjian", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void putString(Context context, String key, String value) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key); // 加密得到的文件名
                File file = new File(Environment.getExternalStorageDirectory() + "/beijingnewws", fileName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(value.getBytes());
                fos.flush();
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences sp = context.getSharedPreferences("niejianjian", Context.MODE_PRIVATE);
            sp.edit().putString(key, value).commit();
        }
    }

    public static String getString(Context context, String key) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String result = "";
            try {
                String fileName = MD5Encoder.encode(key); // 加密得到的文件名
                File file = new File(Environment.getExternalStorageDirectory() + "/beijingnewws", fileName);
                if (!file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    int length = -1;
                    byte[] buff = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((length = fis.read(buff)) != -1) {
                        baos.write(buff, 0, length);

                    }
                    fis.close();
                    baos.flush();
                    baos.close();
                    result = baos.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            SharedPreferences sp = context.getSharedPreferences("niejianjian", Context.MODE_PRIVATE);
            return sp.getString(key, "");
        }
    }

}
