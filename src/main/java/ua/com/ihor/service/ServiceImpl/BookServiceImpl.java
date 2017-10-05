package ua.com.ihor.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.ihor.dao.BookDAO;
import ua.com.ihor.entity.Book;
import ua.com.ihor.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    BookDAO bookDAO;
Scanner scanner=new Scanner(System.in);

        @Override
    public void saveBookWithAuthor(String book_name, String author_name, String author_surname) {
        if (author_name == null || author_name.isEmpty()) {
            bookDAO.save(new Book(book_name));
            System.out.println("Book " + book_name + " was added to the library catalogue");
        } else {
            bookDAO.save(new Book(book_name, author_name, author_surname));
            System.out.println("Book " + book_name + " with its author " + author_name + " " + author_surname + " were added to the library catalogue");
        }
    }

    @Override
    public List<Book> allSortedBooks() {
        List<Book> allSortedBooks = bookDAO.findAll();//find all
        allSortedBooks.sort((p1, p2) -> p1.getBook_name().compareTo(p2.getBook_name()));
        allSortedBooks.forEach(value -> {
            if(value.getAuthor()==null){
                System.out.println(value.getBook_name()+" Unknown author");
            }else {
                System.out.println(value.getBook_name()+" "+ value.getAuthor().getAuthor_name()+ " " +value.getAuthor().getAuthor_surname());
            }});
        System.out.println("Write book name to choose it for further actions (delete or update)");
        String choose_by_name=scanner.next();
        List<Book>chosenByNameBooks=new ArrayList<>();
        allSortedBooks.forEach(value->{
            if (value.getBook_name().equals(choose_by_name)){
                chosenByNameBooks.add(value);
            }
        });
        if (chosenByNameBooks.size()<=1){
            System.out.println("1-delete book, 2- edit book");
Book chosenBook=chosenByNameBooks.get(chosenByNameBooks.size()-1);
            switch (scanner.nextInt()) {
                case 1:
                    bookDAO.delete(chosenBook.getBook_id());
                    System.out.println("Book "+ chosenBook.getBook_name()+ " was deleted");
                    break;
                case 2:
                    System.out.println("write new book name to edit it");
                    String newName=scanner.next();
                   chosenBook.setBook_name(newName);
                    bookDAO.save(chosenBook);
                    System.out.println("New book name is- "+ newName);
                    break;
            }
        }else if (chosenByNameBooks.size()>1){
            System.out.println("There are a few books with this name: ");
            chosenByNameBooks.forEach(value->{
                System.out.println(value.getBook_id()+ " "+ value.getBook_name());
                });
            System.out.println("Choose book by id to do further actions");
            int id = scanner.nextInt();
            Book onee = bookDAO.findOne(id);
            System.out.println("1-delete book, 2- edit book");
               switch (scanner.nextInt()) {
                   case 1:
                      bookDAO.delete(id);
                       System.out.println("Book "+ onee.getBook_name()+ " was deleted");
                       break;
                   case 2:
                       System.out.println("Write new book name to edit it");
                      onee.setBook_name(scanner.next());
                       break;
           }
        }
        return allSortedBooks;
    }

    @Override
    public void deleteBook(String book_name) {
        List<Book> manyBooksByName = bookDAO.findManyBooksByName(book_name);
        if (manyBooksByName.size()<=1){
            int book_id_for_delete=manyBooksByName.get(manyBooksByName.size()-1).getBook_id();
        bookDAO.delete(book_id_for_delete);
            System.out.println("You removed " + book_name);
        }else if (manyBooksByName.size() > 1) {
            System.out.println("Write book id to remove it");
            manyBooksByName.forEach(value -> {
                if (value.getAuthor() == null) {
                    System.out.println("Id- " + value.getBook_id() + " book name- " + value.getBook_name() + " "  + ", unknown author");
                } else {
                    System.out.println("Id- " + value.getBook_id() + ", book name- " + value.getBook_name() + ", author name- " + value.getAuthor().getAuthor_name() + ", author surname- " + value.getAuthor().getAuthor_surname());
                }
            });
            int book_id_for_delete=scanner.nextInt();
                    bookDAO.delete(book_id_for_delete);
            System.out.println("You removed book by id- " + book_id_for_delete);
        }
    }

    @Override
    public Book updateBook(String book_name) {
        List<Book> manyBooksByName = bookDAO.findManyBooksByName(book_name);
        if (manyBooksByName.size()<=1){
            int book_id_for_update=manyBooksByName.get(manyBooksByName.size()-1).getBook_id();
            Book oldBook=manyBooksByName.get(manyBooksByName.size()-1);
            System.out.println("Write new book name");
            String newBookName=scanner.next();
            oldBook.setBook_name(newBookName);
            bookDAO.save(oldBook);
            System.out.println("You edited book. Its new name is " + newBookName);
        }else if (manyBooksByName.size() > 1) {
            System.out.println("Write book id to edit book (change its name)");
            manyBooksByName.forEach(value -> {
                if (value.getAuthor() == null) {
                    System.out.println("id- " + value.getBook_id() + " book name- " + value.getBook_name() + " "  + ", Unknown author");
                } else {
                    System.out.println("id- " + value.getBook_id() + ", book name- " + value.getBook_name() + ", author name- " + value.getAuthor().getAuthor_name() + ", author surname- " + value.getAuthor().getAuthor_surname());
                }

            });
            int book_id_for_update = scanner.nextInt();
        Book oldBook = bookDAO.findOne(book_id_for_update);
            System.out.println("Write new book name");
        String newBookName=scanner.next();
        oldBook.setBook_name(newBookName);
            System.out.println("Book with id- " + oldBook.getBook_id()+" was edited, its new name is- " + newBookName);
        }
        return null;
    }


}
