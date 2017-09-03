package com.example.bzdell.forus;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Jack on 2017/9/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

}
