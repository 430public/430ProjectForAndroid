package com.projectforandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.adapter.EntertainmentFragmentAdapter;
import com.projectforandroid.data.EntertainmentBean;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.http.request.EntertainmentRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.DetailActivity;
import com.projectforandroid.utils.dateutils.DateUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/9/21.
 */
public class EntertainmentFragment extends Fragment implements OnResponseListener {
    private EntertainmentRequest mEntertainmentRequest;
    private EntertainmentFragmentAdapter EntertainmentAdapter;
    private ListView mListView;
    private List<EntertainmentBean.EntertainmentBeanResult> mlist;
    private EntertainmentBean ebean;
    private ArrayList<String> DetialList=new ArrayList<>();
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topline, null);
        GetData();
        mListView = (ListView) view.findViewById(R.id.topline_listview);//服用topLine的布局文件
        mlist = new ArrayList<>();
        EntertainmentAdapter =
            new EntertainmentFragmentAdapter(this.getActivity(), mlist, mListView);
        mListView.setAdapter(EntertainmentAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DetialList.clear();
                DetialList.add(0, mlist.get(position).title);
                DetialList.add(1, String.valueOf(mlist.get(position).time));
                DetialList.add(2, mlist.get(position).description);
                intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putStringArrayListExtra("detail", DetialList);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetData() {
        mEntertainmentRequest = new EntertainmentRequest(this.getActivity(), 10, 1, "10881",
            DateUtils.getyyyyMMddHHmmss(System.currentTimeMillis()),
            ProjectApplication.getSecretKey());
        mEntertainmentRequest.setOnResponseListener(this);
        if (mEntertainmentRequest.LoadCache() != null) {
             ebean = (EntertainmentBean) mEntertainmentRequest.LoadCache();
            this.mlist.clear();
            this.mlist.addAll(ebean.getEntertainmentBeans());
            EntertainmentAdapter.notifyDataSetChanged();
        } else {
            mEntertainmentRequest.execute();
        }
    }

    //------------------------------------------Listener-----------------------------------------------
    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 0) {
            return;
        } else if (response.getStatus() != 0) {
            ebean = (EntertainmentBean) response.getData();
            mlist.clear();
            mlist.addAll(ebean.getEntertainmentBeans());
            EntertainmentAdapter.notifyDataSetChanged();
        }
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
