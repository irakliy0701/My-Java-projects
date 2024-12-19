package ru.kurdashvili.spring_course.JWTAuthorizationApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.repositories.PeopleRepository;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {// Не совсем
    // обычный Service, тк он предназначается именно для Spring Security.
    // И чтобы Spring Security знал, что этот Service загружает пользователя из бд,
    // мы должны реализовывать интерфейс UserDetailsService
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
            // Это исключение будет поймано Spring Security и Spring Security
            // покажет это сообщение на странице логина
        }
        return new PersonDetails(person.get()); // если человек есть
    }
}
