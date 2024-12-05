package training.practice.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.practice.crud.models.Book;
import training.practice.crud.models.Person;
import training.practice.crud.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int books_per_page){
        return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> findAll(boolean flag){
        if (flag)
            return bookRepository.findAll(Sort.by("year"));
        else return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int books_per_page, boolean flag){
        if (flag)
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
        else return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public Book findOne(int id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        System.out.println(book);
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBookID(id);
        updatedBook.setReader(findOne(id).getReader());
        updatedBook.setCreatedAt(findOne(id).getCreatedAt());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> showBooks(Person person){
        return bookRepository.findAllByReader(person);
    }

    public List<Book> showBooksWithDelay(List<Book> array){
        Date date = new Date();

        for (Book book : array){
            long duration = date.getTime() - book.getCreatedAt().getTime();
            if (duration > book.getDELAY() * 60000) book.setFLAG(true);
                else book.setFLAG(false);
        }

        return array;
    }

    public Person showPerson(int bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        return book.orElse(null).getReader();
    }

    @Transactional
    public void freeBook(int bookId){
        Book book = bookRepository.findById(bookId).get();
        book.setReader(null);
        book.setCreatedAt(null);
    }

    @Transactional
    public void choosePerson(int bookId, int personId){
        Book book = bookRepository.findById(bookId).get();
        book.setReader(peopleService.findOne(personId));
        book.setCreatedAt(new Date());
    }

    public List<Book> findAllByTitleStartingWith(String titleForSearch){
        return bookRepository.findAllByTitleStartingWith(titleForSearch);
    }

}