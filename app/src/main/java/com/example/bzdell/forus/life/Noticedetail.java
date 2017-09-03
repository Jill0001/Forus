package com.example.bzdell.forus.life;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Noticedetail extends AppCompatActivity {
    String ID ;
    private TextView a,b,c,d;
    private ImageView ee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticedetail);
        Intent intent = getIntent();

        if (intent != null) {
           ID= intent.getStringExtra("id");
        }

        a=(TextView)findViewById(R.id.title0);
        b=(TextView)findViewById(R.id.content0);
        c=(TextView)findViewById(R.id.author0);
        d=(TextView)findViewById(R.id.time0);
        ee=(ImageView)findViewById(R.id.love);



        BmobQuery<Notice> query = new BmobQuery<>();
        query.include("author");
        query.getObject(ID, new QueryListener<Notice>() {
            @Override
            public void done(Notice notice, BmobException e) {
                if(e==null){
                    a.setText(notice.getTitle());
                    b.setText(notice.getContent());
                    c.setText(notice.author.getUsername());
                    d.setText(notice.getCreatedAt());
                }
                else{
                    Toast.makeText(Noticedetail.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }) ;



        Notice notice = new Notice();
        notice.setObjectId(ID);
        BmobRelation relation = new BmobRelation();
        UserDate user = BmobUser.getCurrentUser(UserDate.class);
        relation.add(user);
        notice.setLove(relation);
        notice.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","用户和该帖子关联成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });


        UserDate users = BmobUser.getCurrentUser(UserDate.class);

        BmobQuery<Notice> query0= new BmobQuery<>();

        query0.addWhereRelatedTo("love", new BmobPointer(users));
        query0.findObjects(new FindListener<Notice>() {

            @Override
            public void done(List<Notice> object, BmobException e) {
                if(e==null){
                    int i;
                    for(i=0;i<object.size();i++) {
                       if(a.getText().equals(object.get(i).getTitle())){
                        ee.setVisibility(View.GONE);break;}
                    }
                }
            }

        });







    }

    public void love(View v){
        UserDate bmobUser = BmobUser.getCurrentUser(UserDate.class);

        Notice notice =new Notice();
        notice.setObjectId(ID);
        BmobRelation relation=new BmobRelation();
       relation.add(notice);
        bmobUser.setLove(relation);
        bmobUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                ee.setVisibility(View.GONE);
                    Log.i("bmob","收藏成功");
                }
                else{
                    Log.i("bmob","收藏失败");
                }
            }
        });
    }


    public void nolove(View view){
        UserDate bmobUser = BmobUser.getCurrentUser(UserDate.class);
        Notice notice =new Notice();
        notice.setObjectId(ID);
        BmobRelation relation=new BmobRelation();
        relation.remove(notice);
        bmobUser.setLove(relation
        );
        bmobUser.update( new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if(e==null){
                    ee.setVisibility(View.VISIBLE);
                    Log.i("bmob","取消收藏成功");
                }
                else{
                    Log.i("bmob","取消收藏失败");
                }

            }
        });
    }

}