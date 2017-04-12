package org.hch.book.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;


public class DigestUtil {

	/**
	 * md5摘要
	 * @Description (TODO
	 * @param bytes
	 * @return
	 */
	public static byte[] md5(byte[] bytes) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("md5");
			digest.update(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return digest.digest();
	}
	
	/**
	 * MD5摘要
	 * @Description (TODO
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		
		return bytesToHex(md5(source.getBytes()));
	}
	
	/**
	 * 字节数组转16进制
	 * @Description (TODO
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder strBuilder = new StringBuilder();
		for (byte b : bytes) {
			int t = b & 0xff;
			if (t <= 0xf) {
				strBuilder.append("0");
			}
			
			strBuilder.append(Integer.toHexString(t));
		}
		return strBuilder.toString();
	}
	
	/**
	 * 16进制转字节数组
	 * @Description (TODO
	 * @param source
	 * @return
	 */
	public static byte[] hexToBytes(String source) {
		byte[] bytes = null;
		byte[] sourceData = source.getBytes();
		int len = sourceData.length;
		bytes = new byte[len>>1];
		for(int i = 0; i < len; i = i + 2) {
			bytes[i>>1] = (byte) ((sourceData[i]<<4)^(sourceData[i+1]));
		}
		return bytes;
	}
	
	public static void main(String[] args) throws Exception {
		String data = "hello";
		
		byte[] bytes = hexToBytes((bytesToHex(data.getBytes())));
		System.out.println(new String(bytes));
	}
}
