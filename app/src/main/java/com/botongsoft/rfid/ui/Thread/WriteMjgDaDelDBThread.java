package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.bean.classity.MjjgdaDelInfos;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_PUTDA_SUCCESS_PB;


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
        int size = objList.size();
        for (int i = 0; i < size; i++) {
            Mjjgda mjjgdaDelInfo = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("dadel", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_PUTDA_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            if(mjjgdaDelInfo.getId()==0){
                DBDataUtils.deleteInfo(Mjjgda.class, "bm", String.valueOf(mjjgdaDelInfo.getBm()),
                        "jlid", String.valueOf(mjjgdaDelInfo.getJlid()),
                        "id", "=", 0 + "");
            }else{
                DBDataUtils.deleteInfo(Mjjgda.class, "bm", String.valueOf(mjjgdaDelInfo.getBm()),
                        "jlid", String.valueOf(mjjgdaDelInfo.getJlid()),
                        "id", "=", mjjgdaDelInfo.getId()+"");
            }

        }

        //        if (saveList != null && !saveList.isEmpty()) {
        ////            DBDataUtils.updateAll(saveList);
        //            DBDataUtils.delList(saveList);
        //        }
        //        mhandler.obtainMessage(Constant.BackThread_GETDADEL_SUCCESS).sendToTarget();

        //        mhandler.obtainMessage(Constant.BackThread_GETDA_SUCCESS).sendToTarget();
    }
}
