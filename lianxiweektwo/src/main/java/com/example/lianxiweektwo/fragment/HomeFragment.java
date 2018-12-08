package com.example.lianxiweektwo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lianxiweektwo.R;
import com.example.lianxiweektwo.baseadapter.ListAdapter;
import com.example.lianxiweektwo.bean.NewsBean;
import com.example.lianxiweektwo.persenter.IPersenterImpl;
import com.example.lianxiweektwo.view.IView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IView {

    private FlyBanner flyBanner;
    private PullToRefreshListView listView;
    private ListAdapter listAdapter;
    private IPersenterImpl iPersenter;
    private String path = "http://www.xieast.com/api/news/news.php?page=%d";
    private int page;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homefragment,container,false);
        //获取资源ID
        flyBanner = v.findViewById(R.id.flyB);
        listView = v.findViewById(R.id.listv);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPersenter = new IPersenterImpl(this);
        page = 1;
        List<String> list = new ArrayList<>();
        list.add("http://www.zhaoapi.cn/images/quarter/ad1.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad2.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad3.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad4.png");
        flyBanner.setImagesUrl(list);
        listAdapter = new ListAdapter(getActivity());
        listView.setAdapter(listAdapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                iPersenter.showRequestData(String.format(path,page),NewsBean.class);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                iPersenter.showRequestData(String.format(path,page),NewsBean.class);
            }
        });
        iPersenter.showRequestData(String.format(path,page),NewsBean.class);
    }

    @Override
    public void startRequestData(Object data) {

        NewsBean bean = (NewsBean) data;
        if (page == 1){
            listAdapter.setData(bean.getData());
        }else {
            listAdapter.addData(bean.getData());
        }
        page++;
        listView.onRefreshComplete();
    }
}
