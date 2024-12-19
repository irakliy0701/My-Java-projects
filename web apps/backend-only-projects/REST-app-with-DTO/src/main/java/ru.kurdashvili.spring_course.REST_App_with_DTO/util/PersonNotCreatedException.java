package ru.kurdashvili.spring_course.REST_App_with_DTO.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String msg){
        super(msg);
    }
}
