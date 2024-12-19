package ru.Kurdashvili.spring_course.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Kurdashvili.spring_course.dao.PersonDAO;
import ru.Kurdashvili.spring_course.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        System.out.println("id = " + person.getId());

        // Проверка на уникальность ФИО при создании и обновлении:
        if (personDAO.show(person.getPhio()).isPresent()){
            if (person.getId() == 0){ // || !person.getPhio().equals(personDAO.show(person.getId()).getPhio())
                errors.rejectValue("phio", "", "This name is already in use");
            }
            else if (!person.getPhio().equals(personDAO.show(person.getId()).getPhio())){
                errors.rejectValue("phio", "", "This name is already in use");
            }
        }

    }
}