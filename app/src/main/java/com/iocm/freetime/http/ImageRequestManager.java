package com.iocm.freetime.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by liubo on 15/7/23.
 */
public class ImageRequestManager {
    private HttpRequestManager mHttpRequestManager;
    private static ImageRequestManager sImageRequestManager;
    public static ImageRequestManager getInstance(Context context) {
        if (sImageRequestManager == null)
            sImageRequestManager = new ImageRequestManager(context);
        return sImageRequestManager;
    }

    private ImageRequestManager(Context context) {
        mHttpRequestManager = HttpRequestManager.getInstance(context);
    }

    private static class LruImageCache implements ImageLoader.ImageCache {

        private static LruCache<String, Bitmap> mLruCache;
        private static LruImageCache sLruImageCache;
        private LruImageCache() {
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int maxCache = maxMemory / 8;
            mLruCache = new LruCache<String, Bitmap>(maxCache) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight() * value.getWidth();
                }
            };
        }

        private static LruImageCache getInstance() {
            if (sLruImageCache == null)
                sLruImageCache = new LruImageCache();
            return sLruImageCache;
        }
        @Override
        public Bitmap getBitmap(String s) {
            return mLruCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            if (mLruCache.get(s) == null) {
                mLruCache.put(s, bitmap);
            } else {
                return;
            }
        }
    }
}
