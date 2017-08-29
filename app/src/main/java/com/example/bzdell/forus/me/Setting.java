package com.example.bzdell.forus.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bzdell.forus.Login.login;
import com.example.bzdell.forus.R;

import cn.bmob.v3.BmobUser;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting);
    }

    public void tuichu(View view){
        BmobUser bmobUser = BmobUser.getCurrentUser();
        bmobUser.logOut();
        startActivity(new Intent(Setting.this,login.class));
    }
    public void zhanghao(View view){
        startActivity(new Intent(Setting.this,usermanager.class));
    }
}
