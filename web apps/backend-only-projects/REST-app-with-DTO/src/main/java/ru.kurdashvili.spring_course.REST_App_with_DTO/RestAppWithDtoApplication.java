package ru.kurdashvili.spring_course.REST_App_with_DTO;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestAppWithDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestAppWithDtoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
