package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETMJG_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteMjgDBThread extends Thread {
    private List<Mjjg> objList = null;
    private List<Mjjg> saveList = new ArrayList<Mjjg>();
    private List<Mjjg> newList = new ArrayList<Mjjg>();
    private Mjjg mjjgOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteMjgDBThread(Handler mhandler, Message uiMsg) {
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
            Mjjg mjjg = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("mjg", i+1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETMJG_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            mjjgOld = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjg.getId() + "");
            if (mjjgOld != null) {
                mjjgOld.setAnchor(mjjg.getAnchor());
                mjjgOld.setId(mjjg.getId());
                mjjgOld.setCs(mjjg.getCs());
                mjjgOld.setMc(mjjg.getMc());
                mjjgOld.setZs(mjjg.getZs());
                mjjgOld.setCfsl(mjjg.getCfsl());
                mjjgOld.setMjjid(mjjg.getMjjid());
                mjjgOld.setZy(mjjg.getZy());
                mjjgOld.setStatus(9);
                saveList.add(mjjgOld);
            } else {
                mjjg.setStatus(9);
                newList.add(mjjg);
            }
        }
        //        for (Mjjg mjjg : objList) {
        //            mjjgOld = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjg.getId() + "");
        //            if (mjjgOld != null) {
        //                mjjgOld.setAnchor(mjjg.getAnchor());
        //                mjjgOld.setId(mjjg.getId());
        //                mjjgOld.setCs(mjjg.getCs());
        //                mjjgOld.setMc(mjjg.getMc());
        //                mjjgOld.setZs(mjjg.getZs());
        //                mjjgOld.setCfsl(mjjg.getCfsl());
        //                mjjgOld.setMjjid(mjjg.getMjjid());
        //                mjjgOld.setZy(mjjg.getZy());
        //                mjjgOld.setStatus(9);
        //                saveList.add(mjjgOld);
        //            } else {
        //                mjjg.setStatus(9);
        //                newList.add(mjjg);
        //            }
        //        }
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
//        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
