package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETCHECKDETAIL_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteCheckDetailDBDelThread extends Thread {
    private List<CheckPlanDeatilDel> objList = null;
    private List<CheckPlanDeatilDel> saveList = new ArrayList<CheckPlanDeatilDel>();
    private List<CheckPlanDeatilDel> newList = new ArrayList<CheckPlanDeatilDel>();
    private CheckPlanDeatilDel mCheckPlanDeatilDelOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteCheckDetailDBDelThread(Handler mhandler, Message uiMsg) {
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
            CheckPlanDeatilDel CheckPlanDeatilDel = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("checkdetaildel", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETCHECKDETAIL_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);

        }
        DBDataUtils.deleteInfos(CheckPlanDeatilDel.class);
    }
}
