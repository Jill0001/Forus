package com.example.bzdell.forus.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bzdell.forus.R;

/**
 * Created by Jack on 2017/8/22.
 */

public class lifeFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, container, false);
        return view;
    }
    public static lifeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        lifeFragment fragment = new lifeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
