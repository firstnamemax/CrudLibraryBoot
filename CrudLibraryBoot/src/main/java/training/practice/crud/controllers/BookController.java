package training.practice.crud.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import training.practice.crud.models.Book;
import training.practice.crud.services.BookService;
import training.practice.crud.services.PeopleService;
import training.practice.crud.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BookService bookService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService, BookValidator bookValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page"})
    public String index(@RequestParam("page") int page,
                        @RequestParam("books_per_page") int books_per_page,
                        Model model) {
        model.addAttribute("books", bookService.findAll(page, books_per_page));
        return "books/index";
    }

    @GetMapping(params = {"sort_by_year"})
    public String index(@RequestParam("sort_by_year") boolean sort_by_year,
                        Model model) {
        model.addAttribute("books", bookService.findAll(sort_by_year));
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page", "sort_by_year"})
    public String index(@RequestParam("page") int page,
                        @RequestParam("books_per_page") int books_per_page,
                        @RequestParam("sort_by_year") boolean sort_by_year,
                        Model model) {
        model.addAttribute("books", bookService.findAll(page, books_per_page, sort_by_year));
        return "books/index";
    }

    @GetMapping("/{bookID}")
    public String show(@PathVariable("bookID") int bookID, Model model) {
        model.addAttribute("book", bookService.findOne(bookID));
        model.addAttribute("person", bookService.showPerson(bookID));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookID}/edit")
    public String edit(@PathVariable("bookID") int bookID,
                       Model model){
        model.addAttribute("book", bookService.findOne(bookID));
        return "books/edit";
    }

    @PatchMapping("/{bookID}")
    public String edit(@PathVariable("bookID") int bookID,
                       @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(bookID, book);
        return "redirect:/books";
    }

    @DeleteMapping("{bookID}")
    public String delete(@PathVariable("bookID") int bookID){
        bookService.delete(bookID);
        return "redirect:/books";
    }

    @PatchMapping("/{bookID}/freeBook")
    public String freeBook(@PathVariable("bookID") int bookID){
        bookService.freeBook(bookID);
        return "redirect:/books/"+bookID;
    }

    @PatchMapping("/{bookID}/choosePerson")
    public String choose(@PathVariable("bookID") int bookID,
                         @RequestParam("person_id") int person_id){
        bookService.choosePerson(bookID, person_id);
        return "redirect:/books/"+bookID;
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("foundBook", new Book());

        boolean FLAG = false;
        model.addAttribute("FLAG", FLAG);

        return "books/search";
    }

    @PatchMapping("/search")
    public String searchWithAnswer(@RequestParam("title") String titleForSearch,
                                   Model model){
        model.addAttribute("books", bookService.findAllByTitleStartingWith(titleForSearch));

        boolean FLAG = true;
        model.addAttribute("FLAG", FLAG);

        model.addAttribute("foundBook", new Book());
        return "books/search";
    }

}