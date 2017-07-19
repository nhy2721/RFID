package com.botongsoft.rfid.ui.Handler;

import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.List;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteMjgDBThread extends Thread {
    private List<Mjjg> objList = null;

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (Mjjg mjjg : objList) {
            Mjjg mjjgOld = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjg.getId() + "");
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
                DBDataUtils.update(mjjgOld);
            } else {
                mjjg.setStatus(9);
                DBDataUtils.save(mjjg);
            }
        }
    }
}
