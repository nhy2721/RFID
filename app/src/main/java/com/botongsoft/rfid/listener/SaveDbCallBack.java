package com.botongsoft.rfid.listener;

/**
 * Created by pc on 2017/7/7.
 */

public interface SaveDbCallBack {
    /**
     * 回调函数
     *
     * @param result
     */
    public void call(String result);

    public void saveSuccess(String result);

    public void saveError(String result);
}
