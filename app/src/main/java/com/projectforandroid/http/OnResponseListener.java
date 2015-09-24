package com.projectforandroid.http;

import com.projectforandroid.model.BaseResponse;

/**
 * Created by exkulo on 9/23/2015.
 */
public interface OnResponseListener {

    public void onSuccess(BaseResponse response);

    public void onFailure(BaseResponse response);

    public void onStart();

}
