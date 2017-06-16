
package com.botongsoft.rfid.common.service.http;

public class BusinessException extends Exception {

    private static final long serialVersionUID = -7340725172187075793L;

    /**
     * 无法连接服务器
     */
    public static final int CODE_UNREACH_SESSION = -97;
    /**
     * 无法连接服务器
     */
    public static final int CODE_UNREACH_SERVER = -96;
    /**
     * 平台信息
     */
    public static final int CODE_SERVIECE_MSG = -100;

    /**
     * 返回结果不是合法的json格式
     */
    public static final int CODE_ILLEGAL_RETURN = -1;
    /**
     * 返回结果解密出错
     */
    public static final int CODE_DECODER_RETURN = -3;
    /**
     * 暂无数据
     */
    public static final int CODE_NO_DATA = -7;

    private int mCode;

    private String mMessage;

    public BusinessException(int code) {
        mCode = code;
    }

    public BusinessException(int code, String msg) {
        mCode = code;
        mMessage = msg;
    }

    public int getCode() {
        return mCode;
    }

    /**
     * 获取实际错误信息
     * 
     * @return
     */
    public String getRealMessage() {
        switch (mCode) {
            case CODE_UNREACH_SERVER:
                return "无法连接服务器";
            case CODE_ILLEGAL_RETURN:
                return "返回结果不是合法的json格式";
            case CODE_SERVIECE_MSG:
            	return mMessage;
        }
        return "未知错误";
    }

    /**
     * 获取错误信息，展示给用户
     */
    @Override
    public String getMessage() {

        switch (mCode) {
            case CODE_UNREACH_SERVER:
                return "服务超时，请稍后重试";
            case CODE_ILLEGAL_RETURN:
                return "请求数据异常";
            case CODE_NO_DATA:
            	return "暂无相关数据";
            case CODE_SERVIECE_MSG:
            	return mMessage;
        }
        return "暂无相关数据";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BusinessException result:").append(mCode).append("  desc:")
                .append(getRealMessage());
        return sb.toString();
    }

    @Override
    public void printStackTrace() {

        System.err.println(toString());
    }

}
