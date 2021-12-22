package com.openxsl.studycloud.gateway.config;

import java.util.concurrent.Executor;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NacosRouteRepository implements RouteDefinitionRepository{
	private ConfigService confService;
	private NacosConfigProperties nacosProperties;
	
	public NacosRouteRepository(ApplicationEventPublisher publisher,
								NacosConfigProperties nacosConfigProperties) {
		this.nacosProperties = nacosConfigProperties;
		String group = nacosProperties.getGroup();
		confService = new NacosConfigManager(nacosProperties).getConfigService();
		try {
			confService.addListener(group, "dataId", new Listener() {

				@Override
				public Executor getExecutor() {
					return null;
				}

				@Override
				public void receiveConfigInfo(String configInfo) {
					publisher.publishEvent(new RefreshRoutesEvent(this));
				}
				
			});
		} catch (NacosException e) {
			e.printStackTrace();
		}
	}

	// RefreshRoutesEvent
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		String configInfo = ""; //confService.getConfig(null, null, 0);
		return Flux.fromIterable(JSON.parseArray(configInfo, RouteDefinition.class));
	}

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return null;
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		return null;
	}

}
