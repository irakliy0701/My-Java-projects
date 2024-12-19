package ru.kurdashvili.spring_course.JWTAuthorizationApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JWTAuthorizationApp {

	public static void main(String[] args) {
		SpringApplication.run(JWTAuthorizationApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
