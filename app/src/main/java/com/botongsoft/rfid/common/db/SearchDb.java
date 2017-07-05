package com.botongsoft.rfid.common.db;

import com.botongsoft.rfid.bean.classity.Mjj;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;


public class SearchDb {

    /**
     * 根据传入的范围返回该次盘点的密集架
     */
    public static Object getMjjList(String fw) {
        List<Mjj> mjjList = new ArrayList<Mjj>();
        String[] srrArray = fw.split(",");
        Integer kfid = Integer.valueOf(srrArray[0]);
        if (kfid == 0) {
            //库房id为0 查询所有库房的密集架
            mjjList = (List) DBDataUtils.getInfos(Mjj.class);
            for (Mjj mjj : mjjList) {
                mjj.setShowLeft(true);
                mjj.setShowRrigh(true);
            }
        } else if (kfid != 0) {
            Integer mjjid = Integer.valueOf(srrArray[1]);
            if (mjjid == 0) {
                //所有密集架
                DbUtils db = DataBaseCreator.create();
                try {
                    mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid));
                    for (Mjj mjj : mjjList) {
                        mjj.setShowLeft(true);
                        mjj.setShowRrigh(true);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            } else if (mjjid != 0) {
                // "密集架面";
                Integer m = Integer.valueOf(srrArray[2]);
                if (m == 0) {
                    //"所有面";
                    DbUtils db = DataBaseCreator.create();
                    try {
                        mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid));
                        for (Mjj mjj : mjjList) {
                            mjj.setShowLeft(true);
                            mjj.setShowRrigh(true);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else if (m == 1) {
                    // "左面";
                    DbUtils db = DataBaseCreator.create();
                    try {
                        mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid).and("noleft", "=", 0));
                        for (Mjj mjj : mjjList) {
                            mjj.setShowLeft(true);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                } else if (m == 2) {
                    // "右面";
                    DbUtils db = DataBaseCreator.create();
                    try {
                        mjjList = db.findAll(Selector.from(Mjj.class).where("kfid", "=", kfid).and("id", "=", mjjid).and("noright", "=", 0));
                        for (Mjj mjj : mjjList) {
                            mjj.setShowRrigh(true);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mjjList;

    }


}
