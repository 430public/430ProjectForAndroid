package com.projectforandroid.http;

import com.projectforandroid.http.respon.BaseResponse;

/**
 * Created by exkulo on 9/23/2015.
 */
public interface OnResponseListener {

    void onSuccess(BaseResponse response);

    void onFailure(BaseResponse response);

    void onHttpStart();

    void onHttpFinish();
}
