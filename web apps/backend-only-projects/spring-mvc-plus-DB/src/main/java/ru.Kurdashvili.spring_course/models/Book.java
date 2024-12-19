package ru.Kurdashvili.spring_course.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    private int personId;

    @NotEmpty(message = "BookName shouldn't be empty")
    @Size(min=2, max=100, message = "BookName should be between 2 and 100 chars")
    @Pattern(regexp = "", message = "") // TODO: доделать
    private String bookName;

    @NotEmpty(message = "AuthorName shouldn't be empty")
    @Size(min = 2, max = 100, message = "")
    @Pattern(regexp = "", message = "") // TODO: доделать
    private String author;

    @Min(value=0, message = "Release Year should be greater than 0")
    private int releaseYear;


    public Book(){
    }

    public Book(int id, int personId, String bookName, String author, int releaseYear) {
        this.id = id;
        this.personId = personId;
        this.bookName = bookName;
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

}
