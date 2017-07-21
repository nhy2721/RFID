package com.botongsoft.rfid.ui.Handler;

import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteKfDBThread extends Thread {
    private List<Kf> objList = null;
    private List<Kf> saveList = new ArrayList<Kf>();
    private List<Kf> newList = new ArrayList<Kf>();

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (Kf kf : objList) {
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
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
    }
}
