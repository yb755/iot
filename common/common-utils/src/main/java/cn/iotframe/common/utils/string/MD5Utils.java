package cn.iotframe.common.utils.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {

	private static Logger logger = LoggerFactory.getLogger(MD5Utils.class.getName());

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];

	}

	/**
	 * 将字符串转换成32位 MD5密码值
	 * 
	 * @param origin
	 * @return
	 * @throws ApplicationException
	 */
	public static String getMD5Encode(String origin) {
		String resultString = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(origin.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			logger.error("加密md5方法失败，错误：" + ex.getMessage(), ex);
		}
		return resultString;
	}

	private static final int HEX_VALUE_COUNT = 16;

	public static String getMD5(byte[] bytes) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char str[] = new char[16 * 2];
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte tmp[] = md.digest();
			int k = 0;
			for (int i = 0; i < HEX_VALUE_COUNT; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(str);
	}
}
