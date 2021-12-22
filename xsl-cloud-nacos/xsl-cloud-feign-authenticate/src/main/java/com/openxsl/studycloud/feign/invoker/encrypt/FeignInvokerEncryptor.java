package com.openxsl.studycloud.feign.invoker.encrypt;

import org.springframework.util.Assert;

public class FeignInvokerEncryptor {
	
	/**
	 * 客户端传递Token
	 * @param name
	 * @param token
	 */
	public static String makeToken(String name, String originToken) {
		return HexEncoder.encode(name) + ":" + encryptToken(name, originToken);
	}
	/**
	 * 服务端校验token
	 * @param authors
	 */
	public static boolean validateToken(String[] authors, String originToken) {
		Assert.isTrue(authors.length==2, "Authorization must be partions of ':'");
		String expectToken = encryptToken(authors[0], originToken);
		return authors[1].equals(expectToken);
	}
	
	/**
	 * 服务端根据应用名产生一个token
	 * @param originToken
	 */
	public static String generToken(String name, String originToken) {
		AESPasswordEncoder encoder = new AESPasswordEncoder();
		try {
			encoder.setSalt(name);
			return HexEncoder.encode(encoder.encode(originToken));
		} finally {
			encoder = null;
		}
	}
	/**
	 * 客户端获取(解密)
	 */
	public static String getOriginToken(String name, String token) {
		AESPasswordEncoder encoder = new AESPasswordEncoder();
		try {
			encoder.setSalt(name);
			return encoder.decode(HexEncoder.decode(token));
		} finally {
			encoder = null;
		}
	}
	
	
	private static String encryptToken(String name, String originToken) {
		return HexEncoder.md5(HexEncoder.encode(originToken));
	}
	
	public static void main(String[] args) {
		String token = generToken("nacos-consumer", "Ubuntu#CentOS-6789");
		//NkU1NTcxNjc0NDY4Njk0RTY1Njg2Nzc5NUE3MTU0NjY0NDZCNDM0ODM5NzI2MTY4NjU3QTRGNDg0NjY3NkU3OTU4NTk3NjM1Nzc2NTUwNDI2Qzc2Njc
		System.out.println(token);
		String origin = getOriginToken("nacos-consumer", token);
		System.out.println(origin);
	}

}
