package com.janonimo.tazma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TazmaApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TazmaApplication.class);
	}


	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/tazma");
		SpringApplication.run(TazmaApplication.class, args);
	}


}
