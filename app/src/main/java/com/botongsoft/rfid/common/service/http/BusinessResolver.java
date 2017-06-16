package com.botongsoft.rfid.common.service.http;

import android.util.Log;

import com.botongsoft.rfid.common.utils.LogUtils;

import java.util.Map;
import java.util.Map.Entry;


/***
 * 业务接口调用辅助类
 * 
 * @author kohui
 * 
 */
public abstract class BusinessResolver {

	private static final String TAG = "NetUtils";

	/**
	 * 数据请求是否成功
	 */
	public static final String PRAM_SUCCESS = "success";

	/**
	 * 数据请求返回消息 成功时为“” 失败时 错误信息
	 */
	public static final String PRAM_MSG = "message";

	/**
	 * 数据请求后返回的数组
	 */
	public static final String PRAM_DATA = "data";

	/**
	 * 数据请求后返回的数组
	 */
	public static final String PRAM_MAP = "map";

	/**
	 * 数据请求后返回的数组
	 */
	public static final String PRAM_RETV = "retValue";

	/**
	 * 解析json字符串，判断是否正确返回结果，正确则解析出data属性中的内容，否则抛出BusinessException异常
	 * 
	 * @param
	 * @return
	 * @throws BusinessException
	 * 
	 *             /** 使用GET方式获取数据
	 * 
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	static String getData(String url) throws BusinessException {
		String json = null;
		try {
			json = NetUtils.doGetString(url);
			LogUtils.d(TAG, "getData()=请求=====" + json);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(BusinessException.CODE_UNREACH_SERVER);
		}
		return json;
	}

	static String getDataLogin(String url) throws BusinessException {
		return null;

	}

	/**
	 * 使用GET方式获取数据
	 * 
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	static String getData(String url, Map<String, Object> params)
			throws BusinessException {
		if (params != null && !params.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(url).append("?");

			for (Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			url = sb.toString();
			System.out.println(url + "---");

		}
		return getData(url);

	}

	static String getDataLogin(String url, Map<String, Object> params)
			throws BusinessException {
		if (params != null && !params.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(url).append("?");

			for (Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			url = sb.toString();

		}
		return getDataLogin(url);

	}

	/**
	 * 使用post方式获取数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	static String postData(String url, Map<String, Object> params)
			throws BusinessException {
		String json = null;
		try {
			json = NetUtils.doPostString(url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(TAG, "result:" + json);
		return json;
	}

	/****
	 * 自定义json转换
	 * 
	 * @author kohui
	 * 
	 * @param <T>
	 */
	interface JsonParser<T> {
		T parse(String json);
	}

	public interface BusinessCallback<T> {
		/**
		 * 成功时调用
		 * 
		 * @param t
		 *            泛型，按照实际返回结果使用指定对象。如果不需要返回对象则使用Void
		 */
		// void onSuccess(T t);
		void onSuccess(T t, int act);

		/**
		 * 发生异常时调用
		 * 
		 * @param e
		 */
		void onError(BusinessException e, int act);
	}
}
