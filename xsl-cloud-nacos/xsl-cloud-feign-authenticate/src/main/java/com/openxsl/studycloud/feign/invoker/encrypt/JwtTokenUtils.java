package com.openxsl.studycloud.feign.invoker.encrypt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;

/**
 * JWT -Json Web Token，本质上属于签名/验签
 * 
 * @author xiongsl
 */
public class JwtTokenUtils {
	private static final String AUTHOR_HEADER_PREFIX = "Bearer ";
	
	public static String getRawToken(String authorizationHeader) {
		return authorizationHeader.substring(AUTHOR_HEADER_PREFIX.length());
	}
	
	public static String generToken(String userName) {
		String token = Jwts.builder()
//				.setClaims(map)
//				.setId("userid")   //七个标准的Claim属性
//				.setSubject(userName)
//				.setAudience("")
//				.setIssuer("mycorp")
//				.setIssuedAt(new Date())
//				.setNotBefore(Date())
//				.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .setPayload(userName)
                .signWith(SignatureAlgorithm.HS512, "PrivateSecret")
                .compressWith(new GzipCompressionCodec())   //压缩
                .compact();
		return AUTHOR_HEADER_PREFIX + token;
	}
	
	public static String getUserToken(String token) {
		return Jwts.parser()
                .setSigningKey("PrivateSecret")
                .parseClaimsJws(token.replace(AUTHOR_HEADER_PREFIX, ""))
                .getBody()
                .getSubject();
	}
	
	public static void main(String[] args) {
		String token = generToken("test1");
		System.out.println(token);
		System.out.println(getUserToken(token));
	}

}
