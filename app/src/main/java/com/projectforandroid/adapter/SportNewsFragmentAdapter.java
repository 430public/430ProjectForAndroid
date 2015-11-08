package com.projectforandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.data.EntertainmentBean;
import com.projectforandroid.data.SportNewsBean;
import com.projectforandroid.widget.BaseTextView;
import com.projectforandroid.widget.GeneralImageView;

import java.util.List;

/**
 * Created by 杰 on 2015/10/15.
 */
public class SportNewsFragmentAdapter extends ListBaseAdapter {

    private ProjectApplication mApplication;

    //定义静态方法ViewHolder
    static class ViewHolder {
        public GeneralImageView Header;
        public BaseTextView Title;
        public BaseTextView Content;
        public ImageView img_jiantou;
    }

    public SportNewsFragmentAdapter(Context context, List list, ListView listView) {
        super(context, list, listView);
        mApplication = (ProjectApplication) context.getApplicationContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_toplineitem, parent, false);
            holder.Title = (BaseTextView) convertView.findViewById(R.id.tv_title);
            holder.Header = (GeneralImageView) convertView.findViewById(R.id.item_avatar);
            holder.Content = (BaseTextView) convertView.findViewById(R.id.tv_content);
            holder.img_jiantou = (ImageView) convertView.findViewById(R.id.imgbutton_jiantou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mTList.size() > 0) {
            SportNewsBean.SportNewsBeanResult bean =
                    (SportNewsBean.SportNewsBeanResult) mTList.get(position);
            holder.Title.setText(bean.title);
            holder.Header.loadImage(bean.picUrl);
            holder.Content.setText(bean.description);
        }
        return convertView;
    }
}
