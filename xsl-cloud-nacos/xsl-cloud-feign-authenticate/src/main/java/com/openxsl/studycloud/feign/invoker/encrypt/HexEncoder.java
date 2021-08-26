package com.openxsl.studycloud.feign.invoker.encrypt;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class HexEncoder {
	public final static String toHex(String text) {
		StringBuffer sb = new StringBuffer(text.length()<<1);
		for (byte b : text.getBytes()) {
			String str = Integer.toHexString(0xFF & b);
			sb.append(str.toUpperCase());
		}
		return sb.toString();
	}
	public final static String fromHex(String hex){
		final int len = hex.length();
		byte[] bytes = new byte[len>>1];
		for (int i = 0; i < len; i+=2) {
			bytes[i>>1] = (byte)Integer.parseInt(hex.substring(i,i+2), 16);
		}
		return new String(bytes);
	}

	/**
	 * 先转16进制再Base64编码
	 */
	public static String encode(String text){
		String encoded = toHex(text);
		return Base64.encodeBase64URLSafeString(encoded.getBytes());
	}
	/**
	 * 先Base64解码再转16进制
	 */
	public static String decode(String secret){
		String decoded = new String(Base64.decodeBase64(secret));
		return fromHex(decoded);
	}
	
	/**
	 * Base64 URL编码
	 */
	public static String encode(byte[] data){
		return Base64.encodeBase64URLSafeString(data);
	}
	public static byte[] decodeBytes(String secret){
		return Base64.decodeBase64(secret);
	}
	
	/**
	 * Base64编码
	 */
	public static byte[] encodeBytes(String text){
		return Base64.encodeBase64(text.getBytes());
	}
	public static String decode(byte[] secret){
		return new String(Base64.decodeBase64(secret));
	}
	
	/**
	 * MD5摘要，固定长度
	 */
	public static String md5(String text) {
		try {
	        byte[] bytes = MessageDigest.getInstance("MD5").digest(text.getBytes()); 
	        return encode(bytes);
		}catch (Exception e) {
			return text;
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		String text = "We_are_ready_我们都是龙的传人！^=^";
		text = "xhhtrust-p@sw0rd";
		String secret = toHex(text);
		System.out.println(secret);
		System.out.println(fromHex(secret));
		
		secret = encode(text);
		System.out.println(secret);
		System.out.println(decode(secret));
		System.out.println(encode("xhhtdp1699"));
		System.out.println(encode("zjtg123456"));
	}

}
