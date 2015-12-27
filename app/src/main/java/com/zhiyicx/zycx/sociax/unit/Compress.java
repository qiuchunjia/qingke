package com.zhiyicx.zycx.sociax.unit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 图片压缩
 */
public class Compress {

    private static final String TAG = "Compress";

    public static InputStream compressPic(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 返回bm为空
        // 获取这个图片的宽和高
        BitmapFactory.decodeFile(file.getPath(), options);// 此时返回bm为空
        Bitmap localBitmap = null;
        for (int i = 0; ; i++) {
            if ((options.outWidth >> i > 1024)
                    || (options.outHeight >> i > 1024))
                continue;
            int j = i;
            options.inSampleSize = (int) Math.pow(2.0D, j);
            options.inJustDecodeBounds = false;
            localBitmap = BitmapFactory.decodeFile(file.getPath(), options);

            if (localBitmap != null)
                break;
            Log.e(TAG, "Bitmap decode error!");
        }
        /*
		 * int be = (int)(options.outHeight / (float)200); if(be <= 0) be =1;
		 * options.inSampleSize =be; options.inJustDecodeBounds = false;
		 * localBitmap = BitmapFactory.decodeFile(file.getPath(),options); if
		 * (localBitmap != null) break; Log.e(TAG,"Bitmap decode error!");
		 */

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        localBitmap.recycle();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static InputStream compressPic(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bitmap.recycle();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static Bitmap compressPicToBitmap(File file) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 返回bm为空
        // 获取这个图片的宽和高
        BitmapFactory.decodeFile(file.getPath(), options);// 此时返回bm为空
        Bitmap localBitmap = null;
        for (int i = 0; ; i++) {
            if ((options.outWidth >> i > 1024)
                    || (options.outHeight >> i > 1024))
                continue;
            int j = i;
            options.inSampleSize = (int) Math.pow(2.0D, j);
            options.inJustDecodeBounds = false;
            localBitmap = BitmapFactory.decodeFile(file.getPath(), options);
            if (localBitmap != null)
                break;
            Log.e(TAG, "Bitmap decode error!");
            throw new Exception("load image error"); // decode error
        }
        return localBitmap;
    }
}