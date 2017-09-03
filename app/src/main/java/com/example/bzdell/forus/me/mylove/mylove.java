package com.example.bzdell.forus.me.mylove;

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
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class mylove extends ListActivity {
    private ListView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mylove);

        a=(ListView)findViewById(android.R.id.list);
        UserDate user = BmobUser.getCurrentUser(UserDate.class);





        UserDate users = BmobUser.getCurrentUser(UserDate.class);

        BmobQuery<Notice> query0= new BmobQuery<>();
        query0.addWhereRelatedTo("love", new BmobPointer(users));
        query0.findObjects(new FindListener<Notice>() {

            @Override
            public void done(List<Notice> object, BmobException e) {

                ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
                if(e==null){
                    int i;
                    for(i=0;i<object.size();i++) {
                        object.get(i).getTitle();

                        HashMap<String, Object> tempHashMap = new HashMap<>();
                        tempHashMap.put("title", object.get(i).getTitle());
                        tempHashMap.put("time", object.get(i).getCreatedAt());
                        tempHashMap.put("content", object.get(i).getContent());
                        tempHashMap.put("author", object.get(i).author.getUsername());
                        tempHashMap.put("id",object.get(i).getObjectId());
                        arrayList.add(tempHashMap);

                    }

                    MyAdapter adapter = new MyAdapter(mylove.this, arrayList);
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
        Intent intent =new Intent(mylove.this, Noticedetail.class);
        intent.putExtra("id",ID);
        startActivity(intent);
    }   //Listview 点击事件






}
