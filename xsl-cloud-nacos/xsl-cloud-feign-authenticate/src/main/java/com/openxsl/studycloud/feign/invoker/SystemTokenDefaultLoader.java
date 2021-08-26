package com.openxsl.studycloud.feign.invoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author xiongsl
 */
public class SystemTokenDefaultLoader {
	@Autowired
	private AccessTokenConfig accessToken;
	
	public String getSystemToken(String systemId) {
		String token = accessToken.getValue(systemId);
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("Unrecognized systemId");
		}
		return token;
	}
	
}
