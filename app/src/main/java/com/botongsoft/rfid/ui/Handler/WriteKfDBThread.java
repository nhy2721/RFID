package com.botongsoft.rfid.ui.Handler;

import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.List;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteKfDBThread extends Thread {
    private List<Kf> objList = null;

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
                DBDataUtils.update(kfOld);
            } else {
                kf.setStatus(9);
                DBDataUtils.save(kf);
            }
        }
    }
}
