package com.projectforandroid.widget.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import com.projectforandroid.R;
import com.projectforandroid.ui.UIHelper;

/**
 * Created by 大灯泡 on 2015/9/24.
 */
public abstract class BasePopup extends PopupWindow implements OnClickListener{
    private static final String TAG = "BASE POPUP";
    protected View contentView;//容纳弹窗的view
    protected PopupWindow mPopupWindow;//弹窗
    private Activity mActivity;

    private View cancelButton;
    private View completeButton;

    private OnDismissListener listener;
    private OnCompleteClickListener mOnCompleteClickListener;
    public BasePopup(Activity activity) {
        this.mActivity = activity;
        contentView = getView();
        contentView.setFocusableInTouchMode(true);

        cancelButton=getCancelButton();
        completeButton=getCompleteButton();
        if (cancelButton!=null)
            cancelButton.setOnClickListener(this);
        if (completeButton!=null)
            completeButton.setOnClickListener(this);
        mPopupWindow =
            new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(0);
        setTitleText();
    }

    public void showPopupWindow() {
        try {
            mPopupWindow.showAtLocation(mActivity.findViewById(android.R.id.content),
                Gravity.RIGHT | Gravity.CENTER_HORIZONTAL, 0, 0);
            if (setAnima()!=null&&contentView!=null){
                contentView.findViewById(R.id.popup_base).startAnimation(setAnima());
            }
        } catch (Exception e) {
            Log.e(TAG, "Show Popup Error");
        }
    }

    public void showPopupWindow(int resource) {
        try {
            mPopupWindow.showAtLocation(mActivity.findViewById(resource),
                Gravity.RIGHT | Gravity.CENTER_HORIZONTAL, 0, 0);
            if (setAnima()!=null&&contentView!=null){
                contentView.findViewById(R.id.popup_base).startAnimation(setAnima());
            }
        } catch (Exception e) {
            Log.e(TAG, "Show Popup Error");
        }
    }

    public void showPopupWindow(View view) {
        try {
            mPopupWindow.showAsDropDown(view);
            if (setAnima()!=null&&contentView!=null){
                contentView.findViewById(R.id.popup_base).startAnimation(setAnima());
            }
        } catch (Exception e) {
            Log.e(TAG, "Show Popup Error");
        }
    }

    /** 设置弹框大小 */
    public void setPopupSize(int width, int height) {
        mPopupWindow = new PopupWindow(contentView, width, height);
    }

    public void dismiss() {
        try {
            mPopupWindow.dismiss();
        } catch (Exception e) {
            Log.e(TAG, "Dismiss Popup Error");
        }
    }

    public View inflateView(int layoutID){
        return LayoutInflater.from(mActivity).inflate(layoutID,null);
    }

    //------------------------------------------抽象方法-----------------------------------------------
    public abstract void setTitleText();
    public abstract View getView();
    public abstract Animation setAnima();
    public abstract View getCancelButton();
    public abstract View getCompleteButton();

    //------------------------------------------动画-----------------------------------------------

    /**
     * 得到从底部滑动出来的动画
     */
    protected Animation getTransAnimaFromBottom() {
        Animation translateAnimation =
            new TranslateAnimation(0, 0, UIHelper.getScreenHeight(mActivity), 0);
        translateAnimation.setDuration(300);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillBefore(true);
        return translateAnimation;
    }

    /**
     * 得到自定义位移动画
     */
    protected Animation getTransAnimaCustom(int fromX, int toX, int fromY, int toY) {
        Animation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setDuration(300);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillBefore(true);
        return translateAnimation;
    }

    /**
     * 得到缩放动画
     */
    protected Animation getScaleAnimation() {
        Animation scaleAnimation =
            new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillBefore(true);
        return scaleAnimation;
    }

    @Override
    public void onClick(View v) {
        if (null!=cancelButton&&v==cancelButton)
            mPopupWindow.dismiss();
        if (null!=completeButton&&v==completeButton){
            if (mOnCompleteClickListener!=null)
                mOnCompleteClickListener.onCompleteClick(v);
        }
    }

    //------------------------------------------Setter-----------------------------------------------

    public void setOnDismissListener(final OnDismissListener listener) {
        this.listener = listener;
        if (listener != null) {
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    listener.onDismiss();
                }
            });
        }
    }

    public void setOnCompleteClickListener(OnCompleteClickListener onCompleteClickListener) {
        mOnCompleteClickListener = onCompleteClickListener;
    }

    //------------------------------------------接口-----------------------------------------------
    public interface OnDismissListener {
        void onDismiss();
    }
    public interface OnCompleteClickListener{
        void onCompleteClick(View v);
    }
}
