package com.example.bzdell.forus.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bzdell.forus.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;



public class UserInfo extends AppCompatActivity implements OnRequestPermissionsResultCallback{
    private static final int REQUESTCODE = 101;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private ImageView mImageHeader;
    private SharedPreferences sp,sb;
    private  int ci;


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


        File cacheDir = StorageUtils.getOwnCacheDirectory(UserInfo.this, "imageloader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(UserInfo.this)
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);


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



        String x= (String)UserDate.getObjectByKey("uri") ;

//        String y= sb.getString("uri","");
//
//        String z=sp.getString("path","");

//        int ci =sp.getInt("cishu",0);

//        if(ci>100000){
//            ci=10;
//        }
//
//
//        SharedPreferences.Editor editor1 =sp.edit() ;
//        editor1.putInt("cishu",++ci);
//        editor1.commit();

        String s=x+"!/sq/50";

//        BmobFile bmobfile =new BmobFile("head.png","",s);

//      if(x!=null) {
//        if (!y.equals(x)) {
//            downloadFile(bmobfile);
//            SharedPreferences.Editor editor = sb.edit();         //将地址保存，进行对比
//            editor.putString("uri", x);
//            editor.commit();
//        }
//      }






        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.appinfo)            //加载图片时的图片
                .showImageForEmptyUri(R.drawable.appinfo)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.appinfo)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
//                        .considerExifParams(true)                          //启用EXIF和JPEG图像格式
//                        .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                .build();

        ImageLoadingListener animateFirstListener = new UserInfo.AnimateFirstDisplayListener();

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(s, mImageHeader, options, animateFirstListener);









//        Bitmap bitmap = BitmapFactory.decodeFile(z);        //加载图片
//        mImageHeader.setImageBitmap(bitmap);


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
          test();
          Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
          galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
          galleryIntent.setType("image/*");//图片
          startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {

                case IMAGE_REQUEST_CODE:
                    Uri originalUri=data.getData();
                    resizeImage(originalUri);

//                    String []imgs1={MediaStore.Images.Media.DATA};

//                    Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);

//                    int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();

//                    String img_url=cursor.getString(index);
//
//                    upload(img_url);

                    break;
                case RESIZE_REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    Bundle extras= data.getExtras();
                    if(extras!=null){
                        Bitmap photo =extras .getParcelable("data");
                        ByteArrayOutputStream stream =new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG,20,stream);
                        File fimage=new File(Environment.getExternalStorageDirectory(),"headforus.png");
                        try {
//                            fimage.createNewFile();
                            FileOutputStream iStream = new FileOutputStream(fimage);
                            photo.compress(Bitmap.CompressFormat.PNG, 100, iStream);
                            iStream.flush();
                            iStream.close();
                        }
                            catch(Exception e){
                                e.printStackTrace();
                            }


                      mImageHeader.setImageBitmap(photo);
                        upload(Environment.getExternalStorageDirectory()+"/headforus.png");
                        }

                        break;
                        default:
                        break;
                    }
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

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

     private void showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            mImageHeader.setImageDrawable(drawable);



        }
    }

    public  void downloadFile(BmobFile file){

        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"

        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());

        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
//                toast("开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
//                    toast("修改成功");
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

    public void upload(String imgpath){


        String xxx=(String)UserDate.getObjectByKey("uri");
        BmobFile file = new BmobFile();
        if(!xxx.equals("")) {                                                //删除上次的头像
            file.setUrl(xxx);
            file.delete(new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("文件删除成功");
                    } else {
                        toast("文件删除失败：" + e.getErrorCode() + "," + e.getMessage());
                    }
                }
            });
        }


        String paths=Environment.getExternalStorageDirectory()+"/headforus.png";
        BmobFile icon = new BmobFile(new File(paths));

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






//                    BmobFile bmobfile =new BmobFile("head.png","",icon1.getFileUrl()+"!/sq/50");     //下载头像
//
//                    downloadFile(bmobfile);
//                    SharedPreferences.Editor editor2 =sb.edit() ;         //将地址保存，进行对比
//                    editor2.putString("uri",icon1.getFileUrl());
//                    editor2.commit();

//                    Bitmap bitmap = BitmapFactory.decodeFile(icon1.getFileUrl());        //加载图片
//                    mImageHeader.setImageBitmap(bitmap);


                    UserDate URL= new UserDate();             //上传URL以便下载
                    URL.setUri(icon1.getFileUrl());
                    URL.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });

                }
                else {
                    Toast.makeText(UserInfo.this, "修改失败", Toast.LENGTH_LONG).show();
                }

            }
        });

        }

    @Override
    public void onRequestPermissionsResult(int requestCode,String []permissions,int[]grantResults){

        if(requestCode == REQUESTCODE){
            if(permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                test();
            }else {
                Toast.makeText(getApplicationContext(),"xxx",Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    private  void test(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int has =checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(has != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUESTCODE );
                return;
            }
        }


    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}








