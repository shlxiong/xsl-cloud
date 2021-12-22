package com.openxsl.studycloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关应用，根据URL中的子域跳转到不同的应用（lb://app1）
 * 
 * 本案中, http://localhost:8080/test/hello 跳转到 http://localhost:8081/test/hello
 * http://localhost:8080/config/hello?name=ketty 跳转到 http://localhost:8082/config/hello?name=ketty
 * http://localhost:8080/test/hello则不会跳转
 * 
 * @author 熊水林
 */
@SpringBootApplication
public class GatewayApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
 
//	/**
//	 * 代码指定PredicateSpec
//	 * @param builder
//	 * @return
//	 */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("path_route", p -> p.path("/csdn").uri("https://blog.csdn.net"))
//                .build();
//    }

}
