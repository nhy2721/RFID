package com.botongsoft.rfid.ui.Thread;

import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 档案线程
 * Created by pc on 2017/7/19.
 */

public class WriteMjgDaDelDBThread extends Thread {
    private List<MjjgdaDelInfos> objList = null;
    private List<MjjgdaDelInfos> saveList = new ArrayList<MjjgdaDelInfos>();
//    private List<MjjgdaDelInfos> newList = new ArrayList<MjjgdaDelInfos>();
    private MjjgdaDelInfos mjjgdaOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteMjgDaDelDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (MjjgdaDelInfos mjjgdaDelInfo : objList) {
            mjjgdaOld = (MjjgdaDelInfos) DBDataUtils.getInfo(MjjgdaDelInfos.class, "bm", mjjgdaDelInfo.getBm() + "", "jlid", mjjgdaDelInfo.getJlid() + "");
            if (mjjgdaOld != null) {

                mjjgdaOld.setStatus(9);
                saveList.add(mjjgdaOld);
            }
        }

        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
//        mhandler.obtainMessage(Constant.BackThread_GETDADEL_SUCCESS).sendToTarget();
        mhandler.obtainMessage(Constant.BackThread_GETDA_SUCCESS).sendToTarget();
    }
}
