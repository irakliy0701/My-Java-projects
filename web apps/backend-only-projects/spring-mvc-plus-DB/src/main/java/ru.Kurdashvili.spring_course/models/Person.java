package ru.Kurdashvili.spring_course.models;

import javax.validation.constraints.*;
import java.util.List;

public class Person {
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min=2, max=100, message = "Name should be between 2 and 100 chars")
    @Pattern(regexp = "", message = "") // TODO: доделать
    private String phio;

    @Min(value=0, message = "BirthYear should be greater than 0")
    private int birthYear;

    public Person(){
    }

    public Person(int id, String phio, int birthYear) {
        this.id = id;
        this.phio = phio;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getPhio() {
        return phio;
    }

    public void setPhio(String phio) {
        this.phio = phio;
    }
}

