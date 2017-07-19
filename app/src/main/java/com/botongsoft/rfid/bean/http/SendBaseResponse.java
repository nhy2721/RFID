package com.botongsoft.rfid.bean.http;

public class SendBaseResponse {
	public String code;
	public int page;
	public int total;
	public int records;
	public String msg;
	public String delrecords;
	public String rows;

	public String phoneNum="";
	public String userName="";
	public String name="";
	public String idCardNo="";
	public String age="";
	public String address="";

	public String sex="";
	public String email="";

	public boolean isSuccess() {
		return ("0".equals(code)) ? true : false;
	}

}
