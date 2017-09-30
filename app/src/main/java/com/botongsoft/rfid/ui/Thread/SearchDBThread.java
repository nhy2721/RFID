package com.botongsoft.rfid.ui.Thread;

import android.app.Activity;

import com.botongsoft.rfid.bean.classity.Epc;
import com.botongsoft.rfid.bean.classity.Kf;
import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.botongsoft.rfid.common.db.DBDataUtils;
import com.botongsoft.rfid.common.db.MjgdaSearchDb;
import com.botongsoft.rfid.common.utils.ListUtils;
import com.botongsoft.rfid.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/7/19.
 */

public class SearchDBThread extends Thread {
    private String value;
    private Activity mContext;
    private ArrayList<Mjjgda> mList;

    public SearchDBThread(Activity mContext, String value) {
        this.mContext = mContext;
        this.value = value;
    }

    @Override
    public void run() {
        List<Epc> epcList = (List<Epc>) DBDataUtils.getInfosHasOp(Epc.class, "archiveno", "like", "%" + value + "%");
        Mjjgda mjjgda = null;
        Mjj mjj = null;
        Kf kf = null;
        String kfname = "";
        String mjjname = "";
        String nLOrR = "";
        if (!ListUtils.isEmpty(epcList)) {
            for (Epc ecp : epcList) {
                mjjgda = MjgdaSearchDb.getInfoHasOp(Mjjgda.class, "bm", "=", ecp.getBm() + "",
                        "jlid", "=", ecp.getJlid() + "", "status", "!=", "-1");// 只查不属于被删除的数据
                if (mjjgda != null) {
                    mjjgda.setTitle(ecp.getArchiveno());
                    mjjgda.setEpccode(String.valueOf(ecp.getEpccode()));
                    Mjjg mjjg = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjgda.getMjgid() + "");
                    if (mjjg != null) {
                        nLOrR = mjjg.getZy() == 1 ? "左" : "右";
                        mjj = (Mjj) DBDataUtils.getInfo(Mjj.class, "id", mjjg.getMjjid() + "");
                    }
                    if (mjj != null) {
                        mjjname = mjj.getMc() + "/";
                        kf = (Kf) DBDataUtils.getInfo(Kf.class, "id", mjj.getKfid() + "");
                    }

                    if (kf != null) {
                        kfname = kf.getMc() + "/";
                    }
                    String name = kfname + mjjname + nLOrR + "/" + mjjg.getZs() + "组" + mjjg.getCs() + "层";
                    //                        map.put("local", name);//界面显示存放位置
                    mjjgda.setScanInfo(name);
                    mList.add(mjjgda);
                }
            }
            if (ListUtils.isEmpty(mList)) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("档号关键字:" + value + "没查询到数据", 1000);
                    }
                });
            }
        }
    }

    public void setList(ArrayList<Mjjgda> mList) {
        this.mList = mList;
    }

    public ArrayList<Mjjgda> getmList() {
        return mList;
    }


}
