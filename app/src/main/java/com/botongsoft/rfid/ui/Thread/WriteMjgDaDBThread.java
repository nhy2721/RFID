package com.botongsoft.rfid.ui.Thread;

import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETDA_SUCCESS;


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

    public WriteMjgDaDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (Mjjgda mjjgda : objList) {
            uiMsg = mhandler.obtainMessage();
            uiMsg.arg1 =  objList.size()-1;
            uiMsg.what = BackThread_GETDA_SUCCESS;
            mhandler.sendMessage(uiMsg);
            mjjgdaOld = (Mjjgda) DBDataUtils.getInfo(Mjjgda.class, "bm", mjjgda.getBm() + "",
                    "jlid", mjjgda.getJlid() + "");
            if (mjjgdaOld != null) {
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
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
//        mhandler.obtainMessage(Constant.BackThread_GETDA_SUCCESS).sendToTarget();
    }
}
