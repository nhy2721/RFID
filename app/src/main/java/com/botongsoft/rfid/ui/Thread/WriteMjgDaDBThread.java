package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETDA_SUCCESS_PB;
import static com.botongsoft.rfid.common.constants.Constant.BackThread_PUTDA_SUCCESS_PB;


/**
 * 档案线程
 * Created by pc on 2017/7/19.
 */

public class WriteMjgDaDBThread extends Thread {
    private List<Mjjgda> objList = null;
    private List<Mjjgda> saveList = new ArrayList<Mjjgda>();
    private List<Mjjgda> newList = new ArrayList<Mjjgda>();
    private Mjjgda mjjgdaOld;
    private Handler mhandler;
    private Message uiMsg;
    private int itype;

    public WriteMjgDaDBThread(Handler mhandler, Message uiMsg, int type) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
        this.itype = type;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        int size = objList.size();
        for (int i = 0; i < size; i++) {
            Mjjgda mjjgda = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("da", i + 1);
            uiMsg.setData(b);
            if (itype == 0) {
                uiMsg.what = BackThread_GETDA_SUCCESS_PB;
            } else {
                uiMsg.what = BackThread_PUTDA_SUCCESS_PB;
            }
            mhandler.sendMessage(uiMsg);
            mjjgdaOld = (Mjjgda) DBDataUtils.getInfo(Mjjgda.class, "bm", mjjgda.getBm() + "",
                    "jlid", mjjgda.getJlid() + "");
            if (mjjgdaOld != null) {
//                if (mjjgdaOld.getStatus() == -1 && mjjgda.getAnchor() > mjjgdaOld.getAnchor()) {//碰到服务器有更新，本地有做下架并且上架操作的要删除掉新上架的位置，以服务器的版本为准
//                    DBDataUtils.deleteInfo(Mjjgda.class, "bm", String.valueOf(mjjgdaOld.getBm()),
//                            "jlid", String.valueOf(mjjgdaOld.getJlid()),
//                            "id", "=", 0 + "");
//                }
                mjjgdaOld.setAnchor(mjjgda.getAnchor());
                mjjgdaOld.setId(mjjgda.getId());
                mjjgdaOld.setMjjid(mjjgda.getMjjid());
                mjjgdaOld.setXh(mjjgda.getXh());
                mjjgdaOld.setFlag(mjjgda.getFlag());
                mjjgdaOld.setKfid(mjjgda.getKfid());
                mjjgdaOld.setBm(mjjgda.getBm());
                mjjgdaOld.setMjgid(mjjgda.getMjgid());
                mjjgdaOld.setJlid(mjjgda.getJlid());
                mjjgdaOld.setStatus(9);
                saveList.add(mjjgdaOld);
            } else {
                mjjgda.setStatus(9);
                newList.add(mjjgda);
            }
        }
        //        for (Mjjgda mjjgda : objList) {
        //            uiMsg = mhandler.obtainMessage();
        //            Bundle b = new Bundle();
        //            b.putInt("da",objList.size()+1);
        //            uiMsg.setData(b);
        //            uiMsg.what = BackThread_GETDA_SUCCESS_PB;
        //            mhandler.sendMessage(uiMsg);
        //            mjjgdaOld = (Mjjgda) DBDataUtils.getInfo(Mjjgda.class, "bm", mjjgda.getBm() + "",
        //                    "jlid", mjjgda.getJlid() + "");
        //            if (mjjgdaOld != null) {
        //                mjjgdaOld.setAnchor(mjjgda.getAnchor());
        //                mjjgdaOld.setId(mjjgda.getId());
        //                mjjgdaOld.setMjjid(mjjgda.getMjjid());
        //                mjjgdaOld.setXh(mjjgda.getXh());
        //                mjjgdaOld.setFlag(mjjgda.getFlag());
        //                mjjgdaOld.setKfid(mjjgda.getKfid());
        //                mjjgdaOld.setBm(mjjgda.getBm());
        //                mjjgdaOld.setMjgid(mjjgda.getMjgid());
        //                mjjgdaOld.setJlid(mjjgda.getJlid());
        //                mjjgdaOld.setStatus(9);
        //                saveList.add(mjjgdaOld);
        //            } else {
        //                mjjgda.setStatus(9);
        //                newList.add(mjjgda);
        //            }
        //        }
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
        //        mhandler.obtainMessage(Constant.BackThread_GETDA_SUCCESS).sendToTarget();
    }
}
