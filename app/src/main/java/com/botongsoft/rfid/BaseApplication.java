package com.botongsoft.rfid;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.botongsoft.rfid.crash.CrashCatchHandler;
import com.botongsoft.rfid.ui.activity.BaseActivity;
import com.handheld.UHFLonger.UHFLongerManager;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * Application
 */
public class BaseApplication extends Application {
    public static Context context;
    public final static String TAG = "BaseApplication";
    public static String DBNAMESTRING = "kfgl.db";
    public static int DBVERSION = 1;
    public final static boolean DEBUG = true;
//    private static BaseApplication application;
    private static int mainTid;
    public static BaseApplication application;
    private UHFLongerManager manager = null; //
    /**
     * Activity集合，来管理所有的Activity
     */
    private static List<BaseActivity> activities;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new LinkedList<>();
        application = this;
        mainTid = android.os.Process.myTid();
        this.context = getApplicationContext();
        try {
            manager =UHFLongerManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CrashCatchHandler.getInstance().init(getApplicationContext());
    }
    public UHFLongerManager getmanager() {
        return manager;
    }
    public void setmanager(UHFLongerManager manager) {
        this.manager = manager;
    }
    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return application;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities() {
        ListIterator<BaseActivity> iterator = activities.listIterator();
        BaseActivity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        clearActivities();
        System.exit(0);
    }
}
