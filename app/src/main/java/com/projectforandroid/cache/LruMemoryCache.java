package com.projectforandroid.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import java.util.Collection;
import org.json.JSONObject;

/**
 * Created by 大灯泡 on 2015/9/21.
 */
public class LruMemoryCache implements com.nostra13.universalimageloader.cache.memory.MemoryCache {


    private static final String TAG = "LruMemoryCache";//标签
    //弱引用，内存紧张会回收
    //private WeakHashMap<String, Bitmap> bitMapCache = new WeakHashMap<String, Bitmap>();
    private LruCache<String, Bitmap> mMemoryBitmapCache;//Bitmap缓存
    private LruCache<String, JSONObject> mMemoryJsonCache;//JSON缓存

    public LruMemoryCache() {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mBitmapCacheSize = maxMemory / 3;
        int mJsonCacheSize = maxMemory / 8;
        //给LruCache分配1/3
        Log.d(TAG, "=============缓存大小：" + mBitmapCacheSize);
        mMemoryBitmapCache = new LruCache<String, Bitmap>(mBitmapCacheSize) {
            //来测量Bitmap的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        mMemoryJsonCache = new LruCache<String, JSONObject>(mJsonCacheSize) {
            //来测量json的大小
            @Override
            protected int sizeOf(String key, JSONObject value) {
                return value.length();
            }
        };
    }
    @Override
    public boolean put(String s, Bitmap bitmap) {
        return false;
    }

    @Override
    public Bitmap get(String key) {
        if (key != null) {
            return mMemoryBitmapCache.get(key) == null ? null : mMemoryBitmapCache.get(key);
        }
        return null;
    }

    @Override
    public Bitmap remove(String s) {
        return null;
    }

    @Override
    public Collection<String> keys() {
        return null;
    }

    @Override
    public void clear() {

    }
}
