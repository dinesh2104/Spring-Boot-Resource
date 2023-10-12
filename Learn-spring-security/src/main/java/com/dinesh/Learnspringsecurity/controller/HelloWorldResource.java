package com.dinesh.Learnspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {

	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello";
	}

}
