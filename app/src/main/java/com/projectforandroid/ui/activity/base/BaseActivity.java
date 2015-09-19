package com.projectforandroid.ui.activity.base;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.utils.picUtils;
import com.projectforandroid.utils.stackutils.AppManager;
import com.projectforandroid.widget.CircleImageView;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 基础依赖
 */
public class BaseActivity extends AppCompatActivity
    implements OnClickListener, OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerMenu;//抽屉菜单
    protected NavigationView mNavigationView;//抽屉菜单下的选项

    protected TextView nick;//昵称
    protected TextView mail;//邮箱
    protected CircleImageView avatar;//头像
    protected ImageView menuBackground;//背景

    protected Toolbar toolbar;

    //------------------------------------------生命期-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }

    //------------------------------------------初始化界面-----------------------------------------------
    public void setContentView(int layoutID) {
        super.setContentView(layoutID);

        mDrawerMenu = (DrawerLayout) findViewById(R.id.my_drawer_menu);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);
        nick = (TextView) findViewById(R.id.nick);
        mail = (TextView) findViewById(R.id.mail);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        menuBackground = (ImageView) findViewById(R.id.menu_bg);
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        if (!UIHelper.isViewNull(toolbar,mDrawerMenu,mNavigationView,nick,mail,avatar,menuBackground)) {
            setSupportActionBar(toolbar);
            mNavigationView.setNavigationItemSelectedListener(this);
            menuBackground.setImageDrawable(picUtils.background(R.drawable.default_menu_bg));
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

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_personal_star:
                // TODO: 2015/9/19 跳到个人收藏
                menuItem.setChecked(false);
                mDrawerMenu.closeDrawers();
                UIHelper.ToastMessage(getApplicationContext(), (String) menuItem.getTitle(), 0);
                break;
            case R.id.menu_personal_detail:
                // TODO: 2015/9/19 跳到个人资料
                menuItem.setChecked(false);
                mDrawerMenu.closeDrawers();
                UIHelper.ToastMessage(getApplicationContext(), (String) menuItem.getTitle(), 0);
                break;
            case R.id.menu_about:
                // TODO: 2015/9/19 跳到关于
                menuItem.setChecked(false);
                mDrawerMenu.closeDrawers();
                UIHelper.ToastMessage(getApplicationContext(), (String) menuItem.getTitle(), 0);
                break;
            case R.id.menu_setting:
                // TODO: 2015/9/19 跳到设置
                menuItem.setChecked(false);
                mDrawerMenu.closeDrawers();
                UIHelper.ToastMessage(getApplicationContext(), (String) menuItem.getTitle(), 0);
                break;
        }
        return true;
    }
}
