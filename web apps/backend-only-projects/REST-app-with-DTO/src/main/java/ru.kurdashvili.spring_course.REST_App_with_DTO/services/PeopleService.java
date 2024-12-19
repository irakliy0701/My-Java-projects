package ru.kurdashvili.spring_course.REST_App_with_DTO.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurdashvili.spring_course.REST_App_with_DTO.models.Person;
import ru.kurdashvili.spring_course.REST_App_with_DTO.repositories.PeopleRepository;
import ru.kurdashvili.spring_course.REST_App_with_DTO.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
//        return foundPerson.orElse(null); // при вводе id несущ. человека на клиенте
        // будет выбрасываться искл, если его не обрабатывать в нашем Spring App
        // (null не конвертится в JSON)

        return foundPerson.orElseThrow(PersonNotFoundException::new); // если не нашли человека, то
                // будет выбрасываться наше кастомное искл
    }

    @Transactional
    public void save(Person person){
        enrichPerson(person);
        peopleRepository.save(person);
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN"); // здесь в реальном приложении у нас было бы какая-то логика,
        // с помощью которой получалось бы имя пользователя, кто сейчас создает человека. То
        // есть здесь могли бы использовать Spring Security, получать имя человека, кто сейчас
        // работает с системой и здесь это имя класть в это поле
    }
}
