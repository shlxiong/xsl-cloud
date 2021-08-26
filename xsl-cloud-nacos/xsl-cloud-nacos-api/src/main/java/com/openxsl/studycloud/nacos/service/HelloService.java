package com.openxsl.studycloud.nacos.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${service-names.hello}")
public interface HelloService {
	
	@GetMapping("config/hello")
	public String sayHello(@RequestParam("name")String name);
	
	@PostMapping("config/think")
	public String doThink(@RequestParam("work")String work);

}
