package com.projectforandroid.widget.popup;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.utils.camerautils.CameraUtils;

/**
 * Created by 大灯泡 on 2015/9/24.
 */
public class PopupCamera extends BasePopup {
    private View v;
    private Activity c;

    public PopupCamera(Activity activity) {
        super(activity);
        c=activity;
        v.findViewById(R.id.popup_choose_from_camera).setOnClickListener(this);
        v.findViewById(R.id.popup_choose_from_album).setOnClickListener(this);
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

    //------------------------------------------点击事件---------------------------------------------

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.popup_choose_from_camera:
                //从相机读取
                CameraUtils.getPhtotFromCamera(c);
                dismiss();
                break;
            case R.id.popup_choose_from_album:
                //从相册读取
                CameraUtils.getPhotoFromAlbum(c);
                dismiss();
                break;
        }

    }
}
