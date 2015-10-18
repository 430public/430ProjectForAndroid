package com.projectforandroid.http;

import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.cache.DiskCache;
import com.projectforandroid.cache.MemoryCache;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.MD5Tools;
import com.projectforandroid.utils.stringutils.StringUtils;
import java.util.Date;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 大灯泡 on 2015/10/11.
 * 对asynchttp的再封装
 */
public abstract class BaseHttpRequest extends AsyncHttpClient {
    private static final String TAG = "BaseHttpClient";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public Context mContext;
    private OnResponseListener mOnResponseListener;
    public BaseResponse mBaseResponse;
    private Object data;
    public static DiskCache mDiskCache;
    private int requestType;
    public static MemoryCache memoryCache;

    public BaseHttpRequest(Context context) {
        mContext = context;
        mDiskCache = new DiskCache(context);
        client.setTimeout(10000);
        memoryCache=MemoryCache.getInstance();
        mBaseResponse=new BaseResponse();
    }

    public BaseHttpRequest() {
        mDiskCache = new DiskCache(mContext);
        memoryCache=MemoryCache.getInstance();
        mBaseResponse=new BaseResponse();
    }

    //------------------------------------------抽象方法-----------------------------------------------

    /** 用于拼接url */
    public abstract String getUrl();

    public abstract String getKey();

    /** 实现get的方法 */
    public abstract void getResponseData(BaseResponse response, JSONObject json)
        throws JSONException;

    //------------------------------------------公用方法-----------------------------------------------

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.mOnResponseListener = listener;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public void execute() {
        if (StringUtils.isEmpty(getUrl())) {
            throw new IllegalStateException("还没输入网址信息噢");
        } else {
            client.get(getUrl(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (mOnResponseListener != null) {
                        mBaseResponse.setStatus(statusCode);
                        mBaseResponse.setRequestType(requestType);
                        memoryCache.putJsonToCache(MD5Tools.hashKey(getKey()), response);
                        mDiskCache.putJson(MD5Tools.hashKey(getKey()), response);
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor,
                            MD5Tools.hashKey(getKey()),  MD5Tools.hashKey(getKey()));
                        try {
                            getResponseData(mBaseResponse, response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "糟糕，app遭遇不明原因崩溃了");
                        }

                        mOnResponseListener.onSuccess(mBaseResponse);
                        Log.d(">>>>>>>>>>>>>>>请求成功<<<<<<<<<<<<<<", response.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                    JSONObject errorResponse) {
                    if (mOnResponseListener != null) {
                        mBaseResponse.setRequestType(requestType);
                        mBaseResponse.setStatus(statusCode);
                        try {
                            getResponseData(mBaseResponse, errorResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "糟糕，app遭遇不明原因崩溃了");
                        }
                        mOnResponseListener.onFailure(mBaseResponse);
                        Log.d(">>>>>>>>>>>>>>>请求失败<<<<<<<<<<<<<<", errorResponse.toString());
                    }
                }

                @Override
                public void onStart() {
                    if (mOnResponseListener != null) mOnResponseListener.onHttpStart();
                }

                @Override
                public void onFinish() {
                    if (mOnResponseListener != null) mOnResponseListener.onHttpFinish();
                }
            });
        }
    }
}
