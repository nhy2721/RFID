package com.botongsoft.rfid.ui.Thread;

import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteMjjDBThread extends Thread {
    private List<Mjj> objList = null;
    private List<Mjj> saveList = new ArrayList<Mjj>();
    private List<Mjj> newList = new ArrayList<Mjj>();

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        for (Mjj mjj : objList) {
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
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
    }
}
