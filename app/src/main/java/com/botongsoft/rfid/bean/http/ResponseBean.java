package com.botongsoft.rfid.bean.http;


import java.util.Arrays;

/**
 * 
* 项目名称：netbank    
* 类名称：http数据返回实体    
* 类描述：暂无  
* 创建人：yb    
* 创建时间：2015年1月26日 上午10:16:56    
* @version
 */
public class ResponseBean {
	private String body = "";
	private byte[] sessionId = new byte[32];
	private short version = 1;
	private byte encryptionMethod = 0;
	private short act = 1;
	private int packageLength = 0;
	private byte[] reserve = new byte[23];
	private int errorCode = 0;
	private String sgin;

	public String getSgin() {
		return sgin;
	}

	public void setSgin(String sgin) {
		this.sgin = sgin;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public short getVersion() {
		return this.version;
	}

	public void setEncryptionMethod(byte encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	public byte getEncryptionMethod() {
		return this.encryptionMethod;
	}

	public void setAct(short act) {
		this.act = act;
	}

	public short getAct() {
		return this.act;
	}

	public void setPackageLength(int packageLength) {
		this.packageLength = packageLength;
	}

	public int getPackageLength() {
		return this.packageLength;
	}

	public byte[] getSessionId() {
		return sessionId;
	}

	public void setSessionId(byte[] sessionId) {
		this.sessionId = sessionId;
	}

	public void setReserve(byte[] reserve) {
		this.reserve = reserve;
	}

	public byte[] getReserve() {
		return reserve;
	}

	@Override
	public String toString() {
		return "ResponseBean [act=" + act + ", body=" + body
				+ ", encryptionMethod=" + encryptionMethod + ", errorCode="
				+ errorCode + ", packageLength=" + packageLength + ", reserve="
				+ Arrays.toString(reserve) + ", sessionId="
				+ Arrays.toString(sessionId) + ", sgin=" + sgin + ", version="
				+ version + "]";
	}
}
