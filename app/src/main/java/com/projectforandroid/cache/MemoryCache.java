package com.projectforandroid.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import org.json.JSONObject;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 内存缓存
 */
public class MemoryCache {
    private static final String TAG = "MemoryCache";//标签
    //弱引用，内存紧张会回收
    //private WeakHashMap<String, Bitmap> bitMapCache = new WeakHashMap<String, Bitmap>();
    private LruCache<String, Bitmap> mMemoryBitmapCache;//Bitmap缓存
    private LruCache<String, JSONObject> mMemoryJsonCache;//JSON缓存

    public MemoryCache() {

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

    /** 从缓存获取 */
    public Bitmap getBitmapFromMemoryCache(String key) {
        if (key != null) {
            return mMemoryBitmapCache.get(key) == null ? null : mMemoryBitmapCache.get(key);
        }
        return null;
    }

    public JSONObject getJsonFromMemoryCache(String key) {
        if (key != null) {
            return mMemoryJsonCache.get(key) == null ? null : mMemoryJsonCache.get(key);
        }
        return null;
    }

    /** 塞到缓存 */
    public void putBitmapToCache(String key, Bitmap value) {
        if (key != null && !"".equals(key) && value != null) {
            mMemoryBitmapCache.put(key, value);
            Log.d(TAG, "Bitmap缓存大小: " + mMemoryBitmapCache.size());
        }
    }

    public void putJsonToCache(String key, JSONObject value) {
        if (key != null && !"".equals(key) && value != null) {
            mMemoryJsonCache.put(key, value);
            Log.d(TAG, "Json缓存大小: " + mMemoryJsonCache.size());
        }
    }

    /** 缓存清空 */
    public void clear() {
        if (mMemoryBitmapCache != null && mMemoryBitmapCache.size() > 0) {
            mMemoryBitmapCache.evictAll();
        }
        if (mMemoryJsonCache != null && mMemoryJsonCache.size() > 0) {
            mMemoryJsonCache.evictAll();
        }
    }
}
