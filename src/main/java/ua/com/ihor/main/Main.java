package ua.com.ihor.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ua.com.ihor.config.DataConfig;
import ua.com.ihor.service.BookService;
import java.util.Scanner;

@Component
public class Main {
    public static void main(String[]args){

        Scanner sc  =  new Scanner(System.in);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
        BookService bookService = context.getBean(BookService.class);

        System.out.println("Please choose option:  1-add book, 2-remove book by name, 3-edit book (change its name), 4-find all books sorted by name for further actions");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("Please write book name");
                sc.nextLine();
                String book_name = sc.nextLine();
                System.out.println("Please write author name or just click enter");
                String author_name = sc.nextLine();
                System.out.println("Please write author surname or just click enter");
                String author_surname = sc.nextLine();
                bookService.saveBookWithAuthor(book_name, author_name, author_surname);
                break;
            case 2:  //If there are 2 or more books with this name there is a choice of further actions.
                System.out.println("Write book name to remove it");
                bookService.deleteBook(sc.next());
                break;
            case 3:  //If there are 2 or more books with this name there is a choice of further actions.
                System.out.println("Write book name to edit it (change its name)");
               bookService.updateBook( sc.next());
                break;
            case 4: //A universal method for sorting books alphabetically. You can also remove or change selected book.
                bookService.allSortedBooks();
                break;

        }
    }
}
