package com.botongsoft.rfid.bean;

import android.text.TextUtils;

import com.botongsoft.rfid.BaseApplication;
import com.botongsoft.rfid.bean.http.SendBaseResponse;
import com.botongsoft.rfid.common.constants.Constant;
import com.botongsoft.rfid.common.ShareManager;


/**
 * 用户本地数据类
 * 
 * @author Administrator
 */
public class LocalUserData {

	/*** 签到时间 *****/
	// private String login_date = "";

	private static LocalUserData instant;

	private SendBaseResponse userInfo;

	private LocalUserData() {

	}

	public static LocalUserData getInstance() {
		if (instant == null) {
			instant = new LocalUserData();
		}
		return instant;
	}

	public SendBaseResponse getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(SendBaseResponse userInfo) {
		this.userInfo = userInfo;
	}

	public boolean isUserLoginState() {
		String user_id = ShareManager.getValueWithDefValue(
				BaseApplication.context, Constant.ACCOUNT_ID, "");
		if (TextUtils.isEmpty(user_id)) {
			return false;
		} else {
			return true;

		}
	}

}
