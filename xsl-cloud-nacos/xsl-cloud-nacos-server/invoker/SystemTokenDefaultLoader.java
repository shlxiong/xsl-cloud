package com.openxsl.studycloud.nacos.invoker;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openxsl.studycloud.nacos.invoker.encrypt.FeignInvokerEncryptor;

/**
 * 配置文件或Nacos中
 * @author xiongsl
 */
@Service
public class SystemTokenDefaultLoader {
	@Autowired
	private AccessToken accessToken;
	
	public String getSystemToken(String systemId) {
		String token = accessToken.getValue(systemId);
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("Unrecognized systemId");
		}
		return token;
	}
	
	@Configuration
	@ConfigurationProperties(prefix="feign.client.access-token")
	public static class AccessToken{
		private Map<String,String> dataMap;
		
		@PostConstruct
		public void init() {
			for (Map.Entry<String,String> entry : dataMap.entrySet()) {
				String value = FeignInvokerEncryptor.getOriginToken(
									entry.getKey(), entry.getValue());
				entry.setValue(value);
			}
		}

		public Map<String,String> getDataMap() {
			return dataMap;
		}

		public void setDataMap(Map<String,String> dataMap) {
			this.dataMap = dataMap;
		}
		
		public String getValue(String key) {
			return dataMap.get(key);
		}
		
	}
}
