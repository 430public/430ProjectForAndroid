package com.projectforandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.projectforandroid.R;
import java.util.List;

/**
 * Created by 杰 on 2015/9/24.
 */
public class FragmentAdapter extends ListBaseAdapter {
    /*//------------------------------------------定义内容（只是Example)-----------------------------------------------
    public String[] itemtitle = new String[] {
        "This is item1 title", "This is item2 title", "This is item3 title", "This is item4 title",
        "This is item5 title", "This is item6 title", "This is item7 title"
    };
    public int[] header = new int[] {
        R.drawable.header, R.drawable.header, R.drawable.header, R.drawable.header,
        R.drawable.header, R.drawable.header, R.drawable.header
    };
    public  String[] content=new String[]{"This is item1 content", "This is item2 content", "This is item3 content", "This is item4 content",
        "This is item5 content", "This is item6 content", "This is item7 content"};

    //------------------------------------------上面是个例子-----------------------------------------------*/
    //定义静态方法ViewHolder
    static class ViewHolder {
        public ImageView Header;
        public TextView Title;
        public TextView Content;
        public ImageView img_jiantou;
    }
    public FragmentAdapter(Context context, List list, ListView listView) {
        super(context, list, listView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_toplineitem,parent,false);
            holder.Title=(TextView)convertView.findViewById(R.id.tv_title);
            holder.Header=(ImageView)convertView.findViewById(R.id.item_avatar);
            holder.Content=(TextView)convertView.findViewById(R.id.tv_content);
            holder.img_jiantou=(ImageView)convertView.findViewById(R.id.imgbutton_jiantou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        //holder.Title.setText(mTList.get(position));
        //holder.Header.setImageResource(header[position]);
        //holder.Content.setText(content[position]);
        holder.img_jiantou.setImageResource(R.drawable.btn_jiantou);
        return convertView;
    }
}

