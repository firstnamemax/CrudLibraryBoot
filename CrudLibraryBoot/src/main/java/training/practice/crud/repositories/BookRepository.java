package training.practice.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training.practice.crud.models.Book;
import training.practice.crud.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReader(Person person);
    List<Book> findAllByTitleStartingWith(String titleForSearch);
}