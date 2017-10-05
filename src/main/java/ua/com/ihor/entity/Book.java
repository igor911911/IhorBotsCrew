package ua.com.ihor.entity;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    private String book_name;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Author author;

    public Book() {
    }


    public Book(String book_name) {
        this.book_name = book_name;
    }

    public Book(String book_name, String author_name, String author_surname) {
        this.book_name = book_name;
        this.author = new Author(author_name, author_surname);
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                '}';
    }
}
