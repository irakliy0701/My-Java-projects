package ru.Kurdashvili.spring_course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.Kurdashvili.spring_course.models.Book;
import ru.Kurdashvili.spring_course.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String bookName) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_name=?", new Object[]{bookName},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(book_name, author, release_year) VALUES(?, ?, ?)", book.getBookName(),
                book.getAuthor(), book.getReleaseYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET book_name=?, author=?, release_year=? WHERE id=?",updatedBook.getBookName(),
                updatedBook.getAuthor(), updatedBook.getReleaseYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    // Делаем JOIN таблицы Book и Person и получаем человека, которому принадлежит книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        // 1 способ:
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON " +
                "Book.person_id = Person.id WHERE Book.id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

        // 2 способ:
//        List<Book> bookOfowner = jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
//                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
//        int ownerId = bookOfowner.get(0).getPersonId();
//        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{ownerId},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    // Освобождает книгу
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }

    // Назначает книгу человеку
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
