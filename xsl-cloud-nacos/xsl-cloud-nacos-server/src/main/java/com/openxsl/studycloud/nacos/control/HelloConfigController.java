package com.openxsl.studycloud.nacos.control;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class HelloConfigController {
	@Value("${useLocalCache:false}")
    private boolean useLocalCache;
	@Value("${corpCode:openxsl.com}")
	private String corpCode;

    @GetMapping("/get")
    public String get() {
        return corpCode + ":" + useLocalCache;
    }
    
    /**
     * RPC服务
     */
    @GetMapping("/hello")
    public String hello(String name) {
    	System.out.println("Hello! " + name);
    	return "Hello! " + name + ", this is nacos provider.";
    }
    
    /**
     * RPC服务
     */
    @PostMapping("/think")
    public String think(String work) {
    	System.out.println("do-think: " + work);
    	return "Let's think something: " + work;
    }

}
