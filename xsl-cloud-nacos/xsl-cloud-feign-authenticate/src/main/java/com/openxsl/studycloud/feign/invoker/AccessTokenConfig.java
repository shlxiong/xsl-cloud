package com.openxsl.studycloud.feign.invoker;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openxsl.studycloud.feign.invoker.anno.ConditionalOnMissingProperty;
import com.openxsl.studycloud.feign.invoker.encrypt.FeignInvokerEncryptor;

/**
 * @author xiongsl
 */
@Configuration
@ConditionalOnMissingProperty("feign.client.access-token")
@ConfigurationProperties(prefix="feign.client.access-token")
public class AccessTokenConfig {
	private Map<String,String> dataMap;
	
	@PostConstruct
	public void init() {
		for (Map.Entry<String,String> entry : dataMap.entrySet()) {
			String value = FeignInvokerEncryptor.getOriginToken(
								entry.getKey(), entry.getValue());
			entry.setValue(value);
		}
	}
	
	@Bean
	public SystemTokenDefaultLoader tokenLoader() {
		return new SystemTokenDefaultLoader();
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
