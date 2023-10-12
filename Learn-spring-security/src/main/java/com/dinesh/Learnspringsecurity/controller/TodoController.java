package com.dinesh.Learnspringsecurity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final List<Todo> TODOS_LIST = List.of(new Todo("dinesh", "Learn Spring Boot"),
			new Todo("dinesh", "Java"), new Todo("dinesh", "Learn Devops"));

	@GetMapping("/todo")
	public List<Todo> showTodo() {
		return TODOS_LIST;
	}

	@GetMapping("/user/{username}/todo")
	public List<Todo> showUserTodos(@PathVariable String username) {
		return TODOS_LIST;
	}

	@PostMapping("/user/{username}/todo")
	public void createTodosForUser(@PathVariable String username, @RequestBody Todo todo) {
		logger.info("Create {} for {}", todo, username);
	}

}

record Todo(String username, String desc) {

}
