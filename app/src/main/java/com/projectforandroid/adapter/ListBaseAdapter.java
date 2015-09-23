package com.projectforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.projectforandroid.imageloader.ImageLoaderCache;
import java.util.List;

/**
 * Created by 大灯泡 on 2015/9/23.
 */
public abstract class ListBaseAdapter<T> extends BaseAdapter{
    protected List<T> mTList;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ListView mListView;

    public ListBaseAdapter(Context context, List<T> list, ListView listView) {
        mContext = context;
        mTList = list;
        mInflater = LayoutInflater.from(context);
        mListView = listView;
        listView.setOnScrollListener(mPauseOnScrollListener);
    }

    @Override
    public int getCount() {
        return mTList != null ? mTList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return (mTList == null ? null : mTList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    //刷新adapter
    public void refreshAdapter(List<T> arrayList) {
        mTList.addAll(arrayList);
        notifyDataSetChanged();
    }

    //清空list集合
    public void clear() {
        mTList.clear();
        notifyDataSetChanged();
    }

    //移除指定位置的对象
    public void remove(int index) {
        mTList.remove(index);
        notifyDataSetChanged();
    }

    //移除对象
    public void remove(Object obj) {
        mTList.remove(obj);
        notifyDataSetChanged();
    }

/*    public void changeScrollState(AbsListView view,int scrollState) {
        switch (scrollState){
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止滚动
                load(view);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://正在滚动
                stopLoad(view);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动做出了抛的动作
                stopLoad(view);
                break;
        }

    }

    //------------------------------------------停止滚动和进行滚动-----------------------------------------------
    public abstract void stopLoad(AbsListView view);
    public abstract void load(AbsListView view);*/


    private PauseOnScrollListener mPauseOnScrollListener=new PauseOnScrollListener(ImageLoaderCache.getInstance().getImageLoader(),true,true);

}
