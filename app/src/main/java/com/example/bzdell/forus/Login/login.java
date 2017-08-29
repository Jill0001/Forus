package com.example.bzdell.forus.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bzdell.forus.MainActivity;
import com.example.bzdell.forus.R;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class login extends AppCompatActivity {
private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);

        sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        final Button btn=(Button)findViewById(R.id.login3);
        final EditText et_user=(EditText)findViewById(R.id.login1);
        final  EditText et_pwd=(EditText)findViewById(R.id.login2);
        final CheckBox cb1=(CheckBox)findViewById(R.id.login5) ;
        final CheckBox cb2=(CheckBox)findViewById(R.id.login6) ;
        Bmob.initialize(this,"bfb519d77e9049fbf4c475c3c55cc49d");
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);
        Button btn1=(Button)findViewById(R.id.login4);




        btn1.setOnClickListener(new Button.OnClickListener() {  //注册按钮功能
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(login.this,register.class);
                startActivity(intent);
            }
        });



        btn.setOnClickListener(new  View.OnClickListener()  {    //登录按钮功能
        @Override
        public  void  onClick(View  v)  {
            final String  username=et_user.getText().toString();
            final String  pwd=et_pwd.getText().toString();
            final UserDate bu2 = new UserDate();
            if(username.equals("")||pwd.equals("")){
                Toast.makeText(login.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
            }

            bu2.setUsername(username);
            bu2.setPassword(pwd);
            bu2.login(new SaveListener<UserDate>() {

                @Override
                public void done(UserDate u, BmobException e) {
                    if(e==null){
                        if(cb1.isChecked()){
                            SharedPreferences.Editor editor =sp.edit();
                            editor.putString("USERNAME",username);
                            editor.putString("PASSWORD",pwd);
                            editor.commit();
                        }
                        if(bu2.getEmailVerified()){
                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                        }
                        else
                            {
                            Toast.makeText(login.this,"请前往邮箱进行验证",Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(e.getErrorCode()==9016) {
                        Toast.makeText(login.this,"登录失败，当前网络不可以用",Toast.LENGTH_SHORT).show();

                    }
                       if(e.getErrorCode()==101)
                    {
                       cb2.setChecked(false);
                        Toast.makeText(login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }
             }
            }
     );
        }
    });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {      //自动登录按钮
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb2.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("ACHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("ACHECK", false).commit();
                }
            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   //记住密码按钮
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (cb1.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("CHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("CHECK", false).commit();

                }

            }
        });

        if(sp.getBoolean("CHECK",false)){     //记住密码和自动登录功能
            cb1.setChecked(true);
            et_user.setText(sp.getString("USERNAME",""));
            et_pwd.setText(sp.getString("PASSWORD",""));

            if(sp.getBoolean("ACHECK",false)){
//                cb2.setChecked(true);
                if(MainActivity.getCurrentUser()!=null){
                    startActivity(new Intent(login.this,MainActivity.class));
                    finish();
                }
            }
        }
}

public void fasong(View v){
    EditText b=(EditText)findViewById(R.id.login8);
    String mail=b.getText().toString();
    UserDate.requestEmailVerify(mail,new UpdateListener(){
        @Override
        public void done(BmobException e) {
            if (e==null)
            {
                Toast.makeText(login.this,"发送成功",Toast.LENGTH_SHORT).show();
            }
        }

    });
}
}
