package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETKF_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteKfDBThread extends Thread {
    private List<Kf> objList = null;
    private List<Kf> saveList = new ArrayList<Kf>();
    private List<Kf> newList = new ArrayList<Kf>();
    private Handler mhandler;
    private Message uiMsg;

    public void setList(List list) {
        this.objList = list;
    }

    public WriteKfDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    @Override
    public void run() {
        for (int i = 0; i < objList.size(); i++) {
            Kf kf = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("kf", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETKF_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            Kf kfOld = (Kf) DBDataUtils.getInfo(Kf.class, "id", kf.getId() + "");
            if (kfOld != null) {
                kfOld.setQzh(kf.getQzh());
                kfOld.setBz(kf.getBz());
                kfOld.setMc(kf.getMc());
                kfOld.setId(kf.getId());
                kfOld.setAnchor(kf.getAnchor());
                kfOld.setStatus(9);
                saveList.add(kfOld);
            } else {
                kf.setStatus(9);
                newList.add(kf);
            }
        }
        //        for (Kf kf : objList) {
        //            Kf kfOld = (Kf) DBDataUtils.getInfo(Kf.class, "id", kf.getId() + "");
        //            if (kfOld != null) {
        //                kfOld.setQzh(kf.getQzh());
        //                kfOld.setBz(kf.getBz());
        //                kfOld.setMc(kf.getMc());
        //                kfOld.setId(kf.getId());
        //                kfOld.setAnchor(kf.getAnchor());
        //                kfOld.setStatus(9);
        //                saveList.add(kfOld);
        //            } else {
        //                kf.setStatus(9);
        //                newList.add(kf);
        //            }
        //        }
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
    }
}
