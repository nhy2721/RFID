package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETEPC_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteEpcDBThread extends Thread {
    private List<Epc> objList = null;
    private List<Epc> saveList = new ArrayList<Epc>();
    private List<Epc> newList = new ArrayList<Epc>();
    private Epc epcOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteEpcDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        int size =objList.size();
        for (int i = 0; i < size; i++) {
            Epc epc = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("epc", i+1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETEPC_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
           epcOld  = (Epc) DBDataUtils.getInfo(Epc.class, "epccode", epc.getEpccode() + "");
            if (epcOld != null) {
                epcOld.setAnchor(epc.getAnchor());
                epcOld.setArchiveno(epc.getArchiveno());
                epcOld.setBm(epc.getBm());
                epcOld.setEpccode(epc.getEpccode());
                epcOld.setJlid(epc.getJlid());
                epcOld.setZtcode(epc.getZtcode());
                epcOld.setStatus(9);
                saveList.add(epcOld);
            } else {
                epc.setStatus(9);
                newList.add(epc);
            }
        }

        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
//        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
