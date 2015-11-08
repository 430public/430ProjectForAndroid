package com.projectforandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.camerautils.CameraUtils;
import com.projectforandroid.widget.CircleImageView;
import com.projectforandroid.widget.GeneralImageView;

/**
 * Created by 杰 on 2015/10/10.
 */
public class PersonalActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static final int CLICK_BG = 23;//点击背景
    private static final int CLICK_AVATAR = 233;//点击头像
    private GeneralImageView personalWallPaper;//个人壁纸
    private ImageButton back;//返回键
    private FloatingActionButton fab;//FAB
    //昵称
    private EditText nick;//EDITTEXT
    private TextView nickCount;//计数器
    private TextView nickOverTip;//提示语
    //更换头像
    private CircleImageView mAvatar;
    //性别选择
    private RadioGroup mRadioGroup;
    //邮箱
    private EditText mailInput;
    private TextView mailTips;
    private int type;

    private ProjectApplication app;
    private BaseActivity.shareHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        app = (ProjectApplication) getApplication();
        mHandler = app.getHandler();
        initView();
        initData();
        initEvent();
    }

    /** 初始化事件 */
    private void initEvent() {
        personalWallPaper.setOnClickListener(this);
        back.setOnClickListener(this);
        fab.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    /** 初始化数据 */
    private void initData() {
        personalWallPaper.loadImage(
            (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                "background", "drawable://" + R.drawable.default_menu_bg));
        mAvatar.loadImage(
            (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                "avatar", "drawable://" + R.drawable.default_avatar));
    }

    /** 初始化控件 */
    private void initView() {
        personalWallPaper = (GeneralImageView) findViewById(R.id.personal_wallpaper);
        back = (ImageButton) findViewById(R.id.personal_back);
        fab = (FloatingActionButton) findViewById(R.id.floatbutton);
        nick = (EditText) findViewById(R.id.ed_nick);
        nickCount = (TextView) findViewById(R.id.nick_count);
        nickOverTip = (TextView) findViewById(R.id.nick_over_tip);
        mAvatar = (CircleImageView) findViewById(R.id.avatar_change);
        mRadioGroup = (RadioGroup) findViewById(R.id.sex_group);
        mailInput = (EditText) findViewById(R.id.ed_mail);
        mailTips = (TextView) findViewById(R.id.email_error_tip);

        int sexType =
            (int) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences, "sex",
                -1);
        switch (sexType) {
            case 0:
                ((RadioButton) findViewById(R.id.radio_man)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radio_half)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.radio_girl)).setChecked(true);
                break;
            default:
                break;
        }

        String nickName =
            (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences, "nick",
                "昵称");
        String mailString =
            (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences, "mail",
                "邮箱");
        nick.setHint(nickName);
        mailInput.setHint(mailString);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.personal_wallpaper:
                type = CLICK_BG;
                UIHelper.startPhotoSelectActivity(this);
                break;
            case R.id.personal_back:
                saveData();
                break;
            case R.id.floatbutton:
                break;
            case R.id.avatar_change:
                type = CLICK_AVATAR;
                UIHelper.startPhotoSelectActivity(this);
                break;
        }
    }

    /** 保存数据 */
    private void saveData() {
        String nickName = nick.getText().toString();
        String emailAddress = mailInput.getText().toString();

        DataUtils.setSharedPreferenceData(ProjectApplication.editor, "nick", nickName);
        DataUtils.setSharedPreferenceData(ProjectApplication.editor, "mail", emailAddress);
        String[] personalDetail = { nickName, emailAddress };
        Message message = new Message();
        message.obj = personalDetail;
        message.what = BaseActivity.REFRESH_UI;
        mHandler.sendMessage(message);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_man:
                DataUtils.setSharedPreferenceData(ProjectApplication.editor, "sex", 0);
                break;
            case R.id.radio_half:
                DataUtils.setSharedPreferenceData(ProjectApplication.editor, "sex", 1);
                break;
            case R.id.radio_girl:
                DataUtils.setSharedPreferenceData(ProjectApplication.editor, "sex", 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult2(requestCode, resultCode, data);
        //照片回调
        if (requestCode == CameraUtils.PHOTO_FROM_CAMERA
            || resultCode == CameraUtils.PHOTO_FROM_CAMERA
            || requestCode == CameraUtils.PHOTO_FROM_ALBUM
            || resultCode == CameraUtils.PHOTO_FROM_ALBUM) {
            Uri picUrl;
            if (data != null) {
                picUrl = data.getData();
            } else {
                picUrl = CameraUtils.photoUri;
            }
            if (picUrl != null) {
                CameraUtils.cropImg(this, picUrl);
            }
        } else if (requestCode == CameraUtils.CROP_PHOTO) {
            if (data == null) {
                UIHelper.ToastMessage(this, "错误，图片不存在！", 0);
            } else {
                //获得返回的数据
                Bundle extras = data.getExtras();
                //获得实际剪裁的区域的bitmap图形
                Bitmap pic = extras.getParcelable("data");
                //设置图片
                switch (type) {
                    case CLICK_AVATAR:
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor, "avatar",
                            "file://" + CameraUtils.getSavePhotoPath());
                        mAvatar.setImageBitmap(pic);
                        mHandler.sendEmptyMessage(BaseActivity.REFRESH_UI);
                        break;
                    case CLICK_BG:
                        DataUtils.setSharedPreferenceData(ProjectApplication.editor, "background",
                            "file://" + CameraUtils.getSavePhotoPath());
                        personalWallPaper.setImageBitmap(pic);
                        mHandler.sendEmptyMessage(BaseActivity.REFRESH_UI);
                        break;
                }
            }
        }else {

        }
        //===========================上面是回调==================================
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }
}
