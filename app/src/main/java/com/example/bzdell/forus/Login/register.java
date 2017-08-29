package com.example.bzdell.forus.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bzdell.forus.R;
import com.example.bzdell.forus.Utils.EmailCheck;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class register extends AppCompatActivity {

   private Button btn;
    private EditText euser,email,epwd,eagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        btn = (Button) findViewById(R.id.register5);
        euser = (EditText) findViewById(R.id.register1);
        email = (EditText) findViewById(R.id.register2);
        epwd = (EditText) findViewById(R.id.register3);
        eagain = (EditText) findViewById(R.id.register4);

//
//              BmobQuery<BmobUser> query0 =new BmobQuery<>();
//              query0.addWhereEqualTo("username",user);
//              query0.findObjects(new FindListener<BmobUser>(){
//                  @Override
//                  public void done(List<BmobUser> list, BmobException e) {
//                      if(list.size()!=0){
//                          Toast.makeText(register.this,"用户名已被注册",Toast.LENGTH_SHORT).show();
//                      }
//                      else{
//                          BmobQuery<BmobUser> query =new BmobQuery<>();   //判断用户名是否已经注册
//                          query.addWhereEqualTo("email",mail);
//                          query.findObjects(new FindListener<BmobUser>(){
//
//                              @Override
//                              public void done(List<BmobUser> list, BmobException e) {
//                                  if(list.size()!=0){
//                                      Toast.makeText(register.this,"邮箱已被注册",Toast.LENGTH_SHORT).show();
//                                  }
//
//                                  else {

}
    public void signup(View v){
        final String user=euser.getText().toString();
        final String mail=email.getText().toString();
        final String pwd=epwd.getText().toString();
        String again=eagain.getText().toString();


        if((user.equals(""))||(pwd.equals(""))){
            Toast.makeText(register.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        if(!EmailCheck.isValidEmail(mail)){
            Toast.makeText(register.this,"邮箱格式不正确",Toast.LENGTH_SHORT).show();
        }
        if(!pwd.equals(again)){
            Toast.makeText(register.this,"密码不一致",Toast.LENGTH_SHORT).show();
        }
        if((pwd.length()<6)||(pwd.length()>16)){
            Toast.makeText(register.this,"密码必须大于等于6位，小于等于16位",Toast.LENGTH_SHORT).show();
        }


        if((!user.equals(""))&&(EmailCheck.isValidEmail(mail))&&(pwd.equals(again))&&(!pwd.equals(""))&&(pwd.length()>=6)){

            UserDate bu =new UserDate();
            bu.setUsername(user);         //注册按钮功能
            bu.setEmail(mail);
            bu.setPassword(pwd);
            bu.signUp(new SaveListener<UserDate>() {

                @Override
                public void done(UserDate userDate, BmobException e) {         //自带邮箱验证，不需要再发一次。md气死我了。
                    if (e == null) {
                        Toast.makeText(register.this, "注册成功，请前往邮箱进行验证", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if(e.getErrorCode()==202){
                        Toast.makeText(register.this,"用户名已被注册",Toast.LENGTH_SHORT).show();
                    }

                    if(e.getErrorCode()==203){
                        Toast.makeText(register.this,"邮箱已被注册",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(register.this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }
                  }
//                                 });
//
//
//
//
//
//          }
//      }
//    });
//
//    }
//
//}
