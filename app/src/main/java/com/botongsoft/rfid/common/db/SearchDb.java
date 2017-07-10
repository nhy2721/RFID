package com.botongsoft.rfid.common.db;

import android.database.Cursor;

import com.botongsoft.rfid.bean.classity.Mjj;
import com.botongsoft.rfid.bean.classity.Mjjg;
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

    /**
     * 判
     * @param srrArray  盘点范围
     * @param mjjg       密集格
     * @return断该密集格是否在盘点范围
     */
    public static int countPdfw(String[] srrArray, Mjjg mjjg) {
        DbUtils db = DataBaseCreator.create();
        String sql = "select * from com_botongsoft_rfid_bean_classity_Mjjg where mjjid = (select id from (select * from com_botongsoft_rfid_bean_classity_Mjj";
        Integer kfid = Integer.valueOf(srrArray[0]);
        Integer mjjid = 0;
        Integer zy = 0;
        if (kfid != 0) {
            sql += " where kfid=" + kfid;
            mjjid = Integer.valueOf(srrArray[1]);
            if (mjjid != 0) {
                sql += " and id=" + mjjid;
                zy = Integer.valueOf(srrArray[2]);
            }
        }
        sql += ") as a where a.id=" + mjjg.getMjjid() + ") and id=" + mjjg.getId();
        if (kfid != 0) {
            if (mjjid != 0) {
                if (zy != 0) {
                    sql += " and zy =" + zy;
                }
            }
        }
        Cursor cursor = null; // 执行自定义sql
        try {
            cursor = (Cursor) db.execQuery(sql);
        } catch (DbException e) {
            e.printStackTrace();
        }
        int s = cursor.getCount();
        return s;
    }

}
