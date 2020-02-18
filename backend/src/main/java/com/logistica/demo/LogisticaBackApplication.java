package com.logistica.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = "com.logistica.demo")
public class LogisticaBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaBackApplication.class, args);
	}

}
