package com.botongsoft.rfid.common.service.http;

import com.botongsoft.rfid.bean.http.ResponseBean;
import com.botongsoft.rfid.common.utils.FormatTransfer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;



/***
 * 请求共公类
 * 
 * @author kohui
 * 
 */
public class URLByteConnector {

	static final int ENCRY_TYPE_0 = 0, ENCRY_TYPE_1 = 1, ENCRY_TYPE_2 = 2,
			ENCRY_TYPE_3 = 3;
	private static final String TAG = "RequestTask";


	/***
	 * 拼接请求消息体字节流（消息体+消息头）
	 * 
	 * @param body
	 *            json拼接消息体
	 * @param verison
	 *            版本号
	 * @param encryptType
	 *            加密方式
	 * @param act
	 *            接口编号(Act)
	 * @param session_id
	 *            SessionID，用户的会话标识(GUID)，
	 * @return
	 */
	public static byte[] getAssyParams(String body, String verison,
			int encryptType, String act, String session_id) {
		switch (Integer.valueOf(encryptType)) {
		case ENCRY_TYPE_0:
			break;
		case ENCRY_TYPE_1:
			break;
		case ENCRY_TYPE_2:
			break;
		default:
			break;
		}
		
		byte[] input = null;
		// 版本号
		input = FormatTransfer.toHH(Short.parseShort(verison));
		// 加密方式
		byte[] encrypt = new byte[] { Byte.parseByte(encryptType+"") };
		input = FormatTransfer.byteArrayCopy(input, encrypt);
		// 接口编号(Act)
		input = FormatTransfer.byteArrayCopy(input,
				FormatTransfer.toHH(Short.parseShort(act)));
		// 数据包体长度
		int body_length = body.length();
		input = FormatTransfer.byteArrayCopy(input,
				FormatTransfer.toHH(body_length));
		input = FormatTransfer.byteArrayCopy(input, session_id.getBytes());
		byte[] reserve = new byte[23];
		input = FormatTransfer.byteArrayCopy(input, reserve);
		try {
			input = FormatTransfer.byteArrayCopy(input, body.getBytes("gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;

	}

	public static ResponseBean connect(String path, String method, byte[] input) {
		HttpURLConnection conn = null;
		ResponseBean bean = null;
		try {
			// 生成URL对象
			URL url = new URL(path);
			// 根据url.opernConnection()得到HttpURLConnection对象,并可以进行按照Http协议通信
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("content-type", "text/html");
			// 设置请求的方式为GET
			conn.setRequestMethod(method);
			// 设置连接超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "100000");
			// 设置读取超时时间
			System.setProperty("sun.net.client.defaultReadTimeout", "100000");
			// URL 连接可用于输入和/或输出。将 doInput 标志设置为 true，指示应用程序要从 URL 连接读取数据。
			conn.setDoInput(true);
			// 根据通信输出流con.getOutputStream() 得到文本输出流打印对象的格式化表示形式
			if (input != null) {
				// URL 连接可用于输入和/或输出。将 doOutput 标志设置为 true，指示应用程序要将数据写入 URL 连接。
				conn.setDoOutput(true);
				OutputStream os = conn.getOutputStream();
				os.write(input);
				os.flush();
			}
			// 输入
			InputStream in = null;
			in = conn.getInputStream();

			bean = convertInputStreamToString(in);
			// 处理逻辑
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return bean;
	}

	/**
	 * 转化数据流为实体。方便操作
	 * 
	 * @param stream
	 *            访问数据流
	 * @return 转化实体
	 * @throws Exception
	 */
	public static ResponseBean convertInputStreamToString(InputStream stream)
			throws Exception {
		if (stream == null) {
			return null;
		}
		ResponseBean response = new ResponseBean();
		DataInputStream responseData = new DataInputStream(stream);
		try {
			response.setEncryptionMethod(responseData.readByte());
			response.setAct(responseData.readShort());
			response.setErrorCode(responseData.readInt());
			response.setPackageLength(responseData.readInt());
			responseData.skipBytes(21);
		} catch (IOException e) {
			// throw new Exception(ErrorType.IO_FAILURE);
		}
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			String body = stringBuilder.toString();
			response.setBody(body);
		} catch (IOException e) {
			// throw new Exception(ErrorType.IO_FAILURE);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				// throw new Exception(ErrorType.IO_FAILURE);
			}
		}

		return response;
	}
}
