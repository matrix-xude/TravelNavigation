package com.xxd.travelnavigation.application;


import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xxd.travelnavigation.BuildConfig;

/**
 * Created by xxd on 2018/1/9.
 */

public class MyApplication extends ActivityManagerApplication {

//    private static Application mInstance;
//
//    public static Application getInstance() {
//        return mInstance;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mInstance = this;

        // 初始化logger类,配置可以定制自己想要的log
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("MyTag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG; // 只有debug版本才打印log
            }
        });
    }
}
