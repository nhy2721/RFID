package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.CheckPlanDeatil;
import com.botongsoft.rfid.bean.classity.CheckPlanDeatilDel;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETCHECKDETAIL_SUCCESS_PB;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteCheckDetailDBThread extends Thread {
    private List<CheckPlanDeatil> objList = null;
    private List<CheckPlanDeatil> saveList = new ArrayList<CheckPlanDeatil>();
    private List<CheckPlanDeatil> newList = new ArrayList<CheckPlanDeatil>();
    private CheckPlanDeatil mCheckPlanDeatilOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteCheckDetailDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < objList.size(); i++) {
            CheckPlanDeatil mCheckPlanDeatil = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("checkdetail", i + 1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETCHECKDETAIL_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            mCheckPlanDeatilOld = (CheckPlanDeatil) DBDataUtils.getInfo(CheckPlanDeatil.class,
                    "pdid", String.valueOf(mCheckPlanDeatil.getPdid()),
                    "zy", String.valueOf(mCheckPlanDeatil.getZy()),
                    "kfid", String.valueOf(mCheckPlanDeatil.getKfid()),
                    "mjgid", String.valueOf(mCheckPlanDeatil.getMjgid()),
                    "mjjid", String.valueOf(mCheckPlanDeatil.getMjjid()),
                    "bm", String.valueOf(mCheckPlanDeatil.getBm()),
                    "jlid", String.valueOf(mCheckPlanDeatil.getJlid()));
            if (mCheckPlanDeatilOld != null) {
                mCheckPlanDeatilOld.setAnchor(mCheckPlanDeatil.getAnchor());
                mCheckPlanDeatilOld.setId(mCheckPlanDeatil.getId());
                mCheckPlanDeatilOld.setPdid(mCheckPlanDeatil.getPdid());
                mCheckPlanDeatilOld.setStatus(9);
                saveList.add(mCheckPlanDeatilOld);
            } else {
                mCheckPlanDeatil.setStatus(9);
                newList.add(mCheckPlanDeatil);
            }
        }
        //        for (CheckPlanDeatil mCheckPlanDeatil : objList) {
        //            mCheckPlanDeatilOld = (CheckPlanDeatil) DBDataUtils.getInfo(CheckPlanDeatil.class, "pdid", String.valueOf(mCheckPlanDeatil.getPdid()),
        //                    "zy", String.valueOf(mCheckPlanDeatil.getZy()),
        //                    "kfid", String.valueOf(mCheckPlanDeatil.getKfid()),
        //                    "mjgid", String.valueOf(mCheckPlanDeatil.getMjgid()),
        //                    "mjjid", String.valueOf(mCheckPlanDeatil.getMjjid()),
        //                    "bm", String.valueOf(mCheckPlanDeatil.getBm()),
        //                    "jlid", String.valueOf(mCheckPlanDeatil.getJlid()));
        //            if (mCheckPlanDeatilOld != null) {
        //                mCheckPlanDeatilOld.setAnchor(mCheckPlanDeatil.getAnchor());
        //                mCheckPlanDeatilOld.setId(mCheckPlanDeatil.getId());
        //                mCheckPlanDeatilOld.setPdid(mCheckPlanDeatil.getPdid());
        //                mCheckPlanDeatilOld.setStatus(9);
        //                saveList.add(mCheckPlanDeatilOld);
        //            } else {
        //                mCheckPlanDeatil.setStatus(9);
        //                newList.add(mCheckPlanDeatil);
        //            }
        //        }
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
        DBDataUtils.deleteInfos(CheckPlanDeatilDel.class);
//        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
