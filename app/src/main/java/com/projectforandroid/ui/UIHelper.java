package com.projectforandroid.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectforandroid.R;

import java.util.logging.Handler;

/**
 * Created by 大灯泡 on 2015/9/1.
 * 请将所有的startActivity，所有关于UI的工具类都放到这里
 */
public class UIHelper { 
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStateBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int titleBarHeight = 0;
        // 定义区域
        Rect titleFrame = new Rect();
        // 得到高度
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(titleFrame);
        statusBarHeight = titleFrame.top;
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     */
    public static int getTitleBarHeight(Activity activity) {
        int titleBarHeight = 0;
        int contenttop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        titleBarHeight = contenttop - getStateBarHeight(activity);
        return titleBarHeight;
    }

    /**
     * 得到屏幕宽高，返回Int数组，int[0]=宽，int[1]=高
     */
    public static int[] getScreenWH(Activity activity) {
        int[] WH = {0, 0};
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WH[0] = metrics.widthPixels;
        WH[1] = metrics.heightPixels;
        return WH;
    }
    /** 隐藏软键盘 */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /** 显示软键盘 */
    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /** 显示软键盘 */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**自定义信息*/
    private static void ToastMessage(Context context, String msg, int iconResid) {
        View view = (View) LayoutInflater.from(context).inflate(
                R.layout.widget_toast_view, null);
        TextView toast_msg = (TextView) view.findViewById(R.id.toast_msg);
        ImageView toast_icon = (ImageView) view.findViewById(R.id.toast_icon);
        if (iconResid > 0) {
            toast_icon.setVisibility(View.VISIBLE);
            toast_icon.setImageResource(iconResid);
        }
        toast_msg.setText(msg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    //------------------------------------------启动Activity的方法请放到这里---------------------------------------------

}
