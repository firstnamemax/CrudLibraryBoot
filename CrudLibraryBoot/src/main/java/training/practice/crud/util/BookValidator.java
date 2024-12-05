package training.practice.crud.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import training.practice.crud.models.Book;

@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (book.getYear() == 0){
            errors.rejectValue("year", "", "Year should not be empty or equal 0");
        }
    }

}