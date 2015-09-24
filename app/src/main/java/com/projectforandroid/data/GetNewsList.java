package com.projectforandroid.data;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.model.BaseResponse;
import com.projectforandroid.model.NewsList;
import com.show.api.ShowApiRequest;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by exkulo on 9/23/2015.
 */
public class GetNewsList {

    private static final String REQUEST_URL = "http://route.showapi.com/109-35";

    OnResponseListener mOnResponseListener;

    public GetNewsList() {
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.mOnResponseListener = listener;
    }

    /**
     * 获取新闻列表，频道id或者名称可以为null，但不可以两者都是null的。如果两个都不为null，那么会以id为准。
     * @param channelId 频道id（精确）
     * @param channelName 频道名称（模糊）
     * @param page 页码
     */
    public void getNewsList(String channelId, String channelName, int page) {
        if (mOnResponseListener == null) {
            throw new IllegalStateException(ProjectApplication.getResString(R.string.respon_error));
        }
        mOnResponseListener.onStart();
        final AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                Throwable e) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setStatus(-1);
                baseResponse.setErrorCode(statusCode);
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
                        object.getJSONObject("showapi_res_body").get("pagebean").toString();
                    NewsList list = new Gson().fromJson(body, NewsList.class);
                    if(list.getContentlist().isEmpty()) {
                        baseResponse.setStatus(-1);
                        baseResponse.setErrorCode(-2);
                        baseResponse.setErrorMsg("网络正常，但是查无此内容");
                        mOnResponseListener.onFailure(baseResponse);
                        return;
                    }
                    baseResponse.setData(list);
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
               .addTextPara("page", page + "");
        if(channelId == null && channelName == null)
            throw new IllegalStateException(ProjectApplication.getResString(R.string.respon_give_id));
        else if(channelId != null){
            request.addTextPara("channelId", channelId);
        } else {
            request.addTextPara("channelName", channelName);
        }
        request.post();
    }
}
