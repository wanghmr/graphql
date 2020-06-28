package com.example.graphql.dao;

import com.example.graphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
public interface BookDao extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long id);

    Book findBookById(Long id);
}
