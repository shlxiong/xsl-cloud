package com.openxsl.studycloud.feign.invoker;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openxsl.studycloud.feign.invoker.encrypt.FeignInvokerEncryptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 客户端类
 * @author xiongsl
 */
@Configuration
@ConditionalOnProperty("feign.client.packages")
@EnableFeignClients("${feign.client.packages}")
public class FeignInterceptorConfiguration {
	@Value("${spring.application.name:feign}")
	private String name;
	@Value("${feign.client.access-token:feign}")
	private String token;
	
	@Bean
	public RequestInterceptor authorizeRequestInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				template.header("accept-origin", "feign");
				template.header("Authorization", FeignInvokerEncryptor.makeToken(name, token));
			}
			
		};
	}
	
	@PostConstruct
	public void init() {
		token = FeignInvokerEncryptor.getOriginToken(name, token);
	}
	
}
