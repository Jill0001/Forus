package com.example.bzdell.forus.Login;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bzdell.forus.MainActivity;
import com.example.bzdell.forus.R;
import com.example.bzdell.forus.Utils.EmailCheck;
import com.example.bzdell.forus.Utils.LoadUtils;
import com.example.bzdell.forus.Utils.Timer1;

import java.util.TimerTask;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class login extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private SharedPreferences sp;
    private Dialog mLoad;
    private int clickTime = 0;
    private int times = -1;
    private java.util.Timer timer = null;
    private static final int REQUESTCODE = 101;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeDialog(mLoad);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);
        test();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        final Button btn = (Button) findViewById(R.id.login3);
        final EditText et_user = (EditText) findViewById(R.id.login1);
        final EditText et_pwd = (EditText) findViewById(R.id.login2);
        final CheckBox cb1 = (CheckBox) findViewById(R.id.login5);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.login6);


        Bmob.initialize(this, "bfb519d77e9049fbf4c475c3c55cc49d");
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);
        BmobPushManager  bmobPushManager = new BmobPushManager();

        Button btn1 = (Button) findViewById(R.id.login4);


        btn1.setOnClickListener(new Button.OnClickListener() {  //注册按钮功能
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {    //登录按钮功能
            @Override
            public void onClick(View v) {
                final String username = et_user.getText().toString();
                final String pwd = et_pwd.getText().toString();
                final UserDate bu2 = new UserDate();
                if (username.equals("") || pwd.equals("")) {
                    Toast.makeText(login.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                }


                bu2.setUsername(username);
                bu2.setPassword(pwd);

                if (!EmailCheck.isValidEmail(username)) {
                    mLoad = LoadUtils.createLoadingDialog(login.this, "登录中...");
                    mHandler.sendEmptyMessageDelayed(1, 10000);

                    bu2.login(new SaveListener<UserDate>() {


                                  @Override
                                  public void done(UserDate u, BmobException e) {

                                      mLoad.dismiss();

                                      if (e == null) {

                                          if (cb1.isChecked()) {
                                              SharedPreferences.Editor editor = sp.edit();
                                              editor.putString("USERNAME", username);
                                              editor.putString("PASSWORD", pwd);
                                              editor.commit();
                                          }

                                          if (bu2.getEmailVerified()) {


                                              startActivity(new Intent(login.this, MainActivity.class));
                                              finish();

                                          } else {
                                              Toast.makeText(login.this, "邮箱未验证，请前新用户注册界面验证邮箱", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                      if (e.getErrorCode() == 9016) {
                                          Toast.makeText(login.this, "登录失败，当前网络不可以用", Toast.LENGTH_SHORT).show();

                                      }
                                      if (e.getErrorCode() == 101) {
                                          cb2.setChecked(false);
                                          Toast.makeText(login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              }
                    );
                } else {
                    mLoad = LoadUtils.createLoadingDialog(login.this, "登录中...");
                    mHandler.sendEmptyMessageDelayed(1, 2000);
                    if (cb1.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USERNAME", username);
                        editor.putString("PASSWORD", pwd);
                        editor.commit();
                    }

                    bu2.loginByAccount(username, pwd, new LogInListener<UserDate>() {

                        @Override
                        public void done(UserDate user, BmobException e) {
                            if (user != null) {
                                mLoad.dismiss();
                                startActivity(new Intent(login.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(login.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

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
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb1.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("CHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("CHECK", false).commit();

                }

            }
        });

        if (sp.getBoolean("CHECK", false)) {     //记住密码和自动登录功能
            cb1.setChecked(true);
            et_user.setText(sp.getString("USERNAME", ""));
            et_pwd.setText(sp.getString("PASSWORD", ""));

            if (sp.getBoolean("ACHECK", false)) {
//                cb2.setChecked(true);
                if (MainActivity.getCurrentUser() != null) {
                    startActivity(new Intent(login.this, MainActivity.class));
                    finish();
                }
            }
        }
    }

    public void signup2(View v) {

        TextView button = (TextView) findViewById(R.id.login7);

        Timer1 timer1 = new Timer1(button, 30000, 1000);
        timer1.start();

        final EditText editText = new EditText(login.this);
        new AlertDialog.Builder(login.this)
                .setTitle("发送重置密码邮件，请输入你的邮箱")
                .setView(editText)
                .setNegativeButton("取消", null)
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String mail = editText.getText().toString();
                        if (EmailCheck.isValidEmail(mail)) {

                            UserDate.resetPasswordByEmail(mail, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(login.this, "发送成功，请到" + mail + "邮箱中修改密码", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                        } else {
                            Toast.makeText(login.this, "邮箱格式不正确，请输入正确的邮箱", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();

    }  //邮箱密码重置

    public void onBackPressed() {    //按两次返回退出程序

        clickTime = clickTime + 1;

        if (clickTime == 1 && timer == null) {
            Toast.makeText(login.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            timer = new java.util.Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    times = times + 1;
                    if (times == 2) {
                        clickTime = 0;
                        times = -1;
                        timer.cancel();
                        timer = null;
                    }
                }
            }, 0, 1000);
        } else if (clickTime == 2) {
            if (timer != null) {
                timer.cancel();
                timer = null;
                super.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }   //按两次返回退出程序

    public static void Hidden(EditText v, ImageButton s) {         //设置密码可见
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            s.setImageResource(R.drawable.ic_lock_outline);


        } else {

            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            s.setImageResource(R.drawable.ic_lock_open);
        }

    }   //设置密码可见

    public void mimas(View view) {
        EditText et_pwd = (EditText) findViewById(R.id.login2);
        ImageButton ibtn = (ImageButton) findViewById(R.id.imageButton);
        Hidden(et_pwd, ibtn);
    }  //设置密码可见

    public void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }    //

    public void showMissingPermissionDialog() {

        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUESTCODE) {
            if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {

            }
            else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("上传头像需要读写手机存储，不赋予权限将无法正常工作！")
                        .setPositiveButton("前往授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })

                        .setCancelable(false)
                        .setNegativeButton("取消授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
//                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FFDB0F19"));
//                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#03a9f4"));
                dialog.show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }   //权限回调信息

    private void test() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int has = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (has != PackageManager.PERMISSION_GRANTED) {

//                String carrier = android.os.Build.MANUFACTURER;

//                if (!carrier.equals("Xiaomi")) {
//                    Toast toast = Toast.makeText(this, "此权限用于加载头像，请允许，若点击拒绝请自行到手机权限管理中允许权限", Toast.LENGTH_LONG);
//                    toast.setGravity(GravityCompat.START, 70, 0);
//                    showmytoast.showMyToast(toast, 5 * 1000);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
//                }
                AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), getPackageName());

                if (checkOp == AppOpsManager.MODE_IGNORED) {
                    ActivityCompat.requestPermissions(login.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
                    return;


                }
            }


        }


    }  //申请权限
}