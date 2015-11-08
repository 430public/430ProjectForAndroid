package com.projectforandroid.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.internal.widget.ViewUtils;
import android.widget.RadioGroup;

import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.data.SharedPrefConstances;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.viewutils.L;

/**
 * Created by 杰 on 2015/10/11.
 */
public class SettingActivity extends BaseActivity {

    RadioGroup mTextSizeGroups;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mTextSizeGroups = (RadioGroup) findViewById(R.id.activity_setting_with_drawer_rg);
        String textSize = (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences, SharedPrefConstances.SHARE_ATTR_TEXT_SIZE, SharedPrefConstances.TEXT_SIZE_NORMAL);
        L.i(textSize);
        //首先是初始化选择组。。。
        if(textSize.equals(SharedPrefConstances.TEXT_SIZE_NORMAL)) {
            mTextSizeGroups.check(R.id.activity_setting_with_drawer_middle);
        } else if(textSize.equals(SharedPrefConstances.TEXT_SIZE_BIG)) {
            mTextSizeGroups.check(R.id.activity_setting_with_drawer_big);
        } else if(textSize.equals(SharedPrefConstances.TEXT_SIZE_SMALL)) {
            mTextSizeGroups.check(R.id.activity_setting_with_drawer_small);
        }

        mTextSizeGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_setting_with_drawer_big:
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor, SharedPrefConstances.SHARE_ATTR_TEXT_SIZE, SharedPrefConstances.TEXT_SIZE_BIG);
                        break;
                    case R.id.activity_setting_with_drawer_middle:
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor, SharedPrefConstances.SHARE_ATTR_TEXT_SIZE, SharedPrefConstances.TEXT_SIZE_NORMAL);
                        break;
                    case R.id.activity_setting_with_drawer_small:
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor, SharedPrefConstances.SHARE_ATTR_TEXT_SIZE, SharedPrefConstances.TEXT_SIZE_SMALL);
                        break;
                }
                UIHelper.ToastMessage(SettingActivity.this, getString(R.string.setting_toast_restart), -1);
            }
        });
    }
}
