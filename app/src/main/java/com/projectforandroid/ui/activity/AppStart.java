package com.projectforandroid.ui.activity;

import android.os.Bundle;
import android.util.Log;
import com.projectforandroid.R;
import com.projectforandroid.http.request.InitRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.base.BaseActivity;

/**
 * Created by 大灯泡 on 2015/9/21.
 * 首页，这里应该是用作欢迎页，然后跳到首页
 */
public class AppStart extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        InitRequest request=new InitRequest(this);
        request.setOnResponseListener(this);
        request.execute();
        UIHelper.startToIndexActivity(this);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
    }
}
