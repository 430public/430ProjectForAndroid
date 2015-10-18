package com.projectforandroid.http.request;

import android.content.Context;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.data.EntertainmentBean;
import com.projectforandroid.data.SportNewsBean;
import com.projectforandroid.http.BaseHttpRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.MD5Tools;
import com.projectforandroid.utils.dateutils.DateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/10/15.
 */
public class SportNewsRequest extends BaseHttpRequest {
    private int num;
    private int page;
    private String id;
    private String timestamp;
    private String sign;

    public SportNewsRequest(Context context, int num, int page, String id, String timestamp,
        String sign) {
        mContext = context;
        this.num = num;
        this.page = page;
        this.id = id;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    @Override
    public String getUrl() {
        return String.format(Locale.getDefault(),
            "%s?num=%d&page=%d&showapi_appid=%s&showapi_timestamp=%s&showapi_sign=%s",
            ProjectApplication.getSportNewsBaseUrl(), num, page, id, timestamp, sign);
    }

    @Override
    public String getKey() {
        return getUrl();
    }

    @Override
    public void getResponseData(BaseResponse response, JSONObject json) throws JSONException {
        if (response.getStatus() != 0) {
            JSONObject object = json.optJSONObject("showapi_res_body");
            if (object != null && object.length() > 0) {
                SportNewsBean sbean = new SportNewsBean();
                List<SportNewsBean.SportNewsBeanResult> slist = new ArrayList<>(object.length());
                for (int i = 0; i < object.length() - 2; i++) {
                    JSONObject obj = object.optJSONObject("" + i);
                    SportNewsBean.SportNewsBeanResult result =
                        new SportNewsBean.SportNewsBeanResult();
                    result.description = obj.optString("description");
                    result.picUrl = obj.optString("picUrl");
                    result.title = obj.optString("title");
                    result.url = obj.optString("url");
                    result.time = obj.optString("time");
                    slist.add(result);
                }
                sbean.setSportNewsBeans(slist);
                response.setData(sbean);
            }
        }
    }

    public Object LoadCache() {
        try {
            JSONObject object;
            object = mDiskCache.getJsonCache(
                (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                    MD5Tools.hashKey(getKey()), MD5Tools.hashKey(getKey())));
            if (object != null && mBaseResponse != null) {
                mBaseResponse.setStatus(0);
                getResponseData(mBaseResponse, object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mBaseResponse.getData() != null) {
            return mBaseResponse.getData();
        } else {
            return null;
        }
    }
}


