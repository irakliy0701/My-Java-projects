package ru.kurdashvili.spring_course.REST_App_with_DTO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurdashvili.spring_course.REST_App_with_DTO.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
