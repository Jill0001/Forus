package com.example.bzdell.forus.me.mynotice;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.R;
import com.example.bzdell.forus.life.MyAdapter;
import com.example.bzdell.forus.life.Notice;
import com.example.bzdell.forus.life.Noticedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class mynotice extends ListActivity {
private ListView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mynotice);
        a=(ListView)findViewById(android.R.id.list);
        UserDate user = BmobUser.getCurrentUser(UserDate.class);

        BmobQuery<Notice> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("author",user);
        query.include("author[username]");
        query.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> list, BmobException e) {
                if (e == null) {

                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();


                    for (Notice notice : list) {
                        HashMap<String, Object> tempHashMap = new HashMap<>();
                        tempHashMap.put("title", notice.getTitle());
                        tempHashMap.put("time", notice.getCreatedAt());
                        tempHashMap.put("content", notice.getContent());
                        tempHashMap.put("author", notice.author.getUsername());
                        tempHashMap.put("id",notice.getObjectId());

                        arrayList.add(tempHashMap);
                    }

                    MyAdapter adapter = new MyAdapter(mynotice.this, arrayList);
                    setListAdapter(adapter);

                }
            }

        });



    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        l=a;
        HashMap<String,String> map=(HashMap<String,String>)a.getItemAtPosition(position);
        String ID =map.get("id");
        Intent intent =new Intent(mynotice.this, Noticedetail.class);
        intent.putExtra("id",ID);
        startActivity(intent);
    }   //Listview 点击事件



}
