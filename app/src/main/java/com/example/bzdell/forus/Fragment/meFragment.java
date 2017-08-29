package com.example.bzdell.forus.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.R;
import com.example.bzdell.forus.me.Setting;

/**
 * Created by Jack on 2017/8/22.
 */

public class meFragment extends Fragment {
    private FragmentActivity mContext;
    private FrameLayout layout;
    private TextView ea,eb;
    private ImageView ec;
    private LinearLayout ed;
    private SharedPreferences sp,sb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    public static meFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        meFragment fragment = new meFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ea=(TextView)getActivity().findViewById(R.id.textView);
        eb=(TextView)getActivity().findViewById(R.id.textView1);
        layout=(FrameLayout)getActivity().findViewById(R.id.LinearLayout1);
        ec=(ImageView)getActivity().findViewById(R.id.iv_head);
        ed= (LinearLayout)getActivity().findViewById(R.id.settings);

        String a=(String ) UserDate.getObjectByKey("nickname");
        String b=(String ) UserDate.getObjectByKey("school");

        sp=getActivity().getSharedPreferences("touxiang", Context.MODE_PRIVATE);
        sb=getActivity().getSharedPreferences("duibi", Context.MODE_PRIVATE);

        String x= sp.getString("path","");

        if(!x.equals("")) {

            Glide.with(this).load(x).centerCrop().signature( new StringSignature("01")).into(ec);                //uri加载方式
        }

        ea.setText(a);
        eb.setText(b);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() ,UserInfo.class);
                startActivity(intent);
            }
        });

        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,Setting.class);
                startActivity(intent);
            }
        });

    }

}
