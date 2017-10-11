package com.botongsoft.rfid.common.db;

import com.botongsoft.rfid.bean.classity.LogDetail;
import com.botongsoft.rfid.bean.classity.LogMain;
import com.botongsoft.rfid.bean.classity.Mjjgda;

import static com.botongsoft.rfid.common.utils.TimeUtils.getCurrentTimeAll;

/**
 * Created by pc on 2017/10/9.
 */

public class LogDbHelper {
    private static final int LX_SJ_PHONE = 26;
    private static final int LX_XJ_PHONE = 27;

    public static int retUpMainId() {
        LogMain logMain = new LogMain();
        logMain.setStatus(0);
        logMain.setLx(LX_SJ_PHONE);
        logMain.setSj(getCurrentTimeAll());
        DBDataUtils.save(logMain);
        return logMain.getLid();
    }
    public static void addUpDetail(int mainID,Mjjgda mjjgda) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLlogid(mainID);
        logDetail.setStatus(0);
        logDetail.setBm(mjjgda.getBm());
        logDetail.setJlid(Integer.valueOf(mjjgda.getJlid()));
        DBDataUtils.save(logDetail);

    }
    public static int retDownMainId() {
        LogMain logMain = new LogMain();
        logMain.setStatus(0);
        logMain.setLx(LX_XJ_PHONE);
        logMain.setSj(getCurrentTimeAll());
        DBDataUtils.save(logMain);
        return logMain.getLid();
    }
}
