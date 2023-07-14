package com.janonimo.tazma;

import com.github.javafaker.Faker;
import com.janonimo.tazma.user.Gender;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.services.UserService;
import com.janonimo.tazma.util.TownTyposCorrector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;

@SpringBootApplication
public class TazmaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/tazma");
		SpringApplication.run(TazmaApplication.class, args);
	}


}
