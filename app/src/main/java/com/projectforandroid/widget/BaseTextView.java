package com.projectforandroid.widget;

import android.content.Context;
import android.support.v7.internal.widget.ViewUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.projectforandroid.ProjectApplication;
import com.projectforandroid.data.SharedPrefConstances;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.viewutils.L;
import com.projectforandroid.utils.viewutils.MakeUnitUtils;

/**
 * Created by Exkulo on 2015/11/7.
 */
public class BaseTextView extends TextView {

    private float mProjectTextSize;
    private float mOrignalTextSize;

    public BaseTextView(Context context) {
        this(context, null);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOrignalTextSize = MakeUnitUtils.px2sp(context, getTextSize());
        mProjectTextSize = mOrignalTextSize;

        L.i("mProjectTextSize=" + mProjectTextSize);
        L.i("mOrignaltextSize=" + mOrignalTextSize);
        notifyTextSizeConfChanged();
    }

    /**
     * 改变字体大小设置的时候调用
     */
    public void notifyTextSizeConfChanged() {
        String sizeConf = (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences, SharedPrefConstances.SHARE_ATTR_TEXT_SIZE, SharedPrefConstances.TEXT_SIZE_NORMAL);
        if (sizeConf.equals(SharedPrefConstances.TEXT_SIZE_SMALL)) {
            mProjectTextSize = mOrignalTextSize - 2;
        } else if (sizeConf.equals(SharedPrefConstances.TEXT_SIZE_NORMAL)) {
            mProjectTextSize = mOrignalTextSize;
        } else if (sizeConf.equals(SharedPrefConstances.TEXT_SIZE_BIG)) {
            mProjectTextSize = mOrignalTextSize + 2;
        }
        setTextSize(mProjectTextSize);

    }

}
