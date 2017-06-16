package com.botongsoft.rfid.common.utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author alun (http://alunblog.duapp.com)
 * @version 1.0
 * @created 2013-5-17
 */
public class RSA {

	// RSA加密
	/****
	 * 
	 * @param str
	 *            加密内容
	 * @param business_no
	 *            商户号
	 * @param public_key
	 *            公钥
	 * @return
	 */
	public static String rsaEncode(String str, String business_no,
			String public_key) {
		String rsaStr = "";
		String tmpStr = "";

		int len = str.length();
		int rsaLen = (len % 117) != 0 ? ((len - len % 117) / 117 + 1)
				: (len / 117);// 分段加密的分段数
		for (int i = 0; i < rsaLen; i++) {// 开始分段加密
			tmpStr = str.substring(117 * i, (i == rsaLen - 1) ? len : 117);// 最后一段长度不一定刚好117
			rsaStr += RSA.encryptByPublic(tmpStr, public_key);
		}
		rsaStr = rsaStr.replace("+", "%2B").replace("/", "%2F")
				.replace("=", "%3D").replace("\n", ""); // 模拟urlencode,
														// 说明:RSA加密串中只有+,/,=这三个特殊字符,所有可以这样模拟
		rsaStr += business_no;// 最后连接上商家号,加密完成
		return rsaStr;
	}

	/*
	 * private static final String RSA_PUBLICE =
	 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPZ1Ju6zzYUoL7HYzLgneROyDC" + "\r"
	 * + "Qf/NANWGO42FfXwQinYVSpqnmtukGLsOhVuwdNH6aRFE0ms3bkpp/WL4cfVDgnCO" +
	 * "\r" + "+W9J6vRVpuTuD/iqfJd9TNacLCd3Jvg3HhjqxbJeO74fYnYqo/mmyJSeLE5iZg4I"
	 * + "\r" + "Zm5LPWBZWUp3ULCAZQIDAQAB";
	 */
	private static final String ALGORITHM = "RSA";

	/**
	 * 得到公钥
	 * 
	 * @param algorithm
	 * @param bysKey
	 * @return
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm,
			String bysKey) throws NoSuchAlgorithmException, Exception {
		byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(x509);
	}

	/**
	 * 使用公钥加密
	 * 
	 * @param content
	 * @param
	 * @return
	 */
	public static String encryptByPublic(String content, String RSA_PUBLICE) {
		try {
			PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubkey);

			byte plaintext[] = content.getBytes("UTF-8");
			byte[] output = cipher.doFinal(plaintext);

			String s = new String(Base64.encode(output, Base64.DEFAULT));

			return s;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用公钥解密
	 * 
	 * @param content
	 *            密文
	 * @param
	 *
	 * @return 解密后的字符串
	 */
	public static String decryptByPublic(String content, String RSA_PUBLICE) {
		try {
			PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pubkey);
			InputStream ins = new ByteArrayInputStream(Base64.decode(content,
					Base64.DEFAULT));
			ByteArrayOutputStream writer = new ByteArrayOutputStream();
			byte[] buf = new byte[128];
			int bufl;
			while ((bufl = ins.read(buf)) != -1) {
				byte[] block = null;
				if (buf.length == bufl) {
					block = buf;
				} else {
					block = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						block[i] = buf[i];
					}
				}
				writer.write(cipher.doFinal(block));
			}
			return new String(writer.toByteArray(), "utf-8");
		} catch (Exception e) {
			return null;
		}
	}

}