package com.projectforandroid.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.boardcast.RefreshPicturesBroadCast;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.imageloader.ImageLoaderCache;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.utils.BitmapUtils;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.camerautils.CameraUtils;
import com.projectforandroid.utils.stackutils.AppManager;
import com.projectforandroid.widget.CircleImageView;
import com.projectforandroid.widget.GeneralImageView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 基础依赖
 */
public class BaseActivity extends AppCompatActivity
    implements OnClickListener, OnNavigationItemSelectedListener, OnResponseListener {
    public static final String refreshPics = "refresh";//broadCast的intentFilter
    private static final int CLICK_BG = 23;//点击背景
    private static final int CLICK_AVATAR = 233;//点击头像

    protected DrawerLayout mDrawerMenu;//抽屉菜单
    protected NavigationView mNavigationView;//抽屉菜单下的选项

    protected TextView nick;//昵称
    protected TextView mail;//邮箱
    protected CircleImageView avatar;//头像
    protected GeneralImageView menuBackground;//背景
    private onDrawerOpenedListener mOnDrawerOpenedListener;
    private onDrawerClosedListener mOnDrawerClosedListener;

    private ActionBarDrawerToggle mActionBarDrawerToggle;//toolbar动画
    private int type;
    protected Toolbar toolbar;

    //------------------------------------------生命期-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        registerReceiver(mBroadCast, new IntentFilter(refreshPics));
    }

    private RefreshPicturesBroadCast mBroadCast = new RefreshPicturesBroadCast() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            menuBackground.loadImage(
                (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                    "background", "assets://default_menu_bg.png"));
            avatar.loadImage(
                (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                    "avatar", "assets://default_avatar.png"));
        }
    };

    @Override
    protected void onDestroy() {
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().removeActivity(this);
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadCast);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadCast);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(mBroadCast, new IntentFilter(refreshPics));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadCast, new IntentFilter(refreshPics));
    }

    //------------------------------------------初始化界面-----------------------------------------------
    public void setContentView(int layoutID) {
        super.setContentView(layoutID);

        mDrawerMenu = (DrawerLayout) findViewById(R.id.my_drawer_menu);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);
        nick = (TextView) findViewById(R.id.nick);
        mail = (TextView) findViewById(R.id.mail);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        menuBackground = (GeneralImageView) findViewById(R.id.menu_bg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (!UIHelper.isViewNull(toolbar, mDrawerMenu, mNavigationView, nick, mail, avatar,
            menuBackground)) {
            setSupportActionBar(toolbar);
            mNavigationView.setNavigationItemSelectedListener(this);
            avatar.setOnClickListener(this);
            menuBackground.setOnClickListener(this);
            menuBackground.loadImage(
                (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                    "background", "assets://default_menu_bg.png"));
            avatar.loadImage(
                (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                    "avatar", "assets://default_avatar.png"));
            //动画
            mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerMenu, toolbar, R.string.drawer_open,
                    R.string.drawer_close) {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        if (mOnDrawerOpenedListener != null) {
                            mOnDrawerOpenedListener.onDrawerOpened(drawerView);
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        if (mOnDrawerClosedListener != null) {
                            mOnDrawerClosedListener.onDrawerClosed(drawerView);
                        }
                    }
                };
            mActionBarDrawerToggle.syncState();
            mDrawerMenu.setDrawerListener(mActionBarDrawerToggle);
        }
    }

    //------------------------------------------内存紧张重写的方法-----------------------------------------------
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    //------------------------------------------事件-----------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                type = CLICK_AVATAR;
                UIHelper.startPhotoSelectActivity(this);
                break;
            case R.id.menu_bg:
                type = CLICK_BG;
                UIHelper.startPhotoSelectActivity(this);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_personal_star:
                menuItem.setCheckable(false);
                mDrawerMenu.closeDrawers();
                UIHelper.startToCollectActivity(this);
                break;
            case R.id.menu_personal_detail:
                menuItem.setCheckable(false);
                mDrawerMenu.closeDrawers();
                UIHelper.startToPersonalActivity(this);
                break;
            case R.id.menu_about:
                menuItem.setCheckable(false);
                mDrawerMenu.closeDrawers();
                UIHelper.ToastMessage(getApplicationContext(), (String) menuItem.getTitle(), 0);
                break;
            case R.id.menu_setting:
                menuItem.setCheckable(false);
                mDrawerMenu.closeDrawers();
                UIHelper.startToSettingActivity(this);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            //获得返回的数据
            Bundle extras = data.getExtras();
            //获得实际剪裁的区域的bitmap图形
            Bitmap pic = extras.getParcelable("data");
            //设置图片
            switch (type) {
                case CLICK_AVATAR:
                    DataUtils.setSharedPreferenceData(ProjectApplication.editor, "avatar",
                        "file://" + CameraUtils.getSavePhotoPath());
                    avatar.setImageBitmap(pic);
                    break;
                case CLICK_BG:
                    DataUtils.setSharedPreferenceData(ProjectApplication.editor, "background",
                        "file://" + CameraUtils.getSavePhotoPath());
                    menuBackground.setImageBitmap(pic);
                    break;
            }
        }
        //===========================上面是回调==================================
    }

    //------------------------------------------Setter--------------------------------------------

    public void setOnDrawerOpenedListener(onDrawerOpenedListener onDrawerOpenedListener) {
        mOnDrawerOpenedListener = onDrawerOpenedListener;
    }

    public void setOnDrawerClosedListener(onDrawerClosedListener onDrawerClosedListener) {
        mOnDrawerClosedListener = onDrawerClosedListener;
    }

    @Override
    public void onSuccess(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onHttpStart() {

    }

    @Override
    public void onHttpFinish() {

    }

    //------------------------------------------接口-----------------------------------------------
    public interface onDrawerOpenedListener {
        void onDrawerOpened(View drawerView);
    }

    public interface onDrawerClosedListener {
        void onDrawerClosed(View drawerView);
    }
}
