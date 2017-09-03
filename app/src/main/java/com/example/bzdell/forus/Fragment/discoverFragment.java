package com.example.bzdell.forus.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bzdell.forus.R;

/**
 * Created by Jack on 2017/8/22.
 */

public class discoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe3);
        initData();
        return view;
    }
    public static discoverFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        discoverFragment fragment = new discoverFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void initData(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);

    }   //下拉刷新监视器

    @Override
    public void onRefresh(){

        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }    //下拉刷新功能

}
