package com.botongsoft.rfid.common.db;

import android.os.Environment;

import com.botongsoft.rfid.BaseApplication;
import com.lidroid.xutils.DbUtils;

public class DataBaseCreator {
	private static String PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()+"/com.botongsoft.rfid/";
	public static DbUtils create() {

		DbUtils db = DbUtils.create(BaseApplication.context,PATH,
				BaseApplication.DBNAMESTRING, BaseApplication.DBVERSION, null);
//		 DbUtils db = DbUtils.create(ApplicationApp.context);
		//开启事务
		db.configAllowTransaction(true);
		db.configDebug(false);
		return db;
	}
}
