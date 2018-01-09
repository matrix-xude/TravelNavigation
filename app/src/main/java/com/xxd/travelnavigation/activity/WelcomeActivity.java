package com.xxd.travelnavigation.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.xxd.travelnavigation.R;
import com.xxd.travelnavigation.application.MyApplication;
import com.xxd.travelnavigation.utils.ActivityUtil;
import com.xxd.travelnavigation.utils.rxtool.RxDataTool;
import com.xxd.travelnavigation.utils.rxtool.RxPermissionsTool;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends CommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<String> needPermissions = RxPermissionsTool.with(this)
                .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .initPermission();
        if (RxDataTool.isEmpty(needPermissions)) {  // 没有需要初始化的权限
            Flowable.fromArray(1)
                    .map(new Function<Integer, Integer>() {
                        @Override
                        public Integer apply(Integer integer) throws Exception {
                            Thread.sleep(2000);
                            return integer;
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            ActivityUtil.skipActivity(WelcomeActivity.this, MainActivity.class);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ActivityUtil.skipActivity(WelcomeActivity.this, MainActivity.class);
                        }
                    });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    MyApplication.finishAllActivity();
                }
            }
            ActivityUtil.skipActivity(this, MainActivity.class);
        }
    }
}
