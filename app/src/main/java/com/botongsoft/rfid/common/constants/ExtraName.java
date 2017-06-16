package com.botongsoft.rfid.common.constants;

/**
 * intent传递的数据参数
 * 
 * @author 张文枫
 * @version 1.0
 * @time 2012-12-15
 */
public class ExtraName {

	/** webService服务超时时间 */
	public static final String CONVERSATIONINFO = "CONVERSATIONINFO";

	/** 切换的哪个tab */
	public static final String TAB_INDEX = "TAB_INDEX";

	/** 推荐套餐详情标签 */
	public static final String INTENT_BESTPRODUCT_KEY = "bestProduct";

	/** 是否显示订购按钮 */
	public final static String IS_SHOW_ORDER = "is_show_order";

	/** 用户已订购的套餐 */
	public static final String GPRS_USERPRODUCT_LIST = "gprs_userproduct_list";

	/** 用户是否已订购的套餐 true以有该套餐,不能重复订购,默认false */
	public final static String IS_HAVE_PRODUCT = "is_have_product";

	/** 信息中心切换的tab菜单 */
	public static final String INFO_TAB_INDEX = "INFO_TAB_INDEX";

	/** 传入标题的 key */
	public static final String KEY_TITLE = "title";

	public static final String KEY_CALL_BACK = "callBack";

	public static final String KEY_FLAG = "flag";

	public static final int FLAG_QUERY_KEY_WORDS = 1001;
	public static final int FLAG_QUERY_BY_TYPE = 1002;
	public static final int FLAG_QUERY_IN_ALL = 1003;

	public static final String KEY_CONTENT = "key_content";
	public static final String KEY_WJBH = "key_wjbh";
	public static final String KEY_ZRZ = "key_zrz";
	public static final String KEY_WJSJ = "key_wjsj";
	public static final String KEY_TM = "key_tm";
	public static final String KEY_TYPE = "key_type";

	public static final String KEY_USERIDS = "key_userIds";

	/***
	 * 传输的数据
	 */
	public static final String KEY_TRAN_DATA = "tran_data";
	/** 登陆回调 */
	public static final String KEY_LOGIN = "KEY_LOGIN";

	public static final String KEY_WEBVIEW_URL = "key_webview_url";

	public static final String TITLE_CLASSIF_Name = "title_class_name";

	/** 修改商户号 */
	public static final String VALUES_UPDATE_MERCHANT_NUMBER = "values_merchant_number";

	/** 修改商户名 */
	public static final String VALUES_UPDATE_MERCHANT_NAME = "values_merchant_name";

	/** 修改商户密码 */
	public static final String VALUES_UPDATE_MERCHANT_PWS = "values_merchant_pws";

	/** 会员卡 */
	public static final String VALUES_MEMBER = "values_member";

	/** 付款吗 */
	public static final String VALUES_PAY_ID = "values_pay_id";

	/** 卡卷核销 */
	public static final String VALUES_CARD = "values_card";

	/** 充值 */
	public static final int VALUES_CHARGE_GOLD = 1002;

	/** 支付 */
	public static final int VALUES_PAY = 1001;

	/** 会员支付 */
	// public static final int VALUES_MEMBER_PAY = 1004;

	/** 扫描付款吗ID */
	public static final int VALUES_GET_PAYID = 1003;

	/** 打印联数 */
	public static final String KEY_PRINT_COUNT = "key_print_count";

	/** 是否自动打印 */
	public static final String KEY_IS_AUTU_PRINT = "key_is_autu_print";

	/****** 设置个人信息相关 ***/
	public static final int FLAG_UPDAT_NLIK_NAME = 1001;

	public static final int FLAG_UPDAT_NAME = 1002;

	public static final int FLAG_UPDAT_IDCARD = 1003;
	

	public static final int FLAG_UPDAT_EMAILS = 1004;

	public static final int FLAG_UPDAT_CITY = 1005;

	public static final int FLAG_UPDAT_HEAD = 1006;

	public static final int FLAG_FROM_MAIN = 1009;
	
	public static final int FLAG_UPDAT_AGE = 1010;


	public static final String KEY_USER_MSG = "str_user_msg";

}
