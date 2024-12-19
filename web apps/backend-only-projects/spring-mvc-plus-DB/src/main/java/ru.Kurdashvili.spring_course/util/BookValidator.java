package ru.Kurdashvili.spring_course.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Kurdashvili.spring_course.dao.BookDAO;
import ru.Kurdashvili.spring_course.models.Book;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        System.out.println("id = " + book.getId());

        // Проверка на уникальность названия книги при создании и обновлении: (мб не надо)
        if (bookDAO.show(book.getBookName()).isPresent()){
            if (book.getId() == 0){ // || !book.getBookName().equals(bookDAO.show(book.getId()).getBookName())
                errors.rejectValue("bookName", "", "This bookName is already in use");
            }
            else if (!book.getBookName().equals(bookDAO.show(book.getId()).getBookName())){
                errors.rejectValue("bookName", "", "This bookName is already in use");
            }
        }
    }
}
