package training.practice.crud.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import training.practice.crud.models.Person;
import training.practice.crud.services.BookService;
import training.practice.crud.services.PeopleService;
import training.practice.crud.util.PersonValidator;
import training.practice.crud.util.PersonValidatorForEdit;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonValidator personValidator;
    private final PersonValidatorForEdit personValidatorForEdit;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator, PersonValidatorForEdit personValidatorForEdit) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personValidator = personValidator;
        this.personValidatorForEdit = personValidatorForEdit;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("person", peopleService.findOne(person_id));
        model.addAttribute("books",
                bookService.showBooksWithDelay(bookService.showBooks(peopleService.findOne(person_id))));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(@PathVariable("person_id") int person_id,
                       Model model) {
        model.addAttribute("person", peopleService.findOne(person_id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String saveEdit(@PathVariable("person_id") int person_id,
                           @ModelAttribute("person") @Valid Person person,
                           BindingResult bindingResult) {
        person.setPersonId(person_id);
        personValidatorForEdit.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{person_id}")
    public String delete(@PathVariable("person_id") int person_id) {
        peopleService.delete(person_id);
        return "redirect:/people";
    }

}