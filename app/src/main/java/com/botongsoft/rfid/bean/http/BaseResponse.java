package com.botongsoft.rfid.bean.http;

public class BaseResponse {

	public SendBaseResponse res;

	public boolean isSuccess() {
		if (res != null) {
			return res.isSuccess();
		} else {
			return false;
		}
	}

}
