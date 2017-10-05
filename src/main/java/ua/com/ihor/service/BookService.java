package ua.com.ihor.service;

import ua.com.ihor.entity.Book;

import java.util.List;

public interface BookService {
    void saveBookWithAuthor(String book_name, String author_name, String author_surname);
    List<Book>allSortedBooks();
    void deleteBook(String book_name);
    Book updateBook(String book_name);
}
