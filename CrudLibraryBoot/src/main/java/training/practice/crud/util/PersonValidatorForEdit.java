package training.practice.crud.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import training.practice.crud.models.Person;
import training.practice.crud.services.PeopleService;

@Component
public class PersonValidatorForEdit implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidatorForEdit(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Person person1 = peopleService.show(person.getName());

        if ((person1 != null) && (person1.getPersonId() != person.getPersonId())){
            errors.rejectValue("name", "", "This name is already taken");
        }

        if (person.getYear() == 0){
            errors.rejectValue("year", "", "Year should not be empty or equal 0");
        }
    }

}