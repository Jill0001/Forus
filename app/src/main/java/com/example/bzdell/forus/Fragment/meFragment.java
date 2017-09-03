package com.example.bzdell.forus.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bzdell.forus.Login.UserDate;
import com.example.bzdell.forus.Login.UserInfo;
import com.example.bzdell.forus.R;
import com.example.bzdell.forus.me.Settings.Setting;
import com.example.bzdell.forus.me.mylove.mylove;
import com.example.bzdell.forus.me.mynotice.mynotice;
import com.example.bzdell.forus.me.renzheng.renzheng;
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

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;


/**
 * Created by Jack on 2017/8/22.
 */

public class meFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentActivity mContext;
    private static final int REQUESTCODE = 101;
    private FrameLayout layout;
    private TextView ea, eb;
    private ImageView ec;
    private LinearLayout ed, ee,ef,eg;
    private SharedPreferences sp, sb;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();

        File cacheDir = StorageUtils.getOwnCacheDirectory(getActivity(), "imageloader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getActivity())
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





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);



        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe1);
        initData();
//        new Thread(runnable).start();
        return view;
    }

    public static meFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        meFragment fragment = new meFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public  void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe1);
        ea = (TextView) getActivity().findViewById(R.id.textView);
        eb = (TextView) getActivity().findViewById(R.id.textView1);
        layout = (FrameLayout) getActivity().findViewById(R.id.LinearLayout1);
        ec = (ImageView) getActivity().findViewById(R.id.iv_head);
        ed = (LinearLayout) getActivity().findViewById(R.id.settings);
        ee = (LinearLayout) getActivity().findViewById(R.id.renzheng);
        ef = (LinearLayout) getActivity().findViewById(R.id.mynotice);
        eg = (LinearLayout) getActivity().findViewById(R.id.shoucang);
        String a = (String) UserDate.getObjectByKey("nickname");
        String b = (String) UserDate.getObjectByKey("school");

        sp = getActivity().getSharedPreferences("touxiang", Context.MODE_PRIVATE);
        sb = getActivity().getSharedPreferences("duibi", Context.MODE_PRIVATE);


//        int ci=sp.getInt("cishu",0);
        String y = sb.getString("uri", "");
        String z = (String) UserDate.getObjectByKey("uri");




//        if (z != null) {
//
//            if (!y.equals(z)) {

        String zha =   z + "!/sq/50";

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.appinfo)            //加载图片时的图片
                        .showImageForEmptyUri(R.drawable.appinfo)         //没有图片资源时的默认图片
                        .showImageOnFail(R.drawable.appinfo)              //加载失败时的图片
                        .cacheInMemory(true)                               //启用内存缓存
                        .cacheOnDisk(true)                                 //启用外存缓存
//                        .considerExifParams(true)                          //启用EXIF和JPEG图像格式
//                        .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                        .build();

                ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

                ImageLoader imageLoader = ImageLoader.getInstance();

                imageLoader.displayImage(zha, ec, options, animateFirstListener);

//        imageLoader.getInstance().loadImage(zha,new SimpleImageLoadingListener(){
//
//
//
//
//
//            @Override
//            public void onLoadingComplete(String imageUri,View view,Bitmap loadedImage){
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                ec.setImageBitmap(loadedImage);
//            }
//        });

//                SharedPreferences.Editor editor = sb.edit();
//                editor.putString("uri", z);
//                editor.commit();

////                String x = sp.getString("path", "");
//
////                if (!x.equals("")) {
//                try {
//                    ec.setImageBitmap(getBitmap(zha));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
////                }
////                BmobFile bmobfile = new BmobFile("head.png", "", zha);
////                downloadFile(bmobfile);
//

//            }
//        }
//




        ea.setText(a);
        eb.setText(b);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfo.class);
                startActivity(intent);
            }
        });

        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), renzheng.class);
                startActivity(intent);
            }
        });

        ef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), mynotice.class);
                startActivity(intent);
            }
        });

        eg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), mylove.class);
                startActivity(intent);
            }
        });

    }

    public void downloadFile(BmobFile file) {

        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onProgress(Integer integer, long l) {

            }

            @Override
            public void onStart() {
//                Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath, BmobException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "下载成功"+savePath, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("path", savePath);
                    editor.commit();
                } else {
                    Toast.makeText(getActivity(), "下载失败" + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }

            }

        });
    }   //下载头像

    public void initData() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);

    }   //下拉刷新监视器

    @Override
    public void onRefresh() {


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.appinfo)            //加载图片时的图片
                .showImageForEmptyUri(R.drawable.appinfo)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.appinfo)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
//                        .considerExifParams(true)                          //启用EXIF和JPEG图像格式
//                        .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                .build();

        ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

        String z = (String) UserDate.getObjectByKey("uri");
        String zha = z + "!/sq/50";
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(zha, ec, options, animateFirstListener);

//        String x = sp.getString("path", "");
//        String y = sb.getString("uri", "");
//        String z = (String) UserDate.getObjectByKey("uri");
//        if (z != null) {
//            if (!y.equals(z)) {
//
//                String zha1 = z + "!/sq/50";
//
//                BmobFile bmobfile = new BmobFile("head.png", "", zha1);
//
//                downloadFile(bmobfile);
//
//                SharedPreferences.Editor editor = sb.edit();
//                editor.putString("uri", z);
//                editor.commit();
//            }
//        }
        String a = (String) UserDate.getObjectByKey("nickname");
        String b = (String) UserDate.getObjectByKey("school");
//        Bitmap bitmap = BitmapFactory.decodeFile(x);
//        ec.setImageBitmap(bitmap);
        ea.setText(a);
        eb.setText(b);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
//        MainActivity activity= (MainActivity) getActivity();
//        activity.reLoadFragView();


            }
        }, 2000);
    }    //下拉刷新功能

//    public static Bitmap getBitmap(String path) throws IOException {
//
//        URL url = new URL(path);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(10000);
//        conn.setRequestMethod("GET");
//
//        if (conn.getResponseCode() == 200){
//            InputStream inputStream = conn.getInputStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            return bitmap;
//        }
//else{
//            Log.d("lyf---","FAIL:"+conn.getResponseCode());
//        }
//
//        return null;
//    }

//   Handler handler =new Handler(){
//       @Override
//       public void handleMessage(Message msg){
//           super.handleMessage(msg);
//           Bundle data = msg.getData();
//           String val =data.getString("value");
//       }
//   };
//
//   Runnable runnable =new Runnable() {
//       @Override
//       public void run() {
//           String zha ="http://bmob-cdn-13732.b0.upaiyun.com/2017/09/01/00fe6e657d854ad58bb54dc78a2b6e52.png"  ;
//           Bitmap bitmap = null;
//           try {
//               bitmap = getBitmap(zha);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//
//        if(bitmap!=null)
//           ec.setImageBitmap(bitmap);
//
//
//           Message msg =new Message();
//           Bundle data =new Bundle();
//           data.putString("value","请求结果");
//           msg.setData(data);
//           handler.sendMessage(msg);
//       }
//   };

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

