package com.example.bzdell.forus.me.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bzdell.forus.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class sendback extends AppCompatActivity {
    public EditText mName,mFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_sendback);

        mName = (EditText) findViewById(R.id.sendback2);
        mFeedback = (EditText)findViewById(R.id.sendback1);
    }

    public void submit(View view){
        String name = mName.getText().toString();
        String feedback = mFeedback.getText().toString();
        if (feedback.equals("")){
            Toast.makeText(sendback.this,"输入框为空",Toast.LENGTH_LONG).show();
            return;
        }
        Feedback feedbackObj = new Feedback();
        feedbackObj.setName(name);
        feedbackObj.setFeedback(feedback);
        feedbackObj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(sendback.this,"提交成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(sendback.this,"提交失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
