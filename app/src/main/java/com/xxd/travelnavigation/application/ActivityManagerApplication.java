package com.xxd.travelnavigation.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by xxd on 2018/1/9.
 * <p>
 * 全局处理activity生命周期管理，不再需要手动添加activity管理类
 * <p>
 * 自Android 4.0（API-14）开始，Application 中多了一个方法，可以设置全局监听Activity的生命周期。
 * 这个方法就是：registerActivityLifecycleCallbacks（ActivityLifecycleCallbacks activityLifecycleCallbacks）
 * 在传入的参数 activityLifecycleCallbacks 能得到全局所有Activity 生命周期的回调，
 * 因此 我们可以从Application中全局监听所有Activity 并对其进行管理，再也不用在Activity中处理那些扯淡的逻辑
 */

public class ActivityManagerApplication extends Application {


    /**
     * 维护Activity 的list
     */
    private static List<Activity> mActivitys = Collections.synchronizedList(new LinkedList<Activity>());

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityListener();
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
        Logger.d("pushActivity:%s, size:%d", activity.getClass().getSimpleName(), mActivitys.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        mActivitys.remove(activity);
        Logger.d("popActivity:%s, size:%d", activity.getClass().getSimpleName(), mActivitys.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return null;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivitys.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivitys != null) {
            for (Activity activity : mActivitys) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        mActivitys.clear();
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        try {
//            LogUtils.e("app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    private void registerActivityListener() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {  // 必须要添加监听，version 14以上才能使用
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                /**
                 *  监听到 Activity创建事件 将该 Activity 加入list
                 */
                pushActivity(activity);

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (null == mActivitys || mActivitys.isEmpty()) {
                    return;
                }
                if (mActivitys.contains(activity)) {
                    /**
                     *  监听到 Activity销毁事件 将该Activity 从list中移除
                     */
                    popActivity(activity);
                }
            }
        });
    }
//    }
}
