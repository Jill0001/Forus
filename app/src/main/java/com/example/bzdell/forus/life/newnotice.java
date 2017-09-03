package com.example.bzdell.forus.life;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class newnotice extends AppCompatActivity {
    private EditText a,b;
    private Button c,d,e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_newnotice);

        a=(EditText)findViewById(R.id.new1);
        b=(EditText)findViewById(R.id.new2);
        c=(Button)findViewById(R.id.new3);
        d=(Button)findViewById(R.id.new4);
        e=(Button)findViewById(R.id.new5);

    }

    public void newnotice1(View v){

        UserDate bu=new UserDate();
        UserDate user = BmobUser.getCurrentUser(UserDate.class);
        String ea=a.getText().toString();
        String eb=b.getText().toString();
        String author=(String)UserDate.getObjectByKey("username");



        if (ea.equals("")||eb.equals("")){
            Toast.makeText(newnotice.this,"输入框为空",Toast.LENGTH_LONG).show();
            return;
        }

        Notice notice =new Notice();
        notice.setTitle(ea);
        notice.setContent(eb);
        notice.setAuthor(user);

        notice.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(newnotice.this,"发布成功",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
