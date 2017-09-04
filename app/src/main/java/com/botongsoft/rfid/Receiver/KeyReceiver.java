package com.botongsoft.rfid.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Switch;

import com.handheld.UHFLonger.UHFLongerManager;

/**
 * Created by pc on 2017/9/1.
 */

public class KeyReceiver extends BroadcastReceiver {
    private String TAG = "KeyReceiver";
    private UHFLongerManager manager = null; //
    private boolean startFlag = false;
    Switch mSwitch;
    public KeyReceiver(UHFLongerManager manager, boolean startFlag, Switch mSwitch) {
        this.manager = manager;
        this.startFlag = startFlag;
        this.mSwitch = mSwitch;
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
//                    Scan();
                    break;
                case KeyEvent.KEYCODE_F2://按键F2
//                    Scan();
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
