package com.example.bzdell.forus.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.R;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInfo extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private ImageView mImageHeader;
    private SharedPreferences sp,sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button baocun=(Button)findViewById(R.id.button4) ;
        final EditText ename= (EditText)findViewById(R.id.editText1);
        final EditText enickname= (EditText)findViewById(R.id.editText10);
        final EditText eqq= (EditText)findViewById(R.id.editText8);
        final EditText eschool= (EditText)findViewById(R.id.editText5);
        final EditText exueyuan= (EditText)findViewById(R.id.editText6);
        final EditText exuehao= (EditText)findViewById(R.id.editText7);
        final EditText ezhuanye= (EditText)findViewById(R.id.editText15);
        final EditText ephone= (EditText)findViewById(R.id.editText4);
        final EditText emingzu=(EditText)findViewById(R.id.editText3) ;
        final EditText email=(EditText)findViewById(R.id.editText9);
        final EditText eshengri=(EditText)findViewById(R.id.editText2) ;

        sp=this.getSharedPreferences("touxiang", Context.MODE_PRIVATE);
        sb=this.getSharedPreferences("duibi",Context.MODE_PRIVATE);

        mImageHeader=(ImageView)findViewById(R.id.imageView);

        String aname=(String)UserDate.getObjectByKey("name") ;
        String anickname=(String)UserDate.getObjectByKey("nickname") ;
        String aschool=(String)UserDate.getObjectByKey("school")   ;
        String axueyuan=(String)UserDate.getObjectByKey("xueyuan")   ;
        String axuehao=(String)UserDate.getObjectByKey("xuehao")   ;
        String azhuanye=(String)UserDate.getObjectByKey("zhuanye")   ;
        String aphone=(String)UserDate.getObjectByKey("phone")  ;
        String aqq=(String)UserDate.getObjectByKey("qq")  ;
        String amignzu=(String)UserDate.getObjectByKey("mingzu") ;
        String amail = (String)UserDate.getObjectByKey("email") ;
        String ashengri=(String)UserDate.getObjectByKey("shengri");

        enickname.setText(anickname);
        eqq.setText(aqq);
        eschool.setText(aschool);
        exueyuan.setText(axueyuan);
        ezhuanye.setText(azhuanye);
        exuehao.setText(axuehao);
        ename.setText(aname);
        ephone.setText(aphone);
        emingzu.setText(amignzu);
        email.setText(amail);
        eshengri.setText(ashengri);

        String x= sp.getString("uri","");
        String y= sb.getString("uri","");
        String z=sp.getString("path","");
        BmobFile bmobfile =new BmobFile("head.png","",x);

      if(!y.equals(x)) {
          downloadFile(bmobfile);
          SharedPreferences.Editor editor =sb.edit() ;         //将地址保存，进行对比
          editor.putString("uri",x);
          editor.commit();
      }

//        Glide.with(this).load(x).into(mImageHeader);
        Glide.with(this).load(z).centerCrop().signature( new StringSignature("01")).into(mImageHeader);//uri加载方式

        baocun.setOnClickListener(new  View.OnClickListener()  {          //保存按钮功能

            @Override
            public  void  onClick(View  v)  {
                UserDate newUser=new UserDate();
                BmobUser bmobUser = BmobUser.getCurrentUser();
                final String name=ename.getText().toString();
                final String nickname=enickname.getText().toString();
                final String school=eschool .getText().toString();
                final String  xueyuan=exueyuan .getText().toString();
                final String  xuehao=exuehao .getText().toString();
                final String  zhuanye=ezhuanye .getText().toString();
                final String  phone=ephone .getText().toString();
                final String  qq=eqq.getText().toString();
                final String mingzu=emingzu.getText().toString();
                final String shengri = eshengri.getText().toString();

                newUser.setNickname(nickname);
                newUser.setName(name);
                newUser.setSchool(school);
                newUser.setXueyuan(xueyuan);
                newUser.setXuehao(xuehao);
                newUser.setZhuanye(zhuanye);
                newUser.setPhone(phone);
                newUser.setQq(qq);
                newUser.setMingzu(mingzu);
                newUser.setShengri(shengri);

                newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(UserInfo.this,"保存成功",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(UserInfo.this,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    });


    }

    public  void touxiang(final View v){
//        String[] items =new  String[] {"相册"};
//        new AlertDialog.Builder(UserInfo.this).setTitle("").setNegativeButton("取消" ,null ).setItems(items,new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface a, int which) {
//                switch (which) {
//                    case 0:
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        galleryIntent.setType("image/*");//图片
                        startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
//                        break;
//                    case 1:
//                            Intent cameraIntent = new Intent(
//                                    "android.media.action.IMAGE_CAPTURE");//拍照
//                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
//                            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//                            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//                        break;
//                }
//            }
//        }).show();



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {


                case IMAGE_REQUEST_CODE:
                    Uri originalUri=data.getData();//获取图片uri
                    resizeImage(originalUri);


                    //下面方法将获取的uri转为String类型哦！
                    String []imgs1={MediaStore.Images.Media.DATA};        //将图片URI转换成存储路径
                    Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);
                    int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String img_url=cursor.getString(index);


                    Glide.with(this).load(img_url).centerCrop().into(mImageHeader);//uri加载方式

//                    Glide.with(this).load(img_url).into(mImageHeader);//uri加载方式
                   BmobFile icon = new BmobFile(new File(img_url));
                    final BmobFile icon1=icon;

                    icon.upload(new UploadFileListener() {                //头像上传
                        @Override
                        public void done(BmobException e) {
                            BmobUser bmobUser = BmobUser.getCurrentUser();
                            if(e==null){

                                UserDate person = new UserDate();
                            person.setIcon(icon1);
                            person.update(bmobUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {               //头像属性添加到用户
                                    if(e==null){
                                        Toast.makeText(UserInfo.this,"保存成功",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(UserInfo.this,"保存失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                                SharedPreferences.Editor editor =sp.edit() ;         //将地址保存，以便下载头像
                                editor.putString("uri",icon1.getFileUrl());
                                editor.commit();

                            }
                            else {
                                Toast.makeText(UserInfo.this, "上传失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //showToast(img_url);
                    break;
//                case CAMERA_REQUEST_CODE:
//                    if (isSdcardExisting()) {
//                        resizeImage(getImageUri());
//                        String []imgs={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
//                        Cursor cursor1=this.managedQuery(getImageUri(), imgs, null, null, null);
//                        int index1=cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        cursor1.moveToFirst();
//                        String img_url1=cursor1.getString(index1);
//                        icon = new BmobFile();
//                        icon.setUrl(img_url1);
//                        icon.upload(new UploadFileListener() {
//                            @Override
//                            public void done(BmobException e) {
//
//                            }
//                        });
//
//
//                    }
//                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {

                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

//    private boolean isSdcardExisting() {//判断SD卡是否存在
//        final String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private Uri getImageUri() {//获取路径
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                toast("开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    toast("下载成功,保存路径:"+savePath);
                    SharedPreferences.Editor editor =sp.edit() ;
                    editor.putString("path",savePath);
                    editor.commit();
                }else{
                    toast("下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    private void toast(String s) {
        Toast.makeText(UserInfo.this,s,Toast.LENGTH_SHORT).show();
    }


}








