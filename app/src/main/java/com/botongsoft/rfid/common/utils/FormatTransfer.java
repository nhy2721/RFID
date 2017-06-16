package com.botongsoft.rfid.common.utils;

/***
 * 转换成字节数组
 * 
 * @author kohui
 * 
 */
public class FormatTransfer {

	public static byte[] toHH(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0xff);
		b[2] = (byte) (n >> 8 & 0xff);
		b[1] = (byte) (n >> 16 & 0xff);
		b[0] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static byte[] toHH(short n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) (n >> 8 & 0xff);
		return b;
	}

	public static byte[] byteArrayCopy(byte[] abyte1, byte[] abyte2) {
		if (abyte1 == null)
			abyte1 = new byte[0];
		byte[] newbytes = new byte[abyte1.length + abyte2.length];
		System.arraycopy(abyte1, 0, newbytes, 0, abyte1.length);
		System.arraycopy(abyte2, 0, newbytes, abyte1.length, abyte2.length);
		return newbytes;
	}

}
