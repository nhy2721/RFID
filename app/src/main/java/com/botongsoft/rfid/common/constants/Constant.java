package com.botongsoft.rfid.common.constants;

/**
 * 公用的常用参数
 *
 * @author Administrator
 */
public class Constant {
    public static final String THEME_MODEL = "theme_model";
    /*** 服务器地址 **/
    public static String DOMAIN = "http://www.zzdanganguan.gov.cn/servlet/zzdajServlet";// ?clientType=0&message=
    public static String DOMAINTEST = "http://192.168.0.66:8086/servlet/appServlet";// ?clientType=0&message=

    /**
     * 数据编码
     */
    public final static String ENCODING = "UTF-8";// utf-8

    /**** RSA_KEY密钥 */
    public final static String RSA_KEY = "rsa_key";

    /**** 商户号 */
    public final static String USER_CODE = "user_code";

    /**** 返回设备终端号 */
    public final static String IDE_CODE = "ide_code";

    public static final String ACCOUNT_ID = "account_id";

    /**** 商户名 */
    public final static String USER_NAME = "user_name";

    /***
     * des 约定请求公钥
     */
    public final static String PUB_DES_KEY = "987654321012345678901224";

    /**
     * 平台
     */
    public static int OS = 1; // 平台

    /**
     * 手机型号
     */
    public static String MODEL = android.os.Build.MODEL; // 手机型号

    /**
     * 系统版本号
     */
    public static String OSVER = android.os.Build.VERSION.RELEASE; // 系统版本号

    /**
     * 当前软件版本
     */
    public static int SWVER_CODE = 1; // 当前软件版本,默认值为1

    /**
     * 用户名
     */
    public static final String ACCOUNT = "name";

    public static final String PASS = "pass";

    // public static final String GRADE_INFO = "grade";

    public static final String DEVICEID = "deviceID";

    // :1余额支付 2:智付支付 3:农行快捷支付 4:海康支付 5:第三方支付 6:和包支付

    /**
     * 业务密码登录时密钥
     */

    /**** 接口区分编号 ******/

    /**** 1注册 ******/
    public static final int ACT_REGISTER = 1;

    /**
     * 2 登录
     */
    public static final int ACT_LOGIN = 2;

    public static final int ACT_UPDATE_PWS = 30;


    /**** 1. 接口名称:扫码支付接口(payScan) ******/
    public static final int ACT_PAY_SCAN = 3;

    /**
     * 获取服务器表数据更新数
     */
    public static final int ACT_GET_WORDS = 9999;
//    public static final int ACT_GET_KF = 1000;
//    public static final int ACT_PUT_KF = 1002;
//    public static final int ACT_GET_MJJ = 1003;
//    public static final int ACT_GET_MJJG = 1004;
//    public static final int ACT_GET_MJJGDA = 1005;
//    public static final int ACT_PUT_MJJGDA = 1006;
    /**
     * 查询集合记录
     */
    public static final int BackThread_SUCCESS = 10000;
    public static final int BackThread_GETDA_SUCCESS = 10001;
    public static final int BackThread_GETDADEL_SUCCESS = 10002;
}
