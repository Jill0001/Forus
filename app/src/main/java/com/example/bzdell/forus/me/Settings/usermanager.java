package com.example.bzdell.forus.me.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bzdell.forus.R;

public class usermanager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_usermanager);
    }
    public  void changepwd(View view){
        startActivity(new Intent(usermanager.this,changepwd.class));
    }
}
