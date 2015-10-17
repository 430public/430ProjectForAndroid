package com.projectforandroid.widget.popup;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import com.projectforandroid.R;

/**
 * Created by 大灯泡 on 2015/9/24.
 */
public class PopupCamera extends BasePopup {
    private View v;

    public PopupCamera(Activity activity) {
        super(activity);
    }

    @Override
    public void setTitleText() {
        ((TextView)v.findViewById(R.id.popup_base_title)).setText(R.string.popup_camera_title);
    }

    @Override
    public View getView() {
        v=inflateView(R.layout.popup_base);
        return v;
    }

    @Override
    public Animation setAnima() {
        return getScaleAnimation();
    }

    @Override
    public View getCancelButton() {
        return v.findViewById(R.id.popup_cancel);
    }

    @Override
    public View getCompleteButton() {
        return null;
    }
}
