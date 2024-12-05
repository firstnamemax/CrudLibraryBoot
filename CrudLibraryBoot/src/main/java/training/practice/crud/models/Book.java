package training.practice.crud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private int bookID;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 50, message = "Incorrect title")
    @Column(name = "title")
    private String title;

    @Size(min = 1, max = 50, message = "Incorrect author")
    @Column(name = "author")
    private String author;

    @Min(value = 1701, message = "Year should be greater than 1700")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "person_id")
    private Person reader;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Transient
    private boolean FLAG;

    @Transient
    private final int DELAY = 10; // delay in the delivery of the book in minutes

    public Book(int bookID, String title, String author, int year) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getFLAG() {
        return FLAG;
    }

    public void setFLAG(boolean FLAG) {
        this.FLAG = FLAG;
    }

    public int getDELAY() {
        return DELAY;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", title=" + title +
                ", author=" + author +
                ", year=" + year +
                ", reader=" + reader +
                ", createdAt=" + createdAt +
                '}';
    }

}