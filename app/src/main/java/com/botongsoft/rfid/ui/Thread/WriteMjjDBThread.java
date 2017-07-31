package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETMJJ_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteMjjDBThread extends Thread {
    private List<Mjj> objList = null;
    private List<Mjj> saveList = new ArrayList<Mjj>();
    private List<Mjj> newList = new ArrayList<Mjj>();
    private Handler mhandler;
    private Message uiMsg;
    public void setList(List list) {
        this.objList = list;
    }
    public WriteMjjDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }
    @Override
    public void run() {
        int size =objList.size();
        for (int i = 0; i < size; i++) {
            Mjj mjj = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("mjj",i+1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETMJJ_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            Mjj mjjOld = (Mjj) DBDataUtils.getInfo(Mjj.class, "id", mjj.getId() + "");
                        if (mjjOld != null) {
                            mjjOld.setAnchor(mjj.getAnchor());
                            mjjOld.setCs(mjj.getCs());
                            mjjOld.setId(mjj.getId());
                            mjjOld.setNoright(mjj.getNoright());
                            mjjOld.setNoleft(mjj.getNoleft());
                            mjjOld.setZlbq(mjj.getZlbq());
                            mjjOld.setYlbq(mjj.getYlbq());
                            mjjOld.setZs(mjj.getZs());
                            mjjOld.setMc(mjj.getMc());
                            mjjOld.setKfid(mjj.getKfid());
                            mjjOld.setBz(mjj.getBz());
                            mjjOld.setStatus(9);
                            saveList.add(mjjOld);
                        } else {
                            mjj.setStatus(9);
                            newList.add(mjj);
                        }
        }
//        for (Mjj mjj : objList) {
//            Mjj mjjOld = (Mjj) DBDataUtils.getInfo(Mjj.class, "id", mjj.getId() + "");
//            if (mjjOld != null) {
//                mjjOld.setAnchor(mjj.getAnchor());
//                mjjOld.setCs(mjj.getCs());
//                mjjOld.setId(mjj.getId());
//                mjjOld.setNoright(mjj.getNoright());
//                mjjOld.setNoleft(mjj.getNoleft());
//                mjjOld.setZlbq(mjj.getZlbq());
//                mjjOld.setYlbq(mjj.getYlbq());
//                mjjOld.setZs(mjj.getZs());
//                mjjOld.setMc(mjj.getMc());
//                mjjOld.setKfid(mjj.getKfid());
//                mjjOld.setBz(mjj.getBz());
//                mjjOld.setStatus(9);
//                saveList.add(mjjOld);
//            } else {
//                mjj.setStatus(9);
//                newList.add(mjj);
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
