package com.projectforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.widget.GeneralImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/9/24.
 */
public class TopLineFragmentAdapter extends ListBaseAdapter {
    //定义静态方法ViewHolder
    static class ViewHolder {
        public GeneralImageView Header;
        public TextView Title;
        public TextView Content;
        public ImageView img_jiantou;
    }

    public TopLineFragmentAdapter(Context context, List list, ListView listView) {
        super(context, list, listView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_toplineitem, parent, false);
            holder.Title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.Header = (GeneralImageView) convertView.findViewById(R.id.item_avatar);
            holder.Content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.img_jiantou = (ImageView) convertView.findViewById(R.id.imgbutton_jiantou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mTList.size() > 0) {
            HotNewBean.HotNewBeanResult bean = (HotNewBean.HotNewBeanResult) mTList.get(position);
            holder.Title.setText(bean.title);
            holder.Header.loadImage(bean.img);
            holder.Content.setText(bean.description);
        }
        return convertView;
    }
}


