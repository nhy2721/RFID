package com.botongsoft.rfid.common.service.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.botongsoft.rfid.bean.LocalUserData;
import com.botongsoft.rfid.bean.http.SendBaseResponse;
import com.botongsoft.rfid.common.utils.LogUtils;
import com.botongsoft.rfid.common.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * 网络操作工具类
 * 
 * @author wangjie
 */
public class NetUtils {

	/**
	 * 无连接
	 */
	public static final int STATE_CONNECT_NONE = 0;

	/**
	 * WIFI连接
	 */
	public static final int STATE_CONNECT_WIFI = 1;

	/**
	 * 移动网络 2G/3G
	 */
	public static final int STATE_CONNECT_MOBILE = 2;

	private static final int TIMEOUT = 80000;

	private static final String TAG = "NetUtils";

	private static final int IO_BUFFER_SIZE = 8 * 1024;

	public static final String ENCODE_UTF_8 = "UTF-8";

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取当前网络连接状态
	 * 
	 * @param context
	 * @return 常量 STATE_CONNECT_NONE：无连接， STATE_CONNECT_WIFI：WIFI连接,
	 *         STATE_CONNECT_MOBILE：移动网络 2G/3G
	 */
	public static int getNetConnectState(Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (networkInfo != null && networkInfo.isConnected()) {
			return STATE_CONNECT_WIFI;
		}
		networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return STATE_CONNECT_MOBILE;
		}
		return STATE_CONNECT_NONE;
	}

	/**
	 * 访问指定url，并获取inputStream
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	// public static InputStream doGetStream(String urlString) throws
	// IOException {
	// InputStream is = null;
	// disableConnectionReuseIfNecessary();
	// HttpURLConnection connection = null;
	//
	// URL url = new URL(urlString);
	// connection = (HttpURLConnection) url.openConnection();
	// connection.setReadTimeout(TIMEOUT);
	// connection.setConnectTimeout(TIMEOUT);
	// connection.setRequestMethod("GET");
	// connection.setDoInput(true);
	// if (!KpiInfoResolver.CURRENT_SESSION.equals("")) {
	// connection.setRequestProperty("Cookie", "JSESSIONID="
	// + KpiInfoResolver.CURRENT_SESSION);
	// }
	// connection.connect();
	//
	// final int statusCode = connection.getResponseCode();
	// if (statusCode != HttpStatus.SC_OK) {
	// return null;
	// }
	//
	// is = connection.getInputStream();
	// return is;
	// }

	/**
	 * 使用GET连接指定的url网址，返回结果转为String
	 * 
	 * @param urlString
	 * @return
	 */
	public static String doGetString(String urlString) {
		InputStream is = null;
		LogUtils.d(TAG, "doPost()=请求URL=====" + urlString);

		disableConnectionReuseIfNecessary();
		HttpURLConnection connection = null;
		try {

			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(TIMEOUT);
			connection.setConnectTimeout(TIMEOUT);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("accept", "*/*");
			connection.setDoInput(true);
			connection.connect();

			final int statusCode = connection.getResponseCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			is = connection.getInputStream();
			return Utils.convertToString(is);

		} catch (Exception e) {
			System.out.print("==================================="
					+ e.getMessage());
		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	/**
	 * 向指定url发送POST请求，返回结果转为String
	 * 
	 * @param urlString
	 * @param params
	 *            post请求参数
	 * @return
	 */
	public static String doPostString(String urlString,
			Map<String, Object> params) {

		return doPost(urlString, mapToParams(params));
	}

	/**
	 * 将Map转换成JSON字符串
	 * 
	 * @param params
	 * @return 实例:key1=value1&key2=value2
	 */
	public static String mapToParams(Map<String, Object> params) {
		StringBuilder paramsStr = new StringBuilder();
		if (params != null && params.size() > 0) {
			try {
				for (String key : params.keySet()) {

					paramsStr.append(URLEncoder.encode(key, ENCODE_UTF_8))
							.append("=").append(params.get(key)).append("&");

				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				LogUtils.e(TAG, "mapToJson()==map转换JSON异常====" + e.toString());
				paramsStr.toString();
			}
			paramsStr.deleteCharAt(paramsStr.length() - 1);
		}
		return paramsStr.toString();
	}

	/**
	 * 向指定url发送POST请求，返回结果转为String
	 * 
	 * @param urlString
	 * @param params
	 *            post请求参数
	 * @return
	 */
	public static String doPost(String urlString, String params) {
		if (TextUtils.isEmpty(urlString) || TextUtils.isEmpty(params)) {
			return null;
		}

		NameValuePair pair1 = new BasicNameValuePair("message", params);
		NameValuePair pair2 = new BasicNameValuePair("clientType", "0");
		SendBaseResponse userInfo = LocalUserData.getInstance().getUserInfo();
		String phoneNumber = "";
		if (userInfo != null) {
			phoneNumber = userInfo.phoneNum;
		}
		NameValuePair pair3 = new BasicNameValuePair("sessionId", phoneNumber);
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		pairList.add(pair1);
		pairList.add(pair2);
		pairList.add(pair3);
		disableConnectionReuseIfNecessary();
		BufferedReader reader = null;
		LogUtils.d(TAG, "doPost()=请求URL=====" + urlString);
		LogUtils.d(TAG, "doPost()=请求报文=====" + pairList);
		StringBuilder result = new StringBuilder();
		try {
			HttpPost httpPost = new HttpPost(urlString);
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, HTTP.UTF_8));
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost);
			if (null == response) {
				return "";
			}
			HttpEntity httpEntity = response.getEntity();
			InputStream inputStream = httpEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String buff;
			while (null != (buff = reader.readLine())) {
				result.append(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e(TAG, "doPost()=请求异常=====" + e.toString());
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LogUtils.v(TAG, "doPost()=响应报文=====" + result.toString());
		return result.toString();

	}

	/**
	 * 修复Froyo之前版本的bug, 具体见:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 */
	private static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		// if (hasHttpConnectionBug()) {
		System.setProperty("http.keepAlive", "false");
		// }
	}

	private NetUtils() {
	}
}
