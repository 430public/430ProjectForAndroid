package com.projectforandroid.http.request;

import android.content.Context;
import android.util.Log;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.data.HotNewsIndexBean;
import com.projectforandroid.http.BaseHttpRequest;
import com.projectforandroid.http.respon.BaseResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 大灯泡 on 2015/10/11.
 * 请求新闻的索引
 */
public class InitRequest extends BaseHttpRequest {

    public InitRequest(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return String.format("http://apis.haoservice.com/lifeservice/news/words?key=%s",
            ProjectApplication.getHotNewsKey());
    }

    @Override
    public void getResponseData(BaseResponse response, JSONObject json) throws JSONException {
        if (response.getStatus() == 0) {
            JSONArray array = json.optJSONArray("result");
            if (array != null && array.length() > 0) {
                HotNewsIndexBean bean = new HotNewsIndexBean();
                List<HotNewsIndexBean.IndexBean> beans =
                    new ArrayList<HotNewsIndexBean.IndexBean>(array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    HotNewsIndexBean.IndexBean resultBean = new HotNewsIndexBean.IndexBean();
                    resultBean.id = object.optInt("Id");
                    resultBean.indexKey = object.optString("KeyWord");
                    beans.add(resultBean);
                }
                bean.setIndexList(beans);
                response.setData(bean);
            }
        }
    }
}
