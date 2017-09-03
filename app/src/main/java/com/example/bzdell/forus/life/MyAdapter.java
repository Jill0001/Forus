package com.example.bzdell.forus.life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bzdell.forus.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 2017/8/30.
 */

public class MyAdapter extends BaseAdapter {
    private int mRightWidth = 400;
    private ArrayList<HashMap<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdapter(Context context,ArrayList<HashMap<String, Object>> data){
        this.context =context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);


    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        zujian zujian = null;
        if(convertView == null){
            zujian = new zujian();
            convertView=layoutInflater.inflate(R.layout.life_listviewitem,null);
            zujian.click=(FrameLayout)convertView.findViewById(R.id.click);
            zujian.imageView =(ImageView)convertView.findViewById(R.id.headphoto);
            zujian.titleview =(TextView)convertView.findViewById(R.id.title);
            zujian.authorview =(TextView)convertView.findViewById(R.id.author);
            zujian.contentview =(TextView)convertView.findViewById(R.id.content);
            zujian.timeview =(TextView)convertView.findViewById(R.id.time);
            zujian.seeview =(TextView)convertView.findViewById(R.id.see);
            zujian.item_right=(RelativeLayout) convertView.findViewById(R.id.item_right);

            convertView.setTag(zujian);
        }
        else{
            zujian = (zujian) convertView.getTag();
        }
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        zujian.item_right.setLayoutParams(lp2);
//            zujian.imageView.setBackgroundResource((Integer) data.get(position).get("image"));
            zujian.titleview.setText((String)data.get(position).get("title"));
            zujian.authorview.setText((String)data.get(position).get("author"));
            zujian.contentview.setText((String)data.get(position).get("content"));
            zujian.timeview.setText((String)data.get(position).get("time"));
//          zujian.seeview.setText((String)data.get(position).get("see"));
//            String id=(String) data.get(position).get("id");

//            zujian.click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    showInfo();
//                }
//            });



        zujian.item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightItemClick(v, position);
                }
            }
        });




        return convertView;
    }

    private onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(onRightItemClickListener listener){
        mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}


