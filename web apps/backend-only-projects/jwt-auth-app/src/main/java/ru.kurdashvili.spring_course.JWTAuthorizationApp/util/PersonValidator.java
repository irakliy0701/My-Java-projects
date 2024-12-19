package ru.kurdashvili.spring_course.JWTAuthorizationApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.services.PersonDetailsService;


@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // Вообще говоря тут можем использовать уже написанный PersonDetailsService,
        // потому что он (метод с логикой поиска - loadUserByUsernamе())делает то,
        // что нам нужно - он получает человека по имени пользователя, однако
        // в методе loadUserByUsername() ненмого неудобная для нас логика:
        // если пользователь не найден, то выбрасывается исклю;
        // если пользователь найден, то искл не выбрасываестя.
        // Тогда тут (в этом методе) опираясь на описанную логику будем смотреть:
        // 1. если искл выбросилось, то это означ., что человек не был найден в таблице бд
        // 2. если искл не выброс -> человек найден в бд

        // Вообще говоря это не оч хорошо так делать - опираться на выброс/невыброс искл.
        // (т.е если тут валидация пройдет, то в PersonDetailsService будем ожидать искл)
        // Поэтому в services можем реализовать отдельный сервис, там реализовать еще
        // один метод с такой же логикой как loadUserByUsername() и там уже возвращать
        // Optional<Person> например.

        Person person = (Person) o;

        // плохой код:
        try{
            personDetailsService.loadUserByUsername(person.getUsername());
        }
        catch (UsernameNotFoundException ignored){
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("username", "",
                "Человек с таким именем пользователя уже существует");
    }
}
