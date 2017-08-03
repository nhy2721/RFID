package com.botongsoft.rfid.common.utils;

/**
 * 自定义Log,输出之前判断是否debug模式
 * 
 * @author wangjie
 */
public class LogUtils {
	// 是否DEBUG模式，发布时需要设置为false true
	public static final boolean DEBUG = true;

	private static long curTime;

	/**
	 * 输出 INFO 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.i(tag, msg);
		}
	}

	/**
	 * 输出 DEBUG 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	/**
	 * 输出 ERROR 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		// 这个日志要输出，要不然有错误，获取不到日志。
		// if (DEBUG) {
		android.util.Log.e(tag, msg);
		// }
	}

	/**
	 * 输出 VERBOSE 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.v(tag, msg);
		}
	}

	/**
	 * 输出 WARN 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		// 这个日志要输出，要不然有错误，获取不到日志。
		// if (DEBUG) {
		android.util.Log.w(tag, msg);
		// }
	}

	/**
	 * 输出 时间 日志信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void time(String tag, String msg) {
		if (DEBUG) {
			long t = System.currentTimeMillis();
			android.util.Log
					.i(tag, msg + " this operate cost " + (t - curTime));
		}
	}

	private LogUtils() {
	}

	/**
	 * 统计时间归零
	 */
	public static void resetTime() {
		if (DEBUG) {
			curTime = System.currentTimeMillis();
		}
	}

	public static void d(String s) {

	}
}
