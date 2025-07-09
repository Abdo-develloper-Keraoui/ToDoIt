package com.webapp.todoit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoItApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoItApplication.class, args);
		System.out.println("HelloWorld");
	}

}
