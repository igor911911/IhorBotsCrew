package ua.com.ihor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.ihor.entity.Book;

import java.util.List;

public interface BookDAO extends JpaRepository<Book, Integer>{
    @Query("from Book b where b.book_name=:book_name")
    List<Book> findManyBooksByName(@Param("book_name")String book_name);

}
