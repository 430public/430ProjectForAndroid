package com.projectforandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.projectforandroid.R;
import com.projectforandroid.adapter.TopLineFragmentAdapter;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.http.request.HotNewsRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.DetailActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/9/24.
 */
public class TopLineFragment extends Fragment implements OnResponseListener {
    private HotNewsRequest mHotNewsRequest;
    private List<HotNewBean.HotNewBeanResult> mylist;
    private ListView listView;
    private TopLineFragmentAdapter adapter;
    private Intent intent;
    private ArrayList<String> DetialList = new ArrayList<>();//用于存放详细的新闻信息

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        mylist = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_topline, null);
        listView = (ListView) view.findViewById(R.id.topline_listview);
        adapter = new TopLineFragmentAdapter(getActivity(), mylist, listView);
        startToGetData();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DetialList.clear();
                DetialList.add(0,mylist.get(position).title);
                DetialList.add(1,String.valueOf(mylist.get(position).time));
                DetialList.add(2,mylist.get(position).description);
                intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putStringArrayListExtra("detail", DetialList);
                startActivity(intent);
            }
        });
        return view;
    }

    private void startToGetData() {
        mHotNewsRequest = new HotNewsRequest(this.getActivity(), 2, 30, 2);
        mHotNewsRequest.setOnResponseListener(this);
        if (mHotNewsRequest.LoadCache() != null) {
            HotNewBean bean = (HotNewBean) mHotNewsRequest.LoadCache();
            this.mylist.clear();
            this.mylist.addAll(bean.getHotNewBeans());
            adapter.notifyDataSetChanged();
        } else {
            mHotNewsRequest.execute();
        }
    }

    //------------------------------------------listener-----------------------------------------------
    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 0) return;
        HotNewBean bean = (HotNewBean) response.getData();
        this.mylist.clear();
        this.mylist.addAll(bean.getHotNewBeans());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIHelper.ToastMessage(this.getActivity(), "失败", 0);
    }

    @Override
    public void onHttpStart() {

    }

    @Override
    public void onHttpFinish() {

    }
}
