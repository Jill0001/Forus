package com.example.bzdell.forus.life;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.bzdell.forus.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;



import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class search extends ListActivity implements OnEditorActionListener {
    private int width, height;
    private EditText t;
    private ListView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_search);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        t = (EditText)findViewById(R.id.editText);
        t.setWidth(width - 20);
        t.setOnEditorActionListener(this);
        a=(ListView)findViewById(android.R.id.list);

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            final String x=t.getText().toString();
            BmobQuery<Notice> query = new BmobQuery<>();
            query.order("-updatedAt");
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
                            if(notice.getTitle().indexOf(x)!=-1)
                            arrayList.add(tempHashMap);

                            }

//                        ArrayList<HashMap<String, Object>> arrayList0 = new ArrayList<>();
//                        int i;
//
//                    for(i=0;i<10;i++){
//                        HashMap<String,Object> temHasMap0= new HashMap<>();
//                        temHasMap0.put("xxx",i);
//                        arrayList0.add(temHasMap0);
//
//                    }



                        MyAdapter adapter = new MyAdapter(search.this, arrayList);
                        setListAdapter(adapter);

                    }
                }

            });




        }
        return false;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        l=a;
        HashMap<String,String> map=(HashMap<String,String>)a.getItemAtPosition(position);
        String ID =map.get("id");
        Intent intent =new Intent(search.this, Noticedetail.class);
        intent.putExtra("id",ID);
        startActivity(intent);
    }   //Listview 点击事件
}
