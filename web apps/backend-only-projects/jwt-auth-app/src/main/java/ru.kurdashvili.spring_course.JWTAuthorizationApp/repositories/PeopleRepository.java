package ru.kurdashvili.spring_course.JWTAuthorizationApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
}
