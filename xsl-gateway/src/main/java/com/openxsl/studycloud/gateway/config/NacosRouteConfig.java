package com.openxsl.studycloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.nacos.NacosConfigProperties;

@Configuration
@ConditionalOnProperty(prefix = "gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class NacosRouteConfig {
	@Autowired
    private ApplicationEventPublisher publisher;
	@Autowired
    private NacosConfigProperties nacosConfigProperties;
	
	@Bean
    public NacosRouteRepository nacosRouteDefinitionRepository() {
        return new NacosRouteRepository(publisher, nacosConfigProperties);
    }

}
