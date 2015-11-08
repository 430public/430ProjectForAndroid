package com.projectforandroid.ui;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import com.projectforandroid.widget.popup.BasePopup;

/**
 * Created by 大灯泡 on 2015/9/24.
 */
public class popup extends BasePopup{

    public popup(Activity activity) {
        super(activity);
    }

    @Override
    public void setTitleText() {

    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public Animation setAnima() {
        return null;
    }

    @Override
    public View getCancelButton() {
        return null;
    }

    @Override
    public View getCompleteButton() {
        return null;
    }


}
