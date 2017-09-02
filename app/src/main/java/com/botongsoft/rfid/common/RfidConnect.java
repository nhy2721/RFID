package com.botongsoft.rfid.common;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.botongsoft.rfid.R;
//import com.botongsoft.rfid.ui.activity.MainActivity;
//import com.handheld.UHFLonger.UHFLongerManager;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import static com.botongsoft.rfid.common.utils.UIUtils.startActivity;
//
///**
// * Created by pc on 2017/9/1.
// */
//
public class RfidConnect   {
//    /*
//     * 实例域
//     */
//    private static final String TAG = "Connect";        // log TAG
//
//    private ProgressBar mProgressBar;                           // 连接时显示
//    private TextView mConnectModuText;                          // 连接时显示的文字
//    private LinearLayout mLinearLayout;                         // Button所在layout，控制是否显示按钮
//    private Button mReconnectButton;                            // 重连按钮
//    private Button mExitButton;                                 // 退出按钮
//    private static UHFLongerManager manager  ;                           // UHF对象
//
//    private Handler mHandler;                                   // UI线程的消息处理handler
//    private Timer mTimer;                                       // 定时，其实是为了显示一下ProgressBar
//
//
//        //定时
////        mTimer = new Timer();
////        timerConnect();
//
//
//    /*
//     * 模块初始化
//     */
//    private boolean initiaModu() {
//        try {
//            manager = UHFLongerManager.getInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(manager == null) {
//            return false;
//        }
//
//        //休眠一下，估计是硬件初始化的需求
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            Log.e(TAG, "Thread sleep error");
//            manager = null;
//            return false;
//        }
//
//        //获取模块功率参数
//        int power = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
//                .getInt("POWER", 26);
//        Log.i(TAG, "Get the power " + power);
//        mReader.setOutputPower(power);
//
//        //连接
//        byte[] versionBytes = mReader.getFirmware();
//        if(versionBytes == null) {
//            mReader = null;
//            return false;
//        }
//        String version = new String(versionBytes);
//        Log.i(TAG, "version : " + version);
//        return true;
//    }
//
//    /*
//     * 返回Reader
//     */
//    public static UHFLongerManager getUhfmanager() {
//        return manager;
//    }
//
//    /*
//     * 定时重连，延时300ms
//     */
//    private void timerConnect() {
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //初始化模块信息发送
//                Message msg = mHandler.obtainMessage();
//                msg.what = 0;
//                mHandler.sendMessage(msg);
//            }
//        }, 300);
//    }
//
//    /*
//     * 初始化Setting参数，只在第一次启动此app时生效
//     */
//    private void initiaParameters() {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        // FIRST为第一次启动的标志，若是第一次则为空，否则为first
//        if (sp.getString(SettingActivity.FIRST,"").equals("")) {
//            sp.edit()
//                    .putInt(SettingActivity.POWER, SettingActivity.MAXPOWER)
//                    .putString(SettingActivity.URL, SettingActivity.DEFAULT_URL)
//                    .putString(SettingActivity.VEHICLE_ID, SettingActivity.DEFAULT_VEHICLE_ID)
//                    .putString(SettingActivity.VEHICLE_TYPE, SettingActivity.TYPE_CAR)
//                    .putString(SettingActivity.ACTION, SettingActivity.ACTION_LOAD)
//                    .putString(SettingActivity.FIRST, SettingActivity.FIRST)
//                    .commit();
//        }
//    }
}
