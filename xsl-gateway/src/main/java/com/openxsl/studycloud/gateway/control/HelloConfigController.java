package com.openxsl.studycloud.gateway.control;

import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloConfigController {
    
    /**
     * RPC服务
     */
    @GetMapping("/hello")
    public String hello(String name) {
    	System.out.println("Hello! " + name);
    	return "Hello! " + name + ", this is Local service!";
    }
    
}
