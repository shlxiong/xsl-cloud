package com.openxsl.studycloud.nacos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openxsl.studycloud.nacos.service.HelloService;

@RestController
@RequestMapping("test")
public class HelloController {
	@Autowired
	private HelloService service;
	
	@GetMapping("hello")
	public String sayHello() {
		return service.sayHello("Xiong shuilin");
	}
	
	@GetMapping("think")
	public String doThink() {
		return service.doThink("Coding");
	}

}
