package com.botongsoft.rfid.common.db;

import com.botongsoft.rfid.BaseApplication;
import com.lidroid.xutils.DbUtils;

public class DataBaseCreator {
	public static DbUtils create() {
//		com_yt_payee_bean_classity_PayOrderInfo
		DbUtils db = DbUtils.create(BaseApplication.context,
				BaseApplication.DBNAMESTRING, BaseApplication.DBVERSION, null);
//		 DbUtils db = DbUtils.create(ApplicationApp.context);
		//开启事务
		db.configAllowTransaction(true);
		db.configDebug(false);
		return db;
	}
}
