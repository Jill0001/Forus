package com.example.bzdell.forus.me.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bzdell.forus.Login.login;
import com.example.bzdell.forus.MainActivity;
import com.example.bzdell.forus.R;

import cn.bmob.v3.BmobUser;

public class Setting extends AppCompatActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting);
        sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public void tuichu(View view){

        BmobUser bmobUser = BmobUser.getCurrentUser();
        bmobUser.logOut();
        startActivity(new Intent(Setting.this,login.class));

        sp.edit().putBoolean("ACHECK", false).commit();
        MainActivity.test_a.finish();
        finish();
    }
    public void zhanghao(View view){
        startActivity(new Intent(Setting.this,usermanager.class));
    }
    public void appinfo(View view){
        startActivity(new Intent(Setting.this,Appinfo.class));
    }
    public void sendback(View view){
        startActivity(new Intent(Setting.this,sendback.class));
    }
}
