package com.botongsoft.rfid.ui.Thread;

import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETDA_SUCCESS;


/**
 * 档案线程
 * Created by pc on 2017/7/19.
 */

public class WriteMjgDaDelDBThread extends Thread {
    private List<Mjjgda> objList = null;
    private List<Mjjgda> saveList = new ArrayList<Mjjgda>();
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
        for (Mjjgda mjjgdaDelInfo : objList) {
            //            mjjgdaOld = (MjjgdaDelInfos) DBDataUtils.getInfo(MjjgdaDelInfos.class, "bm", mjjgdaInfo.getBm() + "", "jlid", mjjgdaDelInfo.getJlid() + "");
            DBDataUtils.deleteInfo(Mjjgda.class, "bm", String.valueOf(mjjgdaDelInfo.getBm()),
                    "jlid", String.valueOf(mjjgdaDelInfo.getJlid()),
                    "id", "=", 0 + "");
            uiMsg = mhandler.obtainMessage();
            uiMsg.arg1 = objList.size() - 1;
            uiMsg.what = BackThread_GETDA_SUCCESS;
            mhandler.sendMessage(uiMsg);
            //            if (mjjgdaOld != null) {
            //
            //                mjjgdaOld.setStatus(9);
            //                saveList.add(mjjgdaOld);
            //            }
        }

        //        if (saveList != null && !saveList.isEmpty()) {
        ////            DBDataUtils.updateAll(saveList);
        //            DBDataUtils.delList(saveList);
        //        }
        //        mhandler.obtainMessage(Constant.BackThread_GETDADEL_SUCCESS).sendToTarget();

        //        mhandler.obtainMessage(Constant.BackThread_GETDA_SUCCESS).sendToTarget();
    }
}
