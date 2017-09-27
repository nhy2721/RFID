package com.botongsoft.rfid.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.widget.Switch;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.common.utils.ToastUtils;
import com.handheld.UHFLonger.UHFLongerManager;

/**
 * Created by pc on 2017/9/1.
 */

public class KeyReceiver extends BroadcastReceiver {
    private String TAG = "KeyReceiver";
    private UHFLongerManager manager = null; //
    private boolean startFlag = false;
    Switch mSwitch;
    private int value;
    public KeyReceiver(UHFLongerManager manager, boolean startFlag, Switch mSwitch) {
        this.manager = manager;
        this.startFlag = startFlag;
        this.mSwitch = mSwitch;
        getSharedValue();
    }

    public KeyReceiver(UHFLongerManager manager, boolean startFlag) {
        this.manager = manager;
        this.startFlag = startFlag;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        int keyCode = intent.getIntExtra("keyCode", 0);
        boolean keyDown = intent.getBooleanExtra("keydown", false);
        //			Log.e("down", ""+keyDown);
        if (keyDown) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_F1: //按键F1
                    getSharedValue();
                    sub();
                    break;
                case KeyEvent.KEYCODE_F2://按键F2
                    getSharedValue();
                    add();
                    break;
                case KeyEvent.KEYCODE_F3:  //手枪按钮
                    Scan();
                    break;
                case KeyEvent.KEYCODE_F4:
//                    Scan();
                    break;
                case KeyEvent.KEYCODE_F5:
//                    Scan();
                    break;
            }
        }
    }
    private int getSharedValue() {
        SharedPreferences sp = BaseApplication.application.getSharedPreferences("power", 0);
          value = sp.getInt("value", 0);
        if (value == 0) {
            value = 30;
        }
        return  value;
    }
    //save Value
    private void saveSharedValue(int value){
        SharedPreferences shared = BaseApplication.application.getSharedPreferences("power", 0);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("value", value);
        editor.commit();
    }
    private void add() {
        if(value < 30){
            value = value + 1;
        }else {
            value = 5;
        }
        if(manager.setOutPower((short) value)){
            saveSharedValue(value);
            ToastUtils.showToast("增大功率为："+value,500);
        }else{
            ToastUtils.showToast("更改功率保存失败,请先关闭扫描功能",500);
        }
    }

    private void sub() {
        if(value > 5){
            value = value - 1;
        }else {
            value = 30;
        }
        if(manager.setOutPower((short) value)){
            saveSharedValue(value);
            ToastUtils.showToast("减小功率为："+value,500);
        }else{
            ToastUtils.showToast("更改功率保存失败,请先关闭扫描功能",500);
        }
    }

    private void Scan() {
        if (manager == null) return;
        if (!startFlag) {
            startFlag = true;
            mSwitch.setChecked(true);
        } else {
            startFlag = false;
            mSwitch.setChecked(false);
        }

    }
}
