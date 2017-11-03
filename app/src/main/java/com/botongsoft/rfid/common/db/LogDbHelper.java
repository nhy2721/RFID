package com.botongsoft.rfid.common.db;

import android.database.Cursor;

import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.bean.classity.LogMain;
import com.botongsoft.rfid.bean.classity.Mjjg;
import com.botongsoft.rfid.bean.classity.Mjjgda;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import static com.botongsoft.rfid.common.utils.TimeUtils.getCurrentTimeAll;

/**
 * Created by pc on 2017/10/9.
 */

public class LogDbHelper {
    private static final int LX_SJ_PHONE = 26;
    private static final int LX_XJ_PHONE = 27;

    /**
     * 返回上架日志主ID
     *
     * @return
     */
    public static int retUpMainId() {
        LogMain logMain = new LogMain();
        logMain.setStatus(0);
        logMain.setLx(LX_SJ_PHONE);
        logMain.setSj(getCurrentTimeAll());
        DBDataUtils.save(logMain);
        return getmainID();
    }

    /**
     * 返回下架日志主ID
     *
     * @return
     */
    public static int retDownMainId() {
        LogMain logMain = new LogMain();
        logMain.setStatus(0);
        logMain.setLx(LX_XJ_PHONE);
        logMain.setSj(getCurrentTimeAll());
        DBDataUtils.save(logMain);
        return getmainID();
    }

    public static void addUpDetail(int mainID, Mjjgda mjjgda) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLlogid(mainID);
        logDetail.setStatus(0);
        logDetail.setBm(mjjgda.getBm());
        logDetail.setJlid(Integer.valueOf(mjjgda.getJlid()));
        Mjjg mjjg = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjgda.getMjgid() + "");
        if (mjjg != null) {
            String wz = mjjgda.getKfid() + "," + mjjgda.getMjjid() + ","
                    + mjjg.getZy() + "," + mjjg.getCs() + "," + mjjg.getZs();
            logDetail.setNewcfwz(wz);
        }
        DBDataUtils.save(logDetail);

    }

    public static void addDownDetail(int mainID, Mjjgda mjjgda) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLlogid(mainID);
        logDetail.setStatus(0);
        logDetail.setBm(mjjgda.getBm());
        logDetail.setJlid(Integer.valueOf(mjjgda.getJlid()));
        Mjjg mjjg = (Mjjg) DBDataUtils.getInfo(Mjjg.class, "id", mjjgda.getMjgid() + "");
        if (mjjg != null) {
            String wz = mjjgda.getKfid() + "," + mjjgda.getMjjid() + ","
                    + mjjg.getZy() + "," + mjjg.getCs() + "," + mjjg.getZs();
            logDetail.setOldcfwz(wz);
        }
        DBDataUtils.save(logDetail);

    }

    /*
    * 根据传入的日志主ID计算出明细条目
    * */

    public static int countDetail(int mainId) {
        DbUtils db = DataBaseCreator.create();
        String sql1 = "select COUNT(*) as counts from com_botongsoft_rfid_bean_classity_LogDetail a where a.llogid= " + mainId;
        Cursor cursor = null; // 执行自定义sql
        int a = -1;
        try {
            cursor = (Cursor) db.execQuery(sql1);
            if (cursor.moveToFirst()) {
                a = cursor.getInt(0);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return a;
    }
    private static int getmainID() {
        DbUtils db = DataBaseCreator.create();
        String sql = "select last_insert_rowid() from com_botongsoft_rfid_bean_classity_LogMain";
        Cursor cursor = null; // 执行自定义sql
        int a = -1;
        try {
            cursor = (Cursor) db.execQuery(sql);
            if (cursor.moveToFirst()) {
                a = cursor.getInt(0);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return a;
    }

    private static int getmainID1() {
        LogMain lo = (LogMain) DBDataUtils.getInfoHasOp(LogMain.class, "lid", ">", "0");
        return lo.getLid();
    }

    public static Object getInfosHasOplimit(Class<?> entityType, String key, String op, String value, int limit) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).limit(limit));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static Object getInfosHasOplimit(Class<?> entityType, String key, String op, String value, String key1, String op1, String value1, int limit) {
        DbUtils db = DataBaseCreator.create();
        Object list = null;
        try {
            list = db.findAll(Selector.from(entityType).where(key, op, value).and(key1, op1, value1).limit(limit));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }
}
