package com.botongsoft.rfid.common.utils;

import android.os.Handler;
import android.widget.Toast;

import com.botongsoft.rfid.BaseApplication;

import static com.botongsoft.rfid.ui.activity.BaseActivity.activity;

/**
 * Author   :hymane
 * Email    :hymanme@163.com
 * Create at 2016-12-26
 * Description:
 */
public class ToastUtils {
    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG).show();
    }
     /* 显示toast，自己定义显示长短。
      * param1:activity  传入context
    * param2:word   我们需要显示的toast的内容
    * param3:time length  long类型，我们传入的时间长度（如500）*/

    public static void showToast(final String word, final long time){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(BaseApplication.getApplication(), word, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, time);
            }
        });
    }
}
