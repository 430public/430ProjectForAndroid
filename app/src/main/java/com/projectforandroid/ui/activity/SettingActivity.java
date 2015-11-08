package com.projectforandroid.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.projectforandroid.R;
import com.projectforandroid.ui.activity.base.BaseActivity;

/**
 * Created by 杰 on 2015/10/11.
 */
public class SettingActivity extends BaseActivity {
    private RadioGroup mRadioGroup;
    private RadioButton big, middle, small;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setSize();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        big = (RadioButton) findViewById(R.id.big);
        middle = (RadioButton) findViewById(R.id.middle);
        small = (RadioButton) findViewById(R.id.small);
    }

    private void setSize() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.big) {

                } else if (checkedId == R.id.small) {

                }
            }
        });
    }
}
