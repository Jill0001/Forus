package com.example.bzdell.forus.me;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bzdell.forus.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class changepwd extends AppCompatActivity {

    private EditText a,b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_changepwd);
        a=(EditText)findViewById(R.id.change1);
        b=(EditText)findViewById(R.id.change2);
        c=(EditText)findViewById(R.id.change3);
    }

    public void change(View view){
        String ea=a.getText().toString();
        String eb=b.getText().toString();
        String ec=c.getText().toString();
        if(!ec.equals(eb)){
            Toast.makeText(changepwd.this,"两次密码不一样",Toast.LENGTH_SHORT).show();
        }
        if(ec.equals(eb)) {
            BmobUser.updateCurrentUserPassword(ea, eb, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(changepwd.this,"密码已修改",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                    if(e.getErrorCode()==210){
                        Toast.makeText(changepwd.this,"旧密码输入错误",Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        }

    }




