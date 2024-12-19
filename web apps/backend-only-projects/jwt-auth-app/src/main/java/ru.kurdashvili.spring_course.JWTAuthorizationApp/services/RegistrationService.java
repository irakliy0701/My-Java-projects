package ru.kurdashvili.spring_course.JWTAuthorizationApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.repositories.PeopleRepository;


@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person){
        // Должны шифровать тот пароль, который человек прислал с формы регистрации:
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRole("ROLE_USER"); // любому пользователю прошедшему регистрацию
                        // назаначаем роль USER

        peopleRepository.save(person);
    }
}
