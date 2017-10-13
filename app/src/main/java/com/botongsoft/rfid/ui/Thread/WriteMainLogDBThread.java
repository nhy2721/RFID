package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.bean.classity.LogMain;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_PUTMAINLOG_SUCCESS_PB;
import static com.botongsoft.rfid.common.utils.ListUtils.isEmpty;

/**
 * 根据服务器传回的日志主记录更新日志主记录表，以及日志明细表。之后在上传日志明细记录
 * Created by pc on 2017/7/19.
 */

public class WriteMainLogDBThread extends Thread {
    private List<LogMain> objList = null;
    private List<LogMain> saveList = new ArrayList<LogMain>();
    private List<LogDetail> detailList = new ArrayList<LogDetail>();
    private LogMain logMainOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteMainLogDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        int size = objList.size();
        int tempId=0;
        for (int i = 0; i < size; i++) {
            LogMain logMain = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("log", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_PUTMAINLOG_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            logMainOld = (LogMain) DBDataUtils.getInfo(LogMain.class, "lid", logMain.getLid() + "");
            if (logMainOld != null) {
                logMainOld.setId(logMain.getId());
                logMainOld.setStatus(logMain.getStatus());
                saveList.add(logMainOld);
                //根据服务器返回的记录更新明细表的服务器主键id;
                if (tempId != logMainOld.getLid()) {
                    tempId = logMainOld.getLid();
                    List<LogDetail> list = (List<LogDetail>) DBDataUtils.getInfosHasOp(LogDetail.class, "llogid", "=", String.valueOf(logMainOld.getLid()));
                    if(!isEmpty(list)){
                        for (LogDetail logDetail : list) {
                            logDetail.setLogid(logMain.getId());
                        }
                        detailList.addAll(list);
                    }
                }
            }

        }


        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
        if (detailList != null && !detailList.isEmpty()) {
            DBDataUtils.updateAll(detailList);
        }

        //        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
