package com.botongsoft.rfid.common.db;

import android.os.Environment;

import com.botongsoft.rfid.BaseApplication;
import com.lidroid.xutils.DbUtils;

public class DataBaseCreator {

    //    private static String PATH = Environment
    //            .getExternalStorageDirectory().toString() + "/com.botongsoft.rfid/";
    private static String PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/com.botongsoft.rfid/";

    public static DbUtils create() {
        //        boolean isSdcardEnable = false;
        //        String state = Environment.getExternalStorageState();
        //        if (Environment.MEDIA_MOUNTED.equals(state)) {//SDCard是否插入
        //            isSdcardEnable = true;
        //        }
        //        if(isSdcardEnable){
        //            File dbp = new File(PATH);
        //            if(!dbp.exists()){
        //                dbp.mkdirs();
        //            }
        //        }

        DbUtils db = DbUtils.create(BaseApplication.context, PATH,
                BaseApplication.DBNAMESTRING, BaseApplication.DBVERSION, null);
        //		 DbUtils db = DbUtils.create(ApplicationApp.context);
        //开启事务
        db.configAllowTransaction(true);
        db.configDebug(false);
        return db;
    }
}
