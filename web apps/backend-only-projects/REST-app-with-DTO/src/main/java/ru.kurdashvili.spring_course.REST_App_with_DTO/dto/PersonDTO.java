package ru.kurdashvili.spring_course.REST_App_with_DTO.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {
    // Описываем те поля, которые будут приходить от клиента,
    // и которые будем клиенту отправлять:
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chars")
    private String name;

    @Min(value = 1, message = "Age should be greater than 0")
    private int age;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
