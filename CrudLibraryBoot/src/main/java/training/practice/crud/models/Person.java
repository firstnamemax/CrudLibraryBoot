package training.practice.crud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @Size(min = 1, max = 50, message = "Name must contain 1 - 50 letters")
    @Column(name = "name")
    private String name;

    @Min(value = 1901, message = "Year should be equal or more than 1901")
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "reader")
    private List<Book> books;

    public Person(int person_id, String name, int year) {
        this.person_id = person_id;
        this.name = name;
        this.year = year;
    }

    public Person() {
    }

    public int getPersonId() {
        return person_id;
    }

    public void setPersonId(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", name=" + name +
                ", year=" + year +
                ", books=" + books +
                '}';
    }

}