package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_PUTDETAILLOG_SUCCESS_PB;

/**
 * 根据服务器传回的日志主记录更新日志主记录表，以及日志明细表。之后在上传日志明细记录
 * Created by pc on 2017/7/19.
 */

public class WriteDetailLogDBThread extends Thread {
    private List<LogDetail> objList = null;
    private List<LogDetail> saveList = new ArrayList<LogDetail>();

    private LogDetail logDetailOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteDetailLogDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        int size = objList.size();
        for (int i = 0; i < size; i++) {
            LogDetail logDetail = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("detaillog", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_PUTDETAILLOG_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            logDetailOld = (LogDetail) DBDataUtils.getInfo(LogDetail.class, "lid", logDetail.getLid() + "");
            if (logDetailOld != null) {
                logDetailOld.setStatus(9);
                saveList.add(logDetail);
            }

        }


        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }

        //        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
