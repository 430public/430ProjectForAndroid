package com.projectforandroid.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.model.BaseResponse;
import com.projectforandroid.model.Channel;
import com.projectforandroid.model.NewsDetail;
import com.projectforandroid.model.NewsDigest;
import com.projectforandroid.model.NewsList;
import com.show.api.ShowApiRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by exkulo on 9/24/2015.
 */
public class GetNewsDetail {

    private static final String REQUEST_URL = "http://route.showapi.com/644-1";

    private OnResponseListener mOnResponseListener;

    public GetNewsDetail() {
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.mOnResponseListener = listener;
    }

    public void getNewsDetail(String url) {
        if (mOnResponseListener == null) {
            throw new IllegalStateException("还没有设置OnResponseListener！");
        }
        mOnResponseListener.onStart();
        final AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                Throwable e) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setStatus(-1);
                baseResponse.setErrorCode(statusCode);
                baseResponse.setErrorMsg("发生了什么我也想不到的事情→_→");
                mOnResponseListener.onFailure(baseResponse);
                e.printStackTrace();
            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    BaseResponse baseResponse = new BaseResponse();
                    String json = new String(responseBody, "utf-8");
                    JSONObject object = new JSONObject(json);
                    baseResponse.setErrorCode(object.getInt("showapi_res_code"));
                    baseResponse.setErrorMsg(object.getString("showapi_res_error"));
                    if (object.getInt("showapi_res_code") != 0) {
                        baseResponse.setStatus(-1);
                        mOnResponseListener.onFailure(baseResponse);
                        return;
                    }
                    String body =
                        object.getJSONObject("showapi_res_body").toString();
                    NewsDetail detail = new Gson().fromJson(body, NewsDetail.class);
                    if(detail == null) {
                        baseResponse.setStatus(-1);
                        baseResponse.setErrorCode(-2);
                        baseResponse.setErrorMsg("网络正常，但是查无此内容");
                        mOnResponseListener.onFailure(baseResponse);
                        return;
                    }
                    baseResponse.setData(detail);
                    baseResponse.setStatus(1);
                    mOnResponseListener.onSuccess(baseResponse);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ShowApiRequest request = new ShowApiRequest(REQUEST_URL, DataConstance.appId,
            DataConstance.appSecret);
        request.setResponseHandler(resHandler)
               .addTextPara("url", url)
               .post();
    }

}
